package com.ubayKyu.accountingSystem.controller;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ubayKyu.accountingSystem.Method.GUIDtoUUIDMethod;
import com.ubayKyu.accountingSystem.entity.Category;
import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.service.CategoryService;
import com.ubayKyu.accountingSystem.service.UserInfoService;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService service;

	@GetMapping("/CategoryList.html")
	public String CategoryList(@RequestParam(value = "uid", required = false) String useridtxt,
			@RequestParam(value = "page", required = false) String pagetxt, Model model, HttpServletResponse response,
			HttpSession session) throws IOException {
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
		}
		model.addAttribute("level", IsAdmin);

		if (useridtxt != null) {
			// if (!useridtxt.equalsIgnoreCase(currentUser.getId().toString())) {
			// session.removeAttribute("loginUser");
			// return "redirect:Default.html";
			// }
			model.addAttribute("acctxt", currentUser.getAccount());

			Integer queryIndex = 0;
			if (pagetxt != null) {
				Integer currentPage = Integer.parseInt(pagetxt);
				queryIndex = currentPage - 1;
			}

			Pageable pageable = PageRequest.of(queryIndex, 10);
			List<Category> categorylist = service.getCategoryListByAccount(useridtxt, pageable);
			List<Integer> accountingCount = new ArrayList<Integer>();

			for (Category c : categorylist) {
				Integer cid = c.getCategoryid();
				Integer cnt = service.getAccountingInCategory(cid);
				accountingCount.add(cnt);

			}

			Integer cnt = service.getTotalCategoryByUserID(useridtxt);
			Integer totalpage = cnt / 10;
			if (cnt % 10 > 0) {
				totalpage += 1;
			}
			List<Integer> datapage = new ArrayList<Integer>();
			for (var i = 0; i < totalpage; i++) {
				datapage.add(i + 1);
			}

			model.addAttribute("userid", currentUser.getId());
			model.addAttribute("category", categorylist);
			model.addAttribute("count", accountingCount);
			model.addAttribute("total", datapage);

			return "/CategoryList.html";
		} else {
			session.removeAttribute("loginUser");
			return "redirect:Default.html";
		}
	}

	@PostMapping("/CategoryList.html/delete_category")
	// 刪除分類
	public String delete(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		if (request.getParameterValues("categoryID") != null) {
			for (String indextxt : request.getParameterValues("categoryID")) {
				Integer cnt = Integer.parseInt(request.getParameter("account_count"));
				if (cnt == 0) {
					Integer cid = Integer.parseInt(indextxt);
					service.DeleteCategoryByCID(cid);
				} else {
					model.addAttribute("errormsg", "勾選分類內仍有流水帳故無法刪除");
				}
			}
		}
		return "redirect:/CategoryList.html";
	}

	// 分類詳細頁顯示
	@GetMapping("/CategoryDetail.html")
	public String CategoryDetail(@RequestParam(value = "cid", required = false) String cidtxt, Model model,
			HttpSession session) {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");
		String useridtxt = currentUser.getId().toString();
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
		}
		model.addAttribute("level", IsAdmin);
		model.addAttribute("acctxt", currentUser.getAccount());
		model.addAttribute("userid", currentUser.getId().toString());

		if (cidtxt != null) {
			Integer cid = Integer.parseInt(cidtxt);
			Category cInfo = service.getCategoryByCID(cid);

			if (cInfo != null) {
				// if (cInfo.getUserid().toString().equalsIgnoreCase(useridtxt)) {
				// }
				model.addAttribute("title", cInfo.getTitle());
				model.addAttribute("remarks", cInfo.getRemarks());
				model.addAttribute("cid", cidtxt);
				model.addAttribute("newCat", false);
			} else {
				model.addAttribute("newCat", true);
				return "redirect:/CategoryDetail.html";
			}
			return "/CategoryDetail.html";
		} else {
			model.addAttribute("newCat", true);
			return "/CategoryDetail.html";
		}

	}

	@PostMapping("/CategoryDetail.html")
	public String EditCategoryDetail(@RequestParam(value = "cid", required = false) String cidtxt,
			@ModelAttribute("title") String titletxt, @ModelAttribute("remarks") String remarkstxt, Model model,
			HttpSession session) throws Exception {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");
		String uidtxt = currentUser.getId().toString();

		// 編輯模式
		if (cidtxt != null) {
			Integer cid = Integer.parseInt(cidtxt);
			model.addAttribute("newCat", false);

			try {
				service.updateCategoryByCID(titletxt, remarkstxt, cid);
			} catch (Exception ex) {
				model.addAttribute("errormsg", ex.getMessage());
			}
			return "redirect:CategoryDetail.html?cid=" + cidtxt;
		} else {
			return "redirect:CategoryList.html?uid=" + uidtxt;
		}
	}

	@GetMapping("/CategoryDetail.html/new")
	// 新增分類詳細頁顯示
	public String newCategory(Model model, HttpSession session) {
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
		}
		model.addAttribute("level", IsAdmin);
		model.addAttribute("acctxt", currentUser.getAccount());
		model.addAttribute("userid", currentUser.getId().toString());

		Category category = new Category();
		Integer newcid = service.getlastCID() + 1;
		category.setCategoryid(newcid);

		model.addAttribute("create", category);
		model.addAttribute("newCat", true);

		return "/CategoryDetail.html";
	}

	@PostMapping("/CategoryDetail.html/new")
	public String CreateCategoryDetail(@ModelAttribute("create") Category category,
			@ModelAttribute("title") String titletxt, @ModelAttribute("remarks") String remarkstxt, Model model,
			HttpSession session, HttpServletResponse response) throws Exception {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");
		String uidtxt = currentUser.getId().toString();

		// 新增模式
		LocalDateTime ct = LocalDateTime.now();

		try {
			service.createCategoryByCID(uidtxt, titletxt, remarkstxt, ct);
			response.sendRedirect("/CategoryList.html?uid=" + uidtxt);
		} catch (Exception ex) {
			model.addAttribute("errormsg", ex.getMessage());
		}
		return "redirect:/CategoryList.html?uid=" + uidtxt;
	}
}
