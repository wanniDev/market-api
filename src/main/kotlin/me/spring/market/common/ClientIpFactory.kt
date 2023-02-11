package me.spring.market.common

import jakarta.servlet.http.HttpServletRequest

class ClientIpFactory {

    companion object {
        private val IP_HEADERS = arrayOf(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
        )

        fun getRequestIP(request: HttpServletRequest): String {
            for (header in IP_HEADERS) {
                val value = request.getHeader(header)
                if (value == null || value.isEmpty()) {
                    continue
                }
                val parts = value.split("\\s*,\\s*")
                return parts[0]
            }
            return request.remoteAddr
        }
    }
}