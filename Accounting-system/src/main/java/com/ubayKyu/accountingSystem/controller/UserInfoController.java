package com.ubayKyu.accountingSystem.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.service.UserInfoService;

@Controller
public class UserInfoController {
	@Autowired
	private UserInfoService service;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// 個人資訊頁顯示
	@GetMapping("/UserProfile.html")
	public String UserProfileInfo(@RequestParam(value = "account", required = true) String acctxt, Model model,
			HttpSession session, HttpServletResponse response) throws IOException {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");
		if (currentUser == null) {
			session.removeAttribute("loginUser");
			response.sendRedirect("/Default.html");
		} else {
			UserInfo info = service.getUserInfoByAccount(acctxt);
			String uuidtxt = info.getId();

			model.addAttribute("uidtxt", info.getId());
			model.addAttribute("ACC", info.account.toString());
			model.addAttribute("Name", info.name.toString());
			model.addAttribute("Email", info.getEmail().toString());
			model.addAttribute("userid", uuidtxt);
		}

		boolean IsAdmin;
		if (currentUser.getUserLevel() == 0) {
			IsAdmin = true;
		} else {
			IsAdmin = false;
		}
		model.addAttribute("level", IsAdmin);
		return "UserProfile.html";
	}

	@PostMapping("/UserProfile.html")
	public String SaveProfile(@ModelAttribute("name") String nametxt, @ModelAttribute("email") String emailtxt,
			RedirectAttributes rediatt, HttpServletRequest request, HttpSession session) {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");

		if (currentUser == null) {
			session.removeAttribute("loginUser");
			return "redirect:/Default.html";
		}
		String acctxt = currentUser.getAccount();
		String useridtxt = currentUser.getId();

		try {
			service.UpdateUserInfo(nametxt, emailtxt, useridtxt);
		} catch (Exception ex) {
			rediatt.addFlashAttribute("errormsg", ex.getLocalizedMessage());
		}
		return "redirect:/UserProfile.html?account=" + acctxt;
	}

	@GetMapping("/UserList.html")
	public String UserList(@RequestParam(value = "cUser", required = false) String useridtxt,
			@RequestParam(value = "page", required = false) String pagetxt, Model model, HttpServletResponse response,
			HttpSession session) {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");

		// 判斷登入
		if (currentUser == null) {
			session.removeAttribute("loginUser");
			return "redirect:Default.html";
		}

		// 頁面權限檢查
		boolean IsAdmin;
		if (currentUser.getUserLevel() == 0) {
			IsAdmin = true;
		} else {
			IsAdmin = false;
			session.removeAttribute("loginUser");
			return "redirect:Default.html";
		}
		model.addAttribute("level", IsAdmin);
		model.addAttribute("ACC", currentUser.getAccount());

		if (useridtxt != null) {
			// if (!useridtxt.equalsIgnoreCase(currentUser.getId().toString())) {
			// session.removeAttribute("loginUser");
			// return "redirect:Default.html";
			// }

			Integer queryIndex = 0;
			if (pagetxt != null) {
				Integer currentPage = Integer.parseInt(pagetxt);
				queryIndex = currentPage - 1;
			}

			Pageable pageable = PageRequest.of(queryIndex, 10);
			List<UserInfo> userlist = service.getAllUserList(pageable);

			Integer cnt = Math.toIntExact(service.usercnt());
			Integer totalpage = cnt / 10;
			if (cnt % 10 > 0) {
				totalpage += 1;
			}
			List<Integer> datapage = new ArrayList<Integer>();
			for (var i = 0; i < totalpage; i++) {
				datapage.add(i + 1);
			}

			model.addAttribute("userid", currentUser.getId());
			model.addAttribute("userlist", userlist);
			model.addAttribute("total", datapage);

			return "/UserList.html";
		} else {
			session.removeAttribute("loginUser");
			return "redirect:Default.html";
		}
	}

	// User詳細頁顯示
	@GetMapping("/UserDetail.html")
	public String UserDetail(@RequestParam(value = "info", required = false) String useridtxt, Model model,
			HttpSession session) {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");

		// 判斷登入
		if (currentUser == null) {
			session.removeAttribute("loginUser");
			return "redirect:Default.html";
		}

		// 頁面權限檢查
		boolean IsAdmin;
		if (currentUser.getUserLevel() == 0) {
			IsAdmin = true;
		} else {
			IsAdmin = false;
			session.removeAttribute("loginUser");
			return "redirect:Default.html";
		}
		model.addAttribute("level", IsAdmin);
		model.addAttribute("ACC", currentUser.getAccount());
		model.addAttribute("userid", useridtxt);

		if (useridtxt != null) {
			UserInfo uInfo = service.getUserInfoByUserID(useridtxt);

			if (uInfo != null) {
				model.addAttribute("info_account", uInfo.getAccount());
				model.addAttribute("info_name", uInfo.getName());
				model.addAttribute("info_email", uInfo.getEmail());
				model.addAttribute("info_create", uInfo.getCreateDate());
				model.addAttribute("info_edit", uInfo.getEditDate());
				model.addAttribute("info_level", uInfo.getUserLevel());
				model.addAttribute("newUser", false);
			} else {
				model.addAttribute("newUser", true);
				return "redirect:/UserDetail.html";
			}
			return "UserDetail.html";
		} else {
			model.addAttribute("newUser", true);
			return "redirect:/UserDetail.html";
		}
	}

	@PostMapping("/UserDetail.html")
	public String EditUserDetail(@RequestParam(value = "info", required = false) String useridtxt,
			@ModelAttribute("name") String nametxt, @ModelAttribute("email") String emailtxt,
			@ModelAttribute("userlevel") String leveltxt, Model model, RedirectAttributes rediatt, HttpSession session)
			throws Exception {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");
		Integer clevel = currentUser.getUserLevel();
		String cleveltxt = null;
		if (clevel == 0) {
			cleveltxt = "admin";
		}

		// 編輯模式
		if (useridtxt != null) {
			model.addAttribute("newUser", false);
			LocalDateTime ct = LocalDateTime.now();
			Integer level = Integer.parseInt(leveltxt);

			try {
				service.updateUser_Admin(nametxt, emailtxt, level, ct, useridtxt);
			} catch (Exception ex) {
				rediatt.addFlashAttribute("errormsg", ex.getLocalizedMessage());
			}
			return "redirect:UserDetail.html?info=" + useridtxt;
		} else {
			return "redirect:UserList.html?cUser=" + cleveltxt;
		}
	}

	@GetMapping("/UserDetail.html/new")
	public String newUserDetail(Model model, HttpServletRequest request, HttpSession session) {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");

		// 判斷登入
		if (currentUser == null) {
			session.removeAttribute("loginUser");
			return "redirect:Default.html";
		}

		// 頁面權限檢查
		boolean IsAdmin;
		if (currentUser.getUserLevel() == 0) {
			IsAdmin = true;
		} else {
			IsAdmin = false;
			session.removeAttribute("loginUser");
			return "redirect:Default.html";
		}
		model.addAttribute("level", IsAdmin);
		model.addAttribute("ACC", currentUser.getAccount());
		model.addAttribute("newUser", true);

		return "/UserDetail.html";
	}

	@PostMapping("/UserDetail.html/new")
	public String CreateCategoryDetail(@ModelAttribute("create") UserInfo userinfo,
			@ModelAttribute("account") String acctxt, @ModelAttribute("name") String nametxt,
			@ModelAttribute("email") String emailtxt, @ModelAttribute("userlevel") String leveltxt, Model model,
			RedirectAttributes rediatt, HttpSession session, HttpServletResponse response) throws Exception {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");
		Integer clevel = currentUser.getUserLevel();
		String cleveltxt = null;
		if (clevel == 0) {
			cleveltxt = "admin";
		}

		// 檢查重複帳號
		Boolean IsRepeat = service.checkRepeat(acctxt);
		if (IsRepeat == true) {
			rediatt.addFlashAttribute("accmsg", "帳號已經存在，請使用其他帳號名稱");
			return "redirect:/UserDetail.html/new";
		} else {
			// 新增模式
			LocalDateTime ct = LocalDateTime.now();
			String pwdtxt = "12345678";
			Integer level = Integer.parseInt(leveltxt);

			try {
				service.createNewUser(acctxt, pwdtxt, nametxt, emailtxt, level, ct);

			} catch (Exception ex) {
				rediatt.addFlashAttribute("errormsg", ex.getLocalizedMessage());
				return "redirect:/UserDetail.html/new";
			}
		}
		return "redirect:/UserList.html?cUser=" + cleveltxt;
	}

	@PostMapping("/UserList.html")
	public String delete(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rediectatt,
			HttpSession session) throws IOException {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");
		Integer clevel = currentUser.getUserLevel();
		String cleveltxt = null;
		if (clevel == 0) {
			cleveltxt = "admin";
		}

		// 刪除分類
		String[] delList = request.getParameterValues("userID");
		if (delList != null) {
			for (String indextxt : delList) {
				try {
					//service.DeleteUserByUserID(indextxt);
					logger.info("管理者：" + currentUser.getAccount() +  '於' + LocalDateTime.now() + "刪除使用者：" + service.getUserInfoByUserID(indextxt).getAccount());
				} catch (Exception ex) {
					rediectatt.addFlashAttribute("errormsg", ex.getMessage());
				}
			}
		} else {
			rediectatt.addFlashAttribute("errormsg", "未勾選使用者");
		}
		return "redirect:/UserList.html?cUser=" + cleveltxt;
	}
}