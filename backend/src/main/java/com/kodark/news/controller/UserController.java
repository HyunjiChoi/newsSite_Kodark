package com.kodark.news.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodark.news.service.AuthProcedureService;
import com.kodark.news.service.MailService;
import com.kodark.news.service.UsersProceduerService;
import com.kodark.news.utils.PasswordEncoderImpl;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	Environment env;
	MailService mailService;
	AuthProcedureService authProcedureService;
	UsersProceduerService usersProcedureService;
	PasswordEncoderImpl passwordEncoder;

	@Autowired
	public UserController(Environment env, MailService mailService, AuthProcedureService authProcedureService,
			UsersProceduerService usersProcedureService, PasswordEncoderImpl passwordEncoder) {
		this.env = env;
		this.mailService = mailService;
		this.authProcedureService = authProcedureService;
		this.usersProcedureService = usersProcedureService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> userInfo(HttpServletRequest request, HttpServletResponse response) {
		int id = (int) request.getAttribute("id");
		Map<String, Object> params = new HashMap<>();
		params.put("_switch", "user_info");
		params.put("_id", id);
		usersProcedureService.execuUsersProcedure(params);
		String resultSet = (String) params.get("result_set");
		if (resultSet.equals("success")) {
			Map<String, Object> map = new HashMap<>();
			map.put("email", params.get("_email"));
			map.put("auth", params.get("_auth"));

			response.setHeader("Links",
					"</users/my-page>; rel=\"myPage\"" + ", </users/my-page/detail>; rel=\"userDetail\""
							+ ", </users/my-page/subscribed-list>; rel=\"subscribedList\""
							+ ", </users/sign-out>; rel=\"signOut\"");
			return new ResponseEntity<>(map, HttpStatus.OK);// 200
		} else if (resultSet.equals("not_found")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
		}
	}

	/**
	 * 마이페이지 작성자 : 최현지 작성일 : 2021-01-07
	 */
	@GetMapping(path = "/my-page")
	public ResponseEntity<Map<String, Object>> myPage(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> linkList = new ArrayList<Map<String, Object>>();
		Map<String, Object> link1;
		Map<String, Object> link2;
		Map<String, Object> link3;

		request.getAttribute("id");
		int id = 2;
		params.put("_id", id);

		params.put("_switch", "my-page");
		usersProcedureService.myPage(params);

		String email = (String) params.get("_email");
		String auth = (String) params.get("_auth");
		params.clear();

		params.put("email", email);
		params.put("auth", auth);

		link1 = new HashMap<String, Object>();
		link2 = new HashMap<String, Object>();
		link3 = new HashMap<String, Object>();

		link1.put("rel", "deleteUser");
		link1.put("href", "/users");
		link1.put("method ", "delete");
		linkList.add(link1);

		link2.put("rel", "editEmail");
		link2.put("href", "/auth");
		link2.put("method ", "patch");
		linkList.add(link2);

		link3.put("rel", "validation");
		link3.put("href", "/auth/verify");
		link3.put("method ", "patch");
		linkList.add(link3);

		params.put("_links", linkList);

		response.setHeader("Links",
				"</users/my-page>; 					rel=\"self\","
						+ "</users/my-page/detail>;			rel=\"userDetail\","
						+ "</users/my-page/subscribed-list>;	rel=\"subscribedList\","
						+ "</auth>;  							rel=\"eidtEmail\","
						+ "</auth/verify>;  					rel=\"validation\","
						+ "</users>; 							rel=\"deleteUser\"");

		return new ResponseEntity<Map<String, Object>>(params, HttpStatus.OK);// 200
	}

	// 회원가입
	@PostMapping(path = "/sign-up")
	public ResponseEntity<String> signUp(@RequestBody Map<String, Object> body, HttpServletResponse response) {
		String email = (String) body.get("email");
		String pwd = (String) body.get("pwd");
		String encodedPwd = passwordEncoder.encode(pwd);
		Map<String, Object> params = new HashMap<>();
		params.put("_switch", "sign_up");
		params.put("_email", email);
		params.put("_pwd", encodedPwd);
		authProcedureService.execuAuthProcedure(params);

		response.setHeader("Links", "</users/sign-up>; rel=\"self\"," + "</ko/signIn>; 	rel=\"next\"");
		if (params.get("result_set").equals("success"))
			return new ResponseEntity<>(HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.CONFLICT);

	}

	// 비밀번호 수정
	@PatchMapping(path = "/pwd")
	public ResponseEntity<String> pwdUpdate(@RequestBody Map<String, Object> body) {
		String pwd = (String) body.get("pwd");
		String email = (String) body.get("email");
		int id = Integer.valueOf((String) body.get("id"));
		Map<String, Object> params = new HashMap<>();
		params.put("_switch", "update_password");
		params.put("_pwd", pwd);
		params.put("_id", id);
		params.put("_email", email);
		usersProcedureService.execuUsersProcedure(params);

		if (params.get("result_set").equals("204")) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);// 204
		} else if (params.get("result_set").equals("401")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);// 401
		} else if (params.get("result_set").equals("404")) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);// 404
		} else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// 500
	}

	/**
	 * 대댓글 작성 날짜 : 2021-01-08 작성자 : 이종현
	 */
	@PostMapping(path = "/comment/reply")
	public ResponseEntity<String> writeCommentReply(@RequestParam("commentId") int commentId,
			@RequestBody Map<String, Object> body) {
		Map<String, Object> params = null;
		try {
			params = new HashMap<String, Object>();
			params.put("_commentId", commentId);
			params.put("_email", body.get("email"));
			params.put("_content", body.get("content"));
			usersProcedureService.writeCommentReply(params);

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
