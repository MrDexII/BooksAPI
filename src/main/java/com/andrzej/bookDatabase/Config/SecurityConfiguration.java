package com.andrzej.bookDatabase.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        addDefaultAdminUser();
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .antMatchers("/book/**").hasAnyAuthority("ADMIN", "USER")
                .anyRequest().authenticated()
                .and().csrf().disable()
                .formLogin()
                .loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/home")
                .usernameParameter("email").passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

    private void addDefaultAdminUser() throws SQLException {
        String sql = "SELECT * FROM books.user_role;";
        try (ResultSet resultSet = dataSource.getConnection().createStatement().executeQuery(sql)) {
            if (!resultSet.next()) {
                String password = "'" + bCryptPasswordEncoder.encode("admin") + "'";
                try (Statement statement = dataSource.getConnection().createStatement()) {
                    statement.executeUpdate("REPLACE INTO `users` VALUES (1,1,'admin','admin','admin'," + password + ");\n");
                    statement.executeUpdate("REPLACE INTO `user_role` VALUES (1,1),(1,2);");

                    password = "'" + bCryptPasswordEncoder.encode("user") + "'";
                    statement.executeUpdate("REPLACE INTO `users` VALUES (2,1,'user','user','user'," + password + ");\n");
                    statement.executeUpdate("REPLACE INTO `user_role` VALUES (2,2);");
                }
            }
        }
    }

}
