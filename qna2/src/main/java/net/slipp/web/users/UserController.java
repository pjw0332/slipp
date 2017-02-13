package net.slipp.web.users;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.slipp.dao.users.UserDao;
import net.slipp.domain.users.Authenticate;
import net.slipp.domain.users.User;

@Controller
@RequestMapping("/users")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDao userDao;

	@RequestMapping("/form")
	public String form(Model model) {
		model.addAttribute("user", new User());
		return "users/form";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String createForm(@Valid User user, BindingResult bindingResult) {
		log.debug("User : {}", user);
		if (bindingResult.hasErrors()) {
			log.debug("Binding Result has error!");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				log.debug("error : {}, {} ", error.getObjectName(), error.getDefaultMessage());
			}
			return "users/form";
		}
		userDao.create(user);
		log.debug("Database : {}", userDao.findById(user.getUserId()));
		return "redirect:/";
		}
	
	@RequestMapping("{userId}/form")
	public String updateForm(@PathVariable String userId, Model model) {
		if(userId == null) {
			throw new IllegalArgumentException("사용자 아이디가 필요합니다.");
		}
		
		User user = userDao.findById(userId);
		model.addAttribute("user", user);
		return "users/form";
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public String update(@Valid User user, BindingResult bindingResult, HttpSession session) {
		log.debug("User : {}", user);
		if (bindingResult.hasErrors()) {
			log.debug("Binding Result has error!");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				log.debug("error : {}, {} ", error.getObjectName(), error.getDefaultMessage());
			}
			return "users/form";
		}
		
		Object temp = session.getAttribute("userId");
		if(temp == null) {
			throw new NullPointerException();
		}
		
		String userId = (String)temp;
		if(!user.matchUserId(userId)) {
			throw new NullPointerException();
		}
		
		userDao.update(user);
		log.debug("Database : {}", userDao.findById(user.getUserId()));
		return "redirect:/";
		}
	
	@RequestMapping("/login/form")
	public String loginForm(Model model) {
		model.addAttribute("authenticate", new Authenticate());
		return "users/login";
	}
	
	@RequestMapping("/login")
	public String login(@Valid Authenticate authenticate, BindingResult bindingResult, HttpSession session, Model model) {
		if (bindingResult.hasErrors()){
			return "users/login";
		}
		
		User user = userDao.findById(authenticate.getUserId());
		if(user == null) {
			model.addAttribute("errorMessage", "존재하지 않는 유저입니다.");
			return "users/login";
			//Todo 에러처리 = 존재하지 않는 사용자입니다.
		}
		 
		if(!user.matchPassword(authenticate)){
			model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
			return "users/login";
			//Todo 에러처리 = 비밀번호가 일치하지 않습니다.
		}
		
		session.setAttribute("userId", user.getUserId());
		// TODO 세션에 사용자 정보 저장
		
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("userId");
		return "redirect:/";
	}
	

}
