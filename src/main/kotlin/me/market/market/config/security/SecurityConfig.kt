package me.market.market.config.security

import me.market.market.common.API_URI_PREFIX
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtHelper(jwtProperty: JwtProperty): JWTHelper {
        return JWTHelper(jwtProperty.issuer,
            jwtProperty.clientSecret,
            jwtProperty.expirySeconds,
            jwtProperty.refreshSeconds)
    }

    @Bean
    fun jwtAuthenticationProvider(): JwtAuthenticationProvider? {
        return JwtAuthenticationProvider()
    }

    @Bean
    @Profile(value = ["feature.local.memberinfo", "default"])
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider())
        return authenticationManagerBuilder.build()
    }

    @Bean
    @Profile(value = ["feature.local.memberinfo", "default"])
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http.csrf().disable()
        http.headers().disable()
        http.authorizeHttpRequests()
            .requestMatchers("$API_URI_PREFIX/test")
            .hasRole(Role.USER.value)
            .anyRequest().permitAll()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.formLogin().disable()
        return http.build()
    }


}