package me.market.market.member.domain

import jakarta.persistence.*

@Entity
class Member(
    @Embedded
    val accountInfo: AccountInfo,
    @Embedded
    val phoneInfo: PhoneInfo,
    @Column(nullable = false)
    val passwd: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}