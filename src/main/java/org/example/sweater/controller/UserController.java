package org.example.sweater.controller;

import org.example.sweater.domain.Role;
import org.example.sweater.domain.User;
import org.example.sweater.repository.UserRepository;
import org.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public String userList(Model model) {
		model.addAttribute("users", userService.findAll());
		return "userList";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("{user}")
	public String userEditForm(@PathVariable User user, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("roles", Role.values());
		return "userEdit";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public String userSave(@RequestParam("userId") User user,
	                       @RequestParam String username,
	                       @RequestParam Map<String, String> form)
	{
		userService.saveUser(user, username, form);
		return "redirect:/user";
	}

	@GetMapping("/profile")
	public String userProfile(Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("user", user);

		String s = (String) model.getAttribute("message");
		if (s != null)
			System.out.println(s);
		return "/profile";
	}

	@PostMapping("/profile")
	public String editProfile(@AuthenticationPrincipal User currentUser, User updateUser, Model model) {
		String message = userService.updateUser(currentUser, updateUser);

		model.addAttribute("message", message);
		return "/profile";
	}
}
