package me.spring.market.member.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class AccountInfo(
    @Column(nullable = false)
    val accountBank: String,

    @Column(nullable = false)
    val accountNum: String
)