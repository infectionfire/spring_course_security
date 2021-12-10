package com.ivanovsergei.spring.security.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

import javax.sql.DataSource;

@EnableWebSecurity//помечаем класс, как класс, ответственный за конфигурацию секьюрити, конфигурейшен аннотацию не пишем
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public DataSource dataSource;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //теперь спринг знает, как брать инфу из бд
        auth.jdbcAuthentication().dataSource(dataSource);

//
//        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();//временный метод, декодер в памяти
//        //плохая практика, подходит для тестов
//        auth.inMemoryAuthentication().withUser(userBuilder
//                .username("sergei")
//                .password("sergei")
//                .roles("EMPLOYEE"));
//        auth.inMemoryAuthentication().withUser(userBuilder
//                .username("oleg")
//                .password("oleg")
//                .roles("HR"));
//        auth.inMemoryAuthentication().withUser(userBuilder
//                .username("semen")
//                .password("semen")
//                .roles("MANAGER"));

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //блокирует возможность просмотра информации по прямым ссылкам пользователям не той роли
        //проверяет роли
        http.authorizeRequests()//запрос авторизации определенных урл
                .antMatchers("/").hasAnyRole("EMPLOYEE","HR","MANAGER")//адрес для всех ролей
                .antMatchers("/hr_info").hasRole("HR")//адрес для конкретных ролей
                // /**-обозначение, что у роли есть доступ к любому адресу далее
                .antMatchers("/manager_info/**").hasRole("MANAGER")
                .and().formLogin().permitAll();//форма логина и пароля будет запрашиваться у всех
    }
}
