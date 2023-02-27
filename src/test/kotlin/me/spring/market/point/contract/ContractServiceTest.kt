package me.spring.market.point.contract

import me.spring.market.AbstractTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ContractServiceTest @Autowired constructor(
    private val contractService: ContractService,
    private val scFuncService: SCFuncService
): AbstractTest() {

    @Test
    @DisplayName("스마트 컨트랙트 배포 테스트")
    fun deploy() {
        val abiName = "MyERC20"
        val result = contractService.deploy(abiName, "MyERC20", "ETW", 18)
        assertThat(result).isEqualTo("success")
    }

    @Test
    @DisplayName("토큰 발행 테스트")
    fun mint() {
        val privateKey = "0x6acf71b90460c409d7cb26e7fdac0f19bc00efcdc2ee48e9758560d0e9793ead"
        val address = "0x411d2AbD5953091F62e514066c8Df42A13C381B8"
        val result = scFuncService.mint(privateKey, address)
        assertThat(result).isEqualTo("success")
    }
}