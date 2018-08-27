package it.giorgiopagnoni.springrestws.security

import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
        authManager: AuthenticationManager
) : BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(SecurityConstants.HEADER_STRING)
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response)
            return
        }

        val authentication: UsernamePasswordAuthenticationToken? = getAuthentication(request)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)

    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        var token = request.getHeader(SecurityConstants.HEADER_STRING)
        if (token != null) {
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "")
            val user = Jwts.parser()
                    .setSigningKey(SecurityConstants.getTokenSecret())
                    .parseClaimsJws(token)
                    .body.subject
            if (user != null) {
                return UsernamePasswordAuthenticationToken(user, null, ArrayList())
            }
        }
        return null
    }
}