package org.example.sweater.controller;


import org.example.sweater.domain.Message;
import org.example.sweater.domain.User;
import org.example.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

	@Value("${upload.path}")
	private String uploadPath;

	@Autowired
	private MessageRepository messageRepository;

	@GetMapping
	public String greeting(Map<String, Object> model) {
		return "greeting";
	}

	@GetMapping("/main")
	public String index(@RequestParam(required = false, defaultValue = "") String tag, Model model) {
		Iterable<Message> messages;
		if (tag != null && !tag.isEmpty())
			messages = messageRepository.findByTag(tag);
		else
			messages = messageRepository.findAll();
		model.addAttribute("messages", messages);
		model.addAttribute("tag", tag);

		return "index";
	}

	@PostMapping("/main/add")
	public String add(@AuthenticationPrincipal User user,
	                  @RequestParam String text,
	                  @RequestParam String tag,
	                  @RequestParam("file") MultipartFile file) throws IOException {
		Message message = new Message(text, tag, user);

		if (file != null && !file.getOriginalFilename().isEmpty()) {
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists())
				uploadDir.mkdir();
			String uuidFile = UUID.randomUUID().toString();
			String resultFileName = uuidFile + "." + file.getOriginalFilename();
			file.transferTo(new File(uploadPath + "/" + resultFileName));
			message.setFilename(resultFileName);
		}

		messageRepository.save(message);

		return "redirect:/main";
	}

	@PostMapping("/main/filter")
	public String filter(@RequestParam String tag, Map<String, Object> model) {
		Iterable<Message> messages;
		if (tag != null && !tag.isEmpty())
			messages = messageRepository.findByTag(tag);
		else
			messages = messageRepository.findAll();
		model.put("messages", messages);
		return "index";
	}
}
