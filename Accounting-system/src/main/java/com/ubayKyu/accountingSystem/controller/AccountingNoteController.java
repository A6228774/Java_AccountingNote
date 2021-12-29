package com.ubayKyu.accountingSystem.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ubayKyu.accountingSystem.entity.AccountingNote;
import com.ubayKyu.accountingSystem.entity.Category;
import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.service.AccountingNoteService;
import com.ubayKyu.accountingSystem.service.CategoryService;

@Controller
public class AccountingNoteController {
	@Autowired
	private AccountingNoteService service;
	@Autowired
	private CategoryService Catservice;

	@GetMapping("/AccountingList.html")
	public String AccountingList(@RequestParam(value = "uid", required = false) String useridtxt,
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
		}
		model.addAttribute("level", IsAdmin);

		if (useridtxt != null) {
			// if (!useridtxt.equalsIgnoreCase(currentUser.getId().toString())) {
			// session.removeAttribute("loginUser");
			// return "redirect:Default.html";
			// }
			model.addAttribute("ACC", currentUser.getAccount());

			// 金額小計
			BigDecimal totalin = new BigDecimal("0");
			if (service.getTotalIncomeByUID(useridtxt, 0) != null) {
				totalin = service.getTotalIncomeByUID(useridtxt, 0);
			}

			BigDecimal totalout = new BigDecimal("0");
			if (service.getTotalIncomeByUID(useridtxt, 1) != null) {
				totalout = service.getTotalExpentitureByUID(useridtxt, 1);
			}

			var totalamount = new BigDecimal("0");
			totalamount = totalamount.add(totalin);
			totalamount = totalamount.subtract(totalout);
			model.addAttribute("totalamount", totalamount.toString());

			// 本月小計
			LocalDate today = LocalDate.now();
			LocalDate start = today.withDayOfMonth(1);
			LocalDate end = today.withDayOfMonth(today.getMonth().length(today.isLeapYear()));

			BigDecimal monthlyin = new BigDecimal("0");
			if (service.getMonthlyIncomeByUID(useridtxt, 0, start, end) != null) {
				monthlyin = service.getMonthlyIncomeByUID(useridtxt, 0, start, end);
			}

			BigDecimal monthlyout = new BigDecimal("0");
			if (service.getMonthlyIncomeByUID(useridtxt, 1, start, end) != null) {
				monthlyout = service.getMonthlyExpentitureByUID(useridtxt, 1, start, end);
			}
			BigDecimal monthlyamount = new BigDecimal("0");
			monthlyamount = monthlyamount.add(monthlyin);
			monthlyamount = monthlyamount.subtract(monthlyout);
			model.addAttribute("monthly", monthlyamount.toString());

			Integer queryIndex = 0;
			if (pagetxt != null) {
				Integer currentPage = Integer.parseInt(pagetxt);
				queryIndex = currentPage - 1;
			}

			Pageable pageable = PageRequest.of(queryIndex, 10);
			List<AccountingNote> acclist = service.getFullAccountingListByAccount(useridtxt, pageable);

			Integer cnt = service.getTotalAccountingByUserID(useridtxt);
			Integer totalpage = cnt / 10;
			if (cnt % 10 > 0) {
				totalpage += 1;
			}
			List<Integer> datapage = new ArrayList<Integer>();
			for (var i = 0; i < totalpage; i++) {
				datapage.add(i + 1);
			}

			model.addAttribute("userid", currentUser.getId());
			model.addAttribute("accounting", acclist);
			model.addAttribute("total", datapage);

			return "/AccountingList.html";
		} else {
			session.removeAttribute("loginUser");
			return "redirect:Default.html";
		}
	}

	@PostMapping("/AccountingList.html")
	public String delete(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rediectatt,
			HttpSession session) throws IOException {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");
		String useridtxt = currentUser.getId().toString();

		// 刪除分類
		String[] delList = request.getParameterValues("accountingID");
		if (delList != null) {
			for (String indextxt : delList) {
				Integer accountingid = Integer.parseInt(indextxt);
				try {
					service.DeleteAccountingByID(accountingid);
				} catch (Exception ex) {
					rediectatt.addFlashAttribute("errormsg", ex.getMessage());
				}
			}
		} else {
			rediectatt.addFlashAttribute("errormsg", "未勾選流水帳");
		}
		return "redirect:/AccountingList.html?uid=" + useridtxt;
	}

	// 流水帳詳細頁顯示
	@GetMapping("/AccountingDetail.html")
	public String AccountingDetail(@RequestParam(value = "id", required = false) String accountingidtxt, Model model,
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
		model.addAttribute("ACC", currentUser.getAccount());
		model.addAttribute("userid", useridtxt);

		if (accountingidtxt != null) {
			Integer accountingid = Integer.parseInt(accountingidtxt);
			AccountingNote aInfo = service.getAccountingByID(accountingid);

			if (aInfo != null) {
				// if (aInfo.getUserid().toString().equalsIgnoreCase(useridtxt)) {
				// }
				List<Category> category = Catservice.getCategoryDLLByUserID(useridtxt);

				model.addAttribute("aid", aInfo.getId().toString());
				model.addAttribute("act", aInfo.getActType());
				model.addAttribute("catDDL", category);
				model.addAttribute("amount", aInfo.getAmount());
				model.addAttribute("caption", aInfo.getCaption());
				model.addAttribute("remarks", aInfo.getRemarks());
				model.addAttribute("cid", aInfo.getCategoryid());
				model.addAttribute("newAccounting", false);
			} else {
				model.addAttribute("newAccounting", true);
				return "redirect:/AccountingDetail.html";
			}
			return "/AccountingDetail.html";
		} else {
			model.addAttribute("newAccounting", true);
			return "/AccountingDetail.html";
		}
	}

	@PostMapping("/AccountingDetail.html")
	public String EditCategoryDetail(@RequestParam(value = "id", required = false) String accountingidtxt,
			@ModelAttribute("acttype") String acttype,
			@RequestParam(value = "category", required = false) String cidtxt,
			@ModelAttribute("amount") String amounttxt, @ModelAttribute("caption") String captiontxt,
			@ModelAttribute("remarks") String remarkstxt, Model model, RedirectAttributes rediatt, HttpSession session)
			throws Exception {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");
		String uidtxt = currentUser.getId().toString();

		// 編輯模式
		if (accountingidtxt != null) {
			Integer aid = Integer.parseInt(accountingidtxt);
			Integer act = Integer.parseInt(acttype);
			Integer amount = Integer.parseInt(amounttxt);
			Integer cid = null;
			if (cidtxt != null) {
				cid = Integer.parseInt(cidtxt);
			}
			model.addAttribute("newAccounting", false);

			try {
				service.updateAccountingByID(uidtxt, captiontxt, remarkstxt, amount, act, cid, aid);
			} catch (Exception ex) {
				rediatt.addFlashAttribute("errormsg", ex.getLocalizedMessage());
			}
			return "redirect:AccountingDetail.html?id=" + accountingidtxt;
		} else {
			return "redirect:AccountingList.html?uid=" + uidtxt;
		}
	}

	@GetMapping("/AccountingDetail.html/new")
	public String newAccounting(Model model, HttpSession session) {
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
		model.addAttribute("ACC", currentUser.getAccount());
		model.addAttribute("userid", useridtxt);

		List<Category> category = Catservice.getCategoryDLLByUserID(useridtxt);
		model.addAttribute("catDDL", category);

		AccountingNote accountingnote = new AccountingNote();
		model.addAttribute("create", accountingnote);
		model.addAttribute("newAccounting", true);

		return "/AccountingDetail.html";
	}

	@PostMapping("/AccountingDetail.html/new")
	public String CreateAccountingDetail(@ModelAttribute("create") AccountingNote details,
			@ModelAttribute("acttype") String acttype,
			@RequestParam(value = "category", required = false) String cidtxt,
			@ModelAttribute("amount") String amounttxt, @ModelAttribute("caption") String captiontxt,
			@ModelAttribute("remarks") String remarkstxt, RedirectAttributes rediatt, HttpSession session,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");
		String uidtxt = currentUser.getId().toString();

		// 新增模式
		LocalDateTime ct = LocalDateTime.now();
		Integer act = Integer.parseInt(acttype);
		Integer amount = Integer.parseInt(amounttxt);
		Integer cid = null;
		if (cidtxt != null) {
			cid = Integer.parseInt(cidtxt);
		}

		try {
			service.createAccountingBy(uidtxt, captiontxt, remarkstxt, amount, act, ct, cid);
		} catch (Exception ex) {
			rediatt.addFlashAttribute("errormsg", ex.getLocalizedMessage());
			return "redirect:/AccountingDetail.html/new";
		}
		return "redirect:/AccountingList.html?uid=" + uidtxt;
	}
}
