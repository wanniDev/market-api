package me.spring.market.member.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class PhoneInfo(
    @Column(nullable = false)
    val mobileCarrier: String,
    @Column(nullable = false)
    val phoneNum: String
)