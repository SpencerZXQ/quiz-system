package com.zhxq;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
  protected final Log logger = LogFactory.getLog(getClass());
  @SuppressWarnings("SpringJavaAutowiringInspection")
  @Autowired
  private DataSource dataSource;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/", "/login", "/register", "/rule").permitAll()
      .antMatchers("/css/**", "/fonts/**", "/js/**", "/img/**").permitAll()
            .antMatchers("/rank").permitAll()
            .antMatchers("/exam").hasRole("USER")
      .antMatchers("/template.xlsx", "/manage", "/search", "/import", "/delete","/usermanage","/adduser","/searchuser","/deleteuser").hasRole("ADMIN")
      .anyRequest().authenticated()
      .and().formLogin().loginPage("/login").defaultSuccessUrl("/profile") //登录后访问
      .failureHandler(new UsernameStoringUrlAuthenticationFailureHandler("/login?error"))
      .and().rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7)
      .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
      logger.info("yes");
    auth.jdbcAuthentication().dataSource(dataSource)
      .usersByUsernameQuery("select username, password, true from User where username = ?")
      .authoritiesByUsernameQuery("select username, authority from User where username = ?")
      .passwordEncoder(new BCryptPasswordEncoder());
  }

  private class UsernameStoringUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    UsernameStoringUrlAuthenticationFailureHandler(String defaultFailureUrl) {
      super(defaultFailureUrl);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
      logger.info("set username to " + request.getParameter("username"));
      logger.info(request.getParameter("password"));
      request.getSession(true).setAttribute("lastusername", request.getParameter("username"));
      super.onAuthenticationFailure(request, response, exception);
    }

  }
}
