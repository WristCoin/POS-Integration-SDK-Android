package com.mywristcoin.wristcoinpos

import com.mywristcoin.wristcoinpos.commands.*
import com.mywristcoin.wristcoinpos.responses.DebitWristbandFullRespResponse
import com.mywristcoin.wristcoinpos.responses.GetWristbandStatusResponse
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class WristCoinPOSTest {

    @Test
    fun setEventIdCommand_ConstructedFromUUID_getEventId_ReturnsEquivalentBEByteArray() {
        val expected = byteArrayOf(
            0xA6.toByte(), 0x49, 0xE0.toByte(), 0xF4.toByte(),
            0x0B, 0xC0.toByte(),
            0x11, 0xEA.toByte(),
            0x84.toByte(), 0xD8.toByte(),
            0xAF.toByte(), 0xBA.toByte(), 0xB2.toByte(), 0xCF.toByte(), 0xCB.toByte(), 0x2A
        )

        val command = SetEventIdCommand(UUID.fromString("a649e0f4-0bc0-11ea-84d8-afbab2cfcb2a"))

        assertArrayEquals(command.eventId, expected)
    }

    @Test
    fun getWristbandStatusCommand_ConstructedWithTimeout_Payload_ReturnsEquivalentByteArray() {
        val expected = byteArrayOf(0xFE.toByte())

        val command = GetWristbandStatusCommand(254.toByte())

        assertArrayEquals(command.payload, expected)
    }

    @Test
    fun getWristbandStatusCommand_ConstructedWithoutTimeout_Payload_IsEmpty() {
        val command = GetWristbandStatusCommand()

        assertTrue(command.payload.isEmpty())
    }

    @Test
    fun debitWristbandCommand_ConstructedWithTimeout_Payload_ReturnsEquivalentSizeFiveByteArray() {
        val expected = byteArrayOf(0x20, 0xA5.toByte(), 0x17, 0xE9.toByte(), 0x21)

        val command: AbstractDebitWristbandCommand = DebitWristbandShortRespCommand(
            debitAmountCentavos = 547690473,
            timeout = 33
        )

        assertArrayEquals(command.payload, expected)
    }

    @Test
    fun debitWristbandCommand_ConstructedWithoutTimeout_Payload_ReturnsEquivalentSizeFourByteArray() {
        val expected = byteArrayOf(0x20, 0xA5.toByte(), 0x17, 0xE9.toByte())

        val command: AbstractDebitWristbandCommand = DebitWristbandShortRespCommand(
            debitAmountCentavos = 547690473
        )

        assertArrayEquals(command.payload, expected)
    }

    @Test
    fun getWristCoinPOSCommandFamilyVersionCommand_Payload_IsEmpty() {
        val command = GetWristCoinPOSCommandFamilyVersionCommand()
        assertTrue(command.payload.isEmpty())
    }

    @Test
    fun getWristbandStatusResponse_logical_fields_value_zero(){
        var payload = byteArrayOf()
        val uid: ByteArray = byteArrayOf(0x0C, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
        val cardVersion = byteArrayOf(0x0E, 0x02, 0x01, 0x05)
        val offlineCreditTotal = byteArrayOf(0x0F, 0x04, 0x00, 0x00, 0x00, 0x00)
        val creditTxCount = byteArrayOf(0x10, 0x04, 0x00, 0x00, 0x00, 0x00)
        val debitTotal = byteArrayOf(0x11, 0x04, 0x00, 0x00, 0x00, 0x00)
        val debitTxCount = byteArrayOf(0x12, 0x04, 0x00, 0x00, 0x00, 0x00)
        val reversalTotal = byteArrayOf(0x14, 0x04, 0x00, 0x00, 0x00, 0x00)
        val reversalApprovedCount = byteArrayOf(0x15, 0x04, 0x00, 0x00, 0x00, 0x00)
        val reversalDeniedCount = byteArrayOf(0x16, 0x04, 0x00, 0x00, 0x00, 0x00)
        val reversalDecisionCount = byteArrayOf(0x17, 0x04, 0x00, 0x00, 0x00, 0x00)
        val reversalRequestCount = byteArrayOf(0x06, 0x04, 0x00, 0x00, 0x00, 0x00)
        val closeoutCount = byteArrayOf(0x13, 0x04, 0x00, 0x00, 0x00, 0x00)
        val aeonCount = byteArrayOf(0x18, 0x04, 0x00, 0x00, 0x00, 0x00)
        val deactivatedCount = byteArrayOf(0x2B, 0x04, 0x00, 0x00, 0x00, 0x00)
        val didReadReversals = byteArrayOf(0x19, 0x01, 0x00)
        val onlineCreditTotal = byteArrayOf(0x2F, 0x04, 0x00, 0x00, 0x00, 0x00)
        val scratchState = byteArrayOf(0x30, 0x01, 0x00)
        val topupConfigurationSupport = byteArrayOf(0x31, 0x01, 0x00)

        val rewardPointCreditTotal = byteArrayOf(0x37, 0x04, 0x00, 0x00, 0x00, 0x00)
        val rewardPointCreditTxCount = byteArrayOf(0x38, 0x04, 0x00, 0x00, 0x00, 0x00)
        val rewardPointDebitTotal = byteArrayOf(0x39, 0x04, 0x00, 0x00, 0x00, 0x00)
        val rewardPointDebitTxCount = byteArrayOf(0x40, 0x04, 0x00, 0x00, 0x00, 0x00)
        val preloadedCreditTotal = byteArrayOf(0x36, 0x04, 0x00, 0x00, 0x00, 0x00)
        val preloadedPointsTotal = byteArrayOf(0x46, 0x04, 0x00, 0x00, 0x00, 0x00)

        payload = uid + cardVersion + offlineCreditTotal + creditTxCount + debitTotal + debitTxCount + reversalTotal + reversalApprovedCount +
                reversalDeniedCount + reversalDecisionCount + reversalRequestCount + closeoutCount + aeonCount + deactivatedCount + didReadReversals +
                onlineCreditTotal + scratchState + topupConfigurationSupport + rewardPointCreditTotal + rewardPointCreditTxCount +
                rewardPointDebitTotal + rewardPointDebitTxCount + preloadedCreditTotal + preloadedPointsTotal

        val response = GetWristbandStatusResponse(payload)
        assertArrayEquals(response.getWristbandState().uid, byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00))
        assertEquals(response.getWristbandState().majorVersion, 0x01)
        assertEquals(response.getWristbandState().minorVersion, 0x05)
        assertEquals(response.getWristbandState().offlineCreditTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().debitTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().debitTxCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().reversalTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().reversalApprovedCount,byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().reversalDeniedCount,byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().reversalDecisionCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().reversalRequestCount,byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().closeoutCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().aeonCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().deactivatedCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().didReadReversals, false)
        assertEquals(response.getWristbandState().onlineCreditTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().scratchState, AppScratchState.NotScratchable)
        assertEquals(response.getWristbandState().topupConfigurationSupport, AppTopupConfigurationSupport.OfflineOnly)

        assertEquals(response.getWristbandState().rewardPointCreditTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().rewardPointCreditTxCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().rewardPointDebitTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().rewardPointDebitTxCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().preloadedCreditTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getWristbandState().preloadedPointsTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
    }
    @Test
    fun DebitWristbandFullRespResponse_logical_fields_value_zero(){
        var payload: ByteArray
        val uid: ByteArray = byteArrayOf(0x0C, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
        val cardVersion = byteArrayOf(0x0E, 0x02, 0x01, 0x05)
        val offlineCreditTotal = byteArrayOf(0x0F, 0x04, 0x00, 0x00, 0x00, 0x00)
        val creditTxCount = byteArrayOf(0x10, 0x04, 0x00, 0x00, 0x00, 0x00)
        val debitTotal = byteArrayOf(0x11, 0x04, 0x00, 0x00, 0x00, 0x00)
        val debitTxCount = byteArrayOf(0x12, 0x04, 0x00, 0x00, 0x00, 0x00)
        val reversalTotal = byteArrayOf(0x14, 0x04, 0x00, 0x00, 0x00, 0x00)
        val reversalApprovedCount = byteArrayOf(0x15, 0x04, 0x00, 0x00, 0x00, 0x00)
        val reversalDeniedCount = byteArrayOf(0x16, 0x04, 0x00, 0x00, 0x00, 0x00)
        val reversalDecisionCount = byteArrayOf(0x17, 0x04, 0x00, 0x00, 0x00, 0x00)
        val reversalRequestCount = byteArrayOf(0x06, 0x04, 0x00, 0x00, 0x00, 0x00)
        val closeoutCount = byteArrayOf(0x13, 0x04, 0x00, 0x00, 0x00, 0x00)
        val aeonCount = byteArrayOf(0x18, 0x04, 0x00, 0x00, 0x00, 0x00)
        val deactivatedCount = byteArrayOf(0x2B, 0x04, 0x00, 0x00, 0x00, 0x00)
        val didReadReversals = byteArrayOf(0x19, 0x01, 0x00)
        val onlineCreditTotal = byteArrayOf(0x2F, 0x04, 0x00, 0x00, 0x00, 0x00)
        val scratchState = byteArrayOf(0x30, 0x01, 0x00)
        val topupConfigurationSupport = byteArrayOf(0x31, 0x01, 0x00)

        val rewardPointCreditTotal = byteArrayOf(0x37, 0x04, 0x00, 0x00, 0x00, 0x00)
        val rewardPointCreditTxCount = byteArrayOf(0x38, 0x04, 0x00, 0x00, 0x00, 0x00)
        val rewardPointDebitTotal = byteArrayOf(0x39, 0x04, 0x00, 0x00, 0x00, 0x00)
        val rewardPointDebitTxCount = byteArrayOf(0x40, 0x04, 0x00, 0x00, 0x00, 0x00)
        val preloadedCreditTotal = byteArrayOf(0x36, 0x04, 0x00, 0x00, 0x00, 0x00)
        val preloadedPointsTotal = byteArrayOf(0x46, 0x04, 0x00, 0x00, 0x00, 0x00)

        payload = uid + cardVersion + offlineCreditTotal + creditTxCount + debitTotal + debitTxCount + reversalTotal + reversalApprovedCount +
                reversalDeniedCount + reversalDecisionCount + reversalRequestCount + closeoutCount + aeonCount + deactivatedCount + didReadReversals +
                onlineCreditTotal + scratchState + topupConfigurationSupport + rewardPointCreditTotal + rewardPointCreditTxCount +
                rewardPointDebitTotal + rewardPointDebitTxCount + preloadedCreditTotal + preloadedPointsTotal

        val response = DebitWristbandFullRespResponse(payload)
        assertArrayEquals(response.getResultingWristbandState().uid, byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00))
        assertEquals(response.getResultingWristbandState().majorVersion, 0x01)
        assertEquals(response.getResultingWristbandState().minorVersion, 0x05)
        assertEquals(response.getResultingWristbandState().offlineCreditTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().debitTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().debitTxCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().reversalTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().reversalApprovedCount,byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().reversalDeniedCount,byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().reversalDecisionCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().reversalRequestCount,byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().closeoutCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().aeonCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().deactivatedCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().didReadReversals, false)
        assertEquals(response.getResultingWristbandState().onlineCreditTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().scratchState, AppScratchState.NotScratchable)
        assertEquals(response.getResultingWristbandState().topupConfigurationSupport, AppTopupConfigurationSupport.OfflineOnly)

        assertEquals(response.getResultingWristbandState().rewardPointCreditTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().rewardPointCreditTxCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().rewardPointDebitTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().rewardPointDebitTxCount, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().preloadedCreditTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().preloadedPointsTotal, byteArrayOf(0x00, 0x00, 0x00, 0x00).toInt())
        assertEquals(response.getResultingWristbandState().rewardBalance, 0)
    }
    @Test
    fun getWristbandStatusResponse_logical_fields_nonzero_values(){
        var payload = byteArrayOf()
        val uid: ByteArray = byteArrayOf(0x0C, 0x07, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x00)
        val cardVersion = byteArrayOf(0x0E, 0x02, 0x01, 0x05)
        val offlineCreditTotal = byteArrayOf(0x0F, 0x04, 0x01, 0x02, 0x03, 0x04)
        val creditTxCount = byteArrayOf(0x10, 0x04, 0x01, 0x02, 0x03, 0x04)
        val debitTotal = byteArrayOf(0x11, 0x04, 0x01, 0x02, 0x03, 0x04)
        val debitTxCount = byteArrayOf(0x12, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalTotal = byteArrayOf(0x14, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalApprovedCount = byteArrayOf(0x15, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalDeniedCount = byteArrayOf(0x16, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalDecisionCount = byteArrayOf(0x17, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalRequestCount = byteArrayOf(0x06, 0x04, 0x01, 0x02, 0x03, 0x04)
        val closeoutCount = byteArrayOf(0x13, 0x04, 0x01, 0x02, 0x03, 0x04)
        val aeonCount = byteArrayOf(0x18, 0x04, 0x01, 0x02, 0x03, 0x04)
        val deactivatedCount = byteArrayOf(0x2B, 0x04, 0x01, 0x02, 0x03, 0x04)
        val didReadReversals = byteArrayOf(0x19, 0x01, 0x00)
        val onlineCreditTotal = byteArrayOf(0x2F, 0x04, 0x01, 0x02, 0x03, 0x04)
        val scratchState = byteArrayOf(0x30, 0x01, 0x00)
        val topupConfigurationSupport = byteArrayOf(0x31, 0x01, 0x00)

        val rewardPointCreditTotal = byteArrayOf(0x37, 0x04, 0x01, 0x02, 0x03, 0x04)
        val rewardPointCreditTxCount = byteArrayOf(0x38, 0x04, 0x01, 0x02, 0x03, 0x04)
        val rewardPointDebitTotal = byteArrayOf(0x39, 0x04, 0x01, 0x02, 0x03, 0x04)
        val rewardPointDebitTxCount = byteArrayOf(0x40, 0x04, 0x01, 0x02, 0x03, 0x04)
        val preloadedCreditTotal = byteArrayOf(0x36, 0x04, 0x01, 0x02, 0x03, 0x04)
        val preloadedPointsTotal = byteArrayOf(0x46, 0x04, 0x01, 0x02, 0x03, 0x04)

        payload = uid + cardVersion + offlineCreditTotal + creditTxCount + debitTotal + debitTxCount + reversalTotal + reversalApprovedCount +
                reversalDeniedCount + reversalDecisionCount + reversalRequestCount + closeoutCount + aeonCount + deactivatedCount + didReadReversals +
                onlineCreditTotal + scratchState + topupConfigurationSupport + rewardPointCreditTotal + rewardPointCreditTxCount +
                rewardPointDebitTotal + rewardPointDebitTxCount + preloadedCreditTotal + preloadedPointsTotal

        val response = GetWristbandStatusResponse(payload)
        assertArrayEquals(response.getWristbandState().uid, byteArrayOf(0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x00))
        assertEquals(response.getWristbandState().majorVersion, 0x01)
        assertEquals(response.getWristbandState().minorVersion, 0x05)
        assertEquals(response.getWristbandState().offlineCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().debitTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().debitTxCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().reversalTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().reversalApprovedCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().reversalDeniedCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().reversalDecisionCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().reversalRequestCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().closeoutCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().aeonCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().deactivatedCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().didReadReversals, false)
        assertEquals(response.getWristbandState().onlineCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().scratchState, AppScratchState.NotScratchable)
        assertEquals(response.getWristbandState().topupConfigurationSupport, AppTopupConfigurationSupport.OfflineOnly)

        assertEquals(response.getWristbandState().rewardPointCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().rewardPointCreditTxCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().rewardPointDebitTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().rewardPointDebitTxCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().preloadedCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().preloadedPointsTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
    }
    @Test
    fun DebitWristbandFullRespResponse_logical_fields_nonzero_values(){
        var payload = byteArrayOf()
        val uid: ByteArray = byteArrayOf(0x0C, 0x07, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x00)
        val cardVersion = byteArrayOf(0x0E, 0x02, 0x01, 0x05)
        val offlineCreditTotal = byteArrayOf(0x0F, 0x04, 0x01, 0x02, 0x03, 0x04)
        val creditTxCount = byteArrayOf(0x10, 0x04, 0x01, 0x02, 0x03, 0x04)
        val debitTotal = byteArrayOf(0x11, 0x04, 0x01, 0x02, 0x03, 0x04)
        val debitTxCount = byteArrayOf(0x12, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalTotal = byteArrayOf(0x14, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalApprovedCount = byteArrayOf(0x15, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalDeniedCount = byteArrayOf(0x16, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalDecisionCount = byteArrayOf(0x17, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalRequestCount = byteArrayOf(0x06, 0x04, 0x01, 0x02, 0x03, 0x04)
        val closeoutCount = byteArrayOf(0x13, 0x04, 0x01, 0x02, 0x03, 0x04)
        val aeonCount = byteArrayOf(0x18, 0x04, 0x01, 0x02, 0x03, 0x04)
        val deactivatedCount = byteArrayOf(0x2B, 0x04, 0x01, 0x02, 0x03, 0x04)
        val didReadReversals = byteArrayOf(0x19, 0x01, 0x00)
        val onlineCreditTotal = byteArrayOf(0x2F, 0x04, 0x01, 0x02, 0x03, 0x04)
        val scratchState = byteArrayOf(0x30, 0x01, 0x00)
        val topupConfigurationSupport = byteArrayOf(0x31, 0x01, 0x00)

        val rewardPointCreditTotal = byteArrayOf(0x37, 0x04, 0x01, 0x02, 0x03, 0x04)
        val rewardPointCreditTxCount = byteArrayOf(0x38, 0x04, 0x01, 0x02, 0x03, 0x04)
        val rewardPointDebitTotal = byteArrayOf(0x39, 0x04, 0x01, 0x02, 0x03, 0x04)
        val rewardPointDebitTxCount = byteArrayOf(0x40, 0x04, 0x01, 0x02, 0x03, 0x04)
        val preloadedCreditTotal = byteArrayOf(0x36, 0x04, 0x01, 0x02, 0x03, 0x04)
        val preloadedPointsTotal = byteArrayOf(0x46, 0x04, 0x01, 0x02, 0x03, 0x04)

        payload = uid + cardVersion + offlineCreditTotal + creditTxCount + debitTotal + debitTxCount + reversalTotal + reversalApprovedCount +
                reversalDeniedCount + reversalDecisionCount + reversalRequestCount + closeoutCount + aeonCount + deactivatedCount + didReadReversals +
                onlineCreditTotal + scratchState + topupConfigurationSupport + rewardPointCreditTotal + rewardPointCreditTxCount +
                rewardPointDebitTotal + rewardPointDebitTxCount + preloadedCreditTotal + preloadedPointsTotal

        val response = DebitWristbandFullRespResponse(payload)
        assertArrayEquals(response.getResultingWristbandState().uid, byteArrayOf(0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x00))
        assertEquals(response.getResultingWristbandState().majorVersion, 0x01)
        assertEquals(response.getResultingWristbandState().minorVersion, 0x05)
        assertEquals(response.getResultingWristbandState().offlineCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().debitTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().debitTxCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().reversalTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().reversalApprovedCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().reversalDeniedCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().reversalDecisionCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().reversalRequestCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().closeoutCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().aeonCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().deactivatedCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().didReadReversals, false)
        assertEquals(response.getResultingWristbandState().onlineCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().scratchState, AppScratchState.NotScratchable)
        assertEquals(response.getResultingWristbandState().topupConfigurationSupport, AppTopupConfigurationSupport.OfflineOnly)

        assertEquals(response.getResultingWristbandState().rewardPointCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().rewardPointCreditTxCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().rewardPointDebitTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().rewardPointDebitTxCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().preloadedCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().preloadedPointsTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
    }

    @Test
    fun getWristbandStatusResponse_logical_fields_no_preloaded_points(){
        var payload = byteArrayOf()
        val uid: ByteArray = byteArrayOf(0x0C, 0x07, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x00)
        val cardVersion = byteArrayOf(0x0E, 0x02, 0x01, 0x05)
        val offlineCreditTotal = byteArrayOf(0x0F, 0x04, 0x01, 0x02, 0x03, 0x04)
        val creditTxCount = byteArrayOf(0x10, 0x04, 0x01, 0x02, 0x03, 0x04)
        val debitTotal = byteArrayOf(0x11, 0x04, 0x01, 0x02, 0x03, 0x04)
        val debitTxCount = byteArrayOf(0x12, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalTotal = byteArrayOf(0x14, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalApprovedCount = byteArrayOf(0x15, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalDeniedCount = byteArrayOf(0x16, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalDecisionCount = byteArrayOf(0x17, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalRequestCount = byteArrayOf(0x06, 0x04, 0x01, 0x02, 0x03, 0x04)
        val closeoutCount = byteArrayOf(0x13, 0x04, 0x01, 0x02, 0x03, 0x04)
        val aeonCount = byteArrayOf(0x18, 0x04, 0x01, 0x02, 0x03, 0x04)
        val deactivatedCount = byteArrayOf(0x2B, 0x04, 0x01, 0x02, 0x03, 0x04)
        val didReadReversals = byteArrayOf(0x19, 0x01, 0x00)
        val onlineCreditTotal = byteArrayOf(0x2F, 0x04, 0x01, 0x02, 0x03, 0x04)
        val scratchState = byteArrayOf(0x30, 0x01, 0x00)
        val topupConfigurationSupport = byteArrayOf(0x31, 0x01, 0x00)

        val rewardPointCreditTotal = byteArrayOf(0x37, 0x04, 0x01, 0x02, 0x03, 0x04)
        val rewardPointCreditTxCount = byteArrayOf(0x38, 0x04, 0x01, 0x02, 0x03, 0x04)
        val rewardPointDebitTotal = byteArrayOf(0x39, 0x04, 0x01, 0x02, 0x03, 0x04)
        val rewardPointDebitTxCount = byteArrayOf(0x40, 0x04, 0x01, 0x02, 0x03, 0x04)
        val preloadedCreditTotal = byteArrayOf(0x36, 0x04, 0x01, 0x02, 0x03, 0x04)

        payload = uid + cardVersion + offlineCreditTotal + creditTxCount + debitTotal + debitTxCount + reversalTotal + reversalApprovedCount +
                reversalDeniedCount + reversalDecisionCount + reversalRequestCount + closeoutCount + aeonCount + deactivatedCount + didReadReversals +
                onlineCreditTotal + scratchState + topupConfigurationSupport + rewardPointCreditTotal + rewardPointCreditTxCount +
                rewardPointDebitTotal + rewardPointDebitTxCount + preloadedCreditTotal

        val response = GetWristbandStatusResponse(payload)
        assertArrayEquals(response.getWristbandState().uid, byteArrayOf(0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x00))
        assertEquals(response.getWristbandState().majorVersion, 0x01)
        assertEquals(response.getWristbandState().minorVersion, 0x05)
        assertEquals(response.getWristbandState().offlineCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().debitTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().debitTxCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().reversalTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().reversalApprovedCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().reversalDeniedCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().reversalDecisionCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().reversalRequestCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().closeoutCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().aeonCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().deactivatedCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().didReadReversals, false)
        assertEquals(response.getWristbandState().onlineCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().scratchState, AppScratchState.NotScratchable)
        assertEquals(response.getWristbandState().topupConfigurationSupport, AppTopupConfigurationSupport.OfflineOnly)

        assertEquals(response.getWristbandState().rewardPointCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().rewardPointCreditTxCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().rewardPointDebitTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().rewardPointDebitTxCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getWristbandState().preloadedCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertNull(response.getWristbandState().preloadedPointsTotal)
    }
    @Test
    fun DebitWristbandFullRespResponse_logical_fields_no_preloaded_points(){
        var payload = byteArrayOf()
        val uid: ByteArray = byteArrayOf(0x0C, 0x07, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x00)
        val cardVersion = byteArrayOf(0x0E, 0x02, 0x01, 0x05)
        val offlineCreditTotal = byteArrayOf(0x0F, 0x04, 0x01, 0x02, 0x03, 0x04)
        val creditTxCount = byteArrayOf(0x10, 0x04, 0x01, 0x02, 0x03, 0x04)
        val debitTotal = byteArrayOf(0x11, 0x04, 0x01, 0x02, 0x03, 0x04)
        val debitTxCount = byteArrayOf(0x12, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalTotal = byteArrayOf(0x14, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalApprovedCount = byteArrayOf(0x15, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalDeniedCount = byteArrayOf(0x16, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalDecisionCount = byteArrayOf(0x17, 0x04, 0x01, 0x02, 0x03, 0x04)
        val reversalRequestCount = byteArrayOf(0x06, 0x04, 0x01, 0x02, 0x03, 0x04)
        val closeoutCount = byteArrayOf(0x13, 0x04, 0x01, 0x02, 0x03, 0x04)
        val aeonCount = byteArrayOf(0x18, 0x04, 0x01, 0x02, 0x03, 0x04)
        val deactivatedCount = byteArrayOf(0x2B, 0x04, 0x01, 0x02, 0x03, 0x04)
        val didReadReversals = byteArrayOf(0x19, 0x01, 0x00)
        val onlineCreditTotal = byteArrayOf(0x2F, 0x04, 0x01, 0x02, 0x03, 0x04)
        val scratchState = byteArrayOf(0x30, 0x01, 0x00)
        val topupConfigurationSupport = byteArrayOf(0x31, 0x01, 0x00)

        val rewardPointCreditTotal = byteArrayOf(0x37, 0x04, 0x01, 0x02, 0x03, 0x04)
        val rewardPointCreditTxCount = byteArrayOf(0x38, 0x04, 0x01, 0x02, 0x03, 0x04)
        val rewardPointDebitTotal = byteArrayOf(0x39, 0x04, 0x01, 0x02, 0x03, 0x03)
        val rewardPointDebitTxCount = byteArrayOf(0x40, 0x04, 0x01, 0x02, 0x03, 0x04)
        val preloadedCreditTotal = byteArrayOf(0x36, 0x04, 0x01, 0x02, 0x03, 0x04)

        payload = uid + cardVersion + offlineCreditTotal + creditTxCount + debitTotal + debitTxCount + reversalTotal + reversalApprovedCount +
                reversalDeniedCount + reversalDecisionCount + reversalRequestCount + closeoutCount + aeonCount + deactivatedCount + didReadReversals +
                onlineCreditTotal + scratchState + topupConfigurationSupport + rewardPointCreditTotal + rewardPointCreditTxCount +
                rewardPointDebitTotal + rewardPointDebitTxCount + preloadedCreditTotal

        val response = DebitWristbandFullRespResponse(payload)
        assertArrayEquals(response.getResultingWristbandState().uid, byteArrayOf(0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x00))
        assertEquals(response.getResultingWristbandState().majorVersion, 0x01)
        assertEquals(response.getResultingWristbandState().minorVersion, 0x05)
        assertEquals(response.getResultingWristbandState().offlineCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().debitTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().debitTxCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().reversalTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().reversalApprovedCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().reversalDeniedCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().reversalDecisionCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().reversalRequestCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().closeoutCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().aeonCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().deactivatedCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().didReadReversals, false)
        assertEquals(response.getResultingWristbandState().onlineCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().scratchState, AppScratchState.NotScratchable)
        assertEquals(response.getResultingWristbandState().topupConfigurationSupport, AppTopupConfigurationSupport.OfflineOnly)

        assertEquals(response.getResultingWristbandState().rewardPointCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().rewardPointCreditTxCount, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().rewardPointDebitTotal, byteArrayOf(0x01, 0x02, 0x03, 0x03).toInt())
        assertEquals(response.getResultingWristbandState().rewardPointDebitTxCount,byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertEquals(response.getResultingWristbandState().preloadedCreditTotal, byteArrayOf(0x01, 0x02, 0x03, 0x04).toInt())
        assertNull(response.getResultingWristbandState().preloadedPointsTotal)
    }
}
