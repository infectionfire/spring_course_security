package com.ivanovsergei.spring.security.configuration;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@EnableWebSecurity//помечаем класс, как класс, ответственный за конфигурацию секьюрити, конфигурейшен аннотацию не пишем
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();//временный метод, декодер в памяти
        //плохая практика, подходит для тестов
        auth.inMemoryAuthentication().withUser(userBuilder
                .username("sergei")
                .password("sergei")
                .roles("EMPLOYEE"));
        auth.inMemoryAuthentication().withUser(userBuilder
                .username("oleg")
                .password("oleg")
                .roles("HR"));
        auth.inMemoryAuthentication().withUser(userBuilder
                .username("semen")
                .password("semen")
                .roles("MANAGER"));
    }
}
