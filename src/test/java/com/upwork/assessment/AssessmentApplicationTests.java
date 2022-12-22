package com.upwork.assessment;

import com.upwork.assessment.config.MyUser;
import com.upwork.assessment.config.SecurityConfig;
import com.upwork.assessment.controller.AuthController;
import com.upwork.assessment.controller.HomeController;
import com.upwork.assessment.repository.UsersRepository;
import com.upwork.assessment.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ HomeController.class, AuthController.class })
@Import({ SecurityConfig.class, TokenService.class })
@ActiveProfiles("test")
class AssessmentApplicationTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	UsersRepository usersRepository;

	public String getTokenForUser(String username, String password) throws Exception {
		if(Objects.equals(username, "")){
			username = "user";
		}
		if(Objects.equals(password, "")){
			password = "$2a$12$z0TyNiXX75DBE4t7.Dh2B.iZOwT.MlAd5dT7dgkLIIxL9NPXGZj/W";
		}
		Set<GrantedAuthority> authorities = new HashSet<>();
		GrantedAuthority grantedAuthority = new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return "USER";
			}
		};
		authorities.add(grantedAuthority);
		MyUser myUser = new MyUser(username, password,true,true,true,true,authorities,"a","b","a@gmail.com",  LocalDate.of(1996,12,24));
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/token").with(user(myUser));

		MvcResult result = this.mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

		return result.getResponse().getContentAsString();
	}


	public String getTokenForAdmin(String username, String password) throws Exception {
		if(Objects.equals(username, "")){
			username = "user";
		}
		if(Objects.equals(password, "")){
			password = "$2a$12$z0TyNiXX75DBE4t7.Dh2B.iZOwT.MlAd5dT7dgkLIIxL9NPXGZj/W";
		}
		Set<GrantedAuthority> authorities = new HashSet<>();
		GrantedAuthority grantedAuthority = new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return "ADMIN";
			}
		};
		authorities.add(grantedAuthority);
		MyUser myUser = new MyUser(username, password,true,true,true,true,authorities,"a","b","a@gmail.com",  LocalDate.of(1996,12,24));
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/token").with(user(myUser));

		MvcResult result = this.mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

		return result.getResponse().getContentAsString();
	}
	@Test
	void tokenWhenAnonymousThenStatusIsUnauthorized() throws Exception {
		String token = "";
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/")
				.header("Authorization", "Bearer " + token);

		this.mvc.perform(requestBuilder)
				.andExpect(status().isUnauthorized());
	}

	@Test
	void tokenWithBasicThenGetToken() throws Exception {
			assertThat(getTokenForUser("user", "")).isNotEmpty();
	}

	@Test
	void adminWhenUnauthenticatedThen403() throws Exception {
		String token = getTokenForUser("","");
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin")
				.header("Authorization", "Bearer " + token);

		this.mvc.perform(requestBuilder)
				.andExpect(status().isForbidden());
	}

	@Test
	public void rootWithBasicStatusIsUnauthorized() throws Exception {
		this.mvc.perform(get("/").with(httpBasic("dvega", "password"))).andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "admin", authorities = { "ADMIN" })
	public void rootWithMockUserStatusIsOK() throws Exception {
		this.mvc.perform(get("/")).andExpect(status().isOk());
	}

}