package com.ubayKyu.accountingSystem.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ubayKyu.accountingSystem.entity.Category;
import com.ubayKyu.accountingSystem.entity.UserInfo;
import com.ubayKyu.accountingSystem.service.CategoryService;
import com.ubayKyu.accountingSystem.service.UserInfoService;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService service;
	@Autowired
	private UserInfoService userservice;

	@GetMapping("/CategoryList.html")
	public String CategoryList(@RequestParam(value = "uid", required = false) String useridtxt,
			@RequestParam(value = "page", required = false) String pagetxt, Model model, HttpSession session) {
		UserInfo currentUser = (UserInfo) session.getAttribute("loginUser");

		Integer queryIndex = 0;
		if (pagetxt != null) {
			Integer currentPage = Integer.parseInt(pagetxt);
			queryIndex = currentPage - 1;
		}

		Pageable pageable = PageRequest.of(queryIndex, 10);
		List<Category> categorylist = service.getCategoryListByAccount(useridtxt, pageable);
		List<Integer> accountingCount = new ArrayList<Integer>();

		for (Category c : categorylist) {
			Integer cid = c.getCID();
			Integer cnt = service.getAccountingInCategory(cid);
			accountingCount.add(cnt);

		}

		Integer cnt = service.getTotalCategoryByUserID(useridtxt);
		Integer pagecnt = cnt / 10;
		List<Integer> datapage = new ArrayList<Integer>();
		for (var i = 0; i <= pagecnt; i++) {
			datapage.add(i + 1);
		}

		model.addAttribute("userid", currentUser.getId());
		model.addAttribute("category", categorylist);
		model.addAttribute("count", accountingCount);
		model.addAttribute("total", datapage);
		return "/CategoryList.html";
	}

	@PostMapping("/CategoryDetail.html")
	public String newCategory() {

		return "/CategoryDetail.html";
	}

	@PostMapping("/CategoryList.html/delete_category")
	public String delete(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		if (request.getParameterValues("categoryID") != null) {
			for (String indextxt : request.getParameterValues("categoryID")) {
				Integer cnt = Integer.parseInt(request.getParameter("account_count"));
				if (cnt == 0) {
					Integer cid = Integer.parseInt(indextxt);
					service.DeleteCategoryByCID(cid);
				} else {
					response.sendRedirect("/CategoryList.html");
				}
			}
		}
		return "redirect:/CategoryList.html/delete_category";
	}

	@RequestMapping("/CategoryDetail.html")
	public String CategoryDetail() {
		return "/CategoryDetail.html";
	}
}
