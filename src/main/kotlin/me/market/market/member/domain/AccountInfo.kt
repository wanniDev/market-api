package me.market.market.member.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class AccountInfo {
    @Column(nullable = false)
    private val accountBank: String? = null

    @Column(nullable = false)
    private val accountNum: String? = null
}