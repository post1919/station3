package com.station3.login.handler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomLoginSuccessHandler /*extends SavedRequestAwareAuthenticationSuccessHandler*/ {

//	@Autowired
	//LoginService loginService;

	/*@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		SecurityContextHolder.getContext().setAuthentication(authentication);

		log.info("Login 성공");
		List<String> roleNames = new ArrayList<>();
		authentication.getAuthorities().forEach(authority -> { roleNames.add(authority.getAuthority()); });
		log.info("Role Names : " + roleNames);

		//if(roleNames.contains("ROLE_ADMIN")) { response.sendRedirect("/sample/admin"); return; }
		//if(roleNames.contains("ROLE_MEMBER")) { response.sendRedirect("/sample/member"); return; }

		//response.sendRedirect(ConstantProperties.PATH_MAIN);

		ObjectMapper mapper = new ObjectMapper();	//JSON 변경용

		log.info("authentication.getPrincipal() => " + authentication.getPrincipal());

		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setCode(ConstantProperties.RESPONSE_STATUS_200);
		loginResponseDto.setStatus(ConstantProperties.RESPONSE_STATUS_200);
		loginResponseDto.setResult(ConstantProperties.OUTPUT_SUCCESS);
		loginResponseDto.setUser((CustomUserDetailsDto)authentication.getPrincipal());

		log.info("loginResponseDto.getUser => " + loginResponseDto.getUser());

		//String prevPage = request.getSession().getAttribute("prevPage").toString();	//이전 페이지 가져오기

		HttpSession session = request.getSession();
		session.setAttribute("SESSION_loginTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
		session.setAttribute("USER", authentication.getPrincipal());

		Map<String, String> items = new HashMap<String,String>();
		//items.put("url", prevPage);	// 이전 페이지 저장
		loginResponseDto.setItems(items);

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

		String json =objectMapper.writeValueAsString(loginResponseDto);

		System.out.println("response json => " + json);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}*/
}
