// Указываем, что этот файл является частью пакета ru.itmentor.spring.boot_security.demo.configs
package ru.itmentor.spring.boot_security.demo.configs;

// Импортируем необходимые классы и аннотации из Spring для настройки безопасности
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// Импортируем класс VolunteerDetailsService из вашего пакета, который используется для загрузки данных пользователей
import ru.itmentor.spring.boot_security.demo.service.VolunteerDetailsService;

// Аннотация @Configuration указывает, что данный класс содержит конфигурацию Spring
@Configuration
// Аннотация @EnableWebSecurity активирует безопасность на веб-уровне
@EnableWebSecurity
// Объявляем класс WebSecurityConfig, который наследует WebSecurityConfigurerAdapter для настройки безопасности
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // Объявляем поля для зависимостей
    private final VolunteerDetailsService volunteerDetailsService;
    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    // Используем аннотацию @Autowired для автоматического внедрения зависимостей через конструктор
    @Autowired
    public WebSecurityConfig(VolunteerDetailsService volunteerDetailsService, AuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.volunteerDetailsService = volunteerDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    // Переопределяем метод configure для настройки HTTP безопасности
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // Указываем, что доступ к /volunteers/volunteers_edit разрешен только пользователям с ролью ADMIN
                .antMatchers("/volunteers/volunteers_edit").hasRole("ADMIN")
                // Любые другие запросы должны быть аутентифицированы
                .anyRequest().authenticated()
                .and()
                // Настраиваем форму входа
                .formLogin()
                // Указываем, что в случае успешной аутентификации должен использоваться customAuthenticationSuccessHandler
                .successHandler(customAuthenticationSuccessHandler)
                // Разрешаем доступ ко всем пользователям
                .permitAll()
                .and()
                // Настраиваем выход из системы
                .logout()
                // Разрешаем доступ ко всем пользователям
                .permitAll();
    }

    // Используем аннотацию @Autowired для автоматической настройки глобальной аутентификации
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Указываем, что для аутентификации должен использоваться volunteerDetailsService
        auth.userDetailsService(volunteerDetailsService);
    }

    // Создаем bean для PasswordEncoder, который не шифрует пароли (используется для простоты в демонстрационных целях)
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
