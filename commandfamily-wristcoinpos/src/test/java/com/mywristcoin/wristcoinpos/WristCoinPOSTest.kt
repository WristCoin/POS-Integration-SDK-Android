package com.mywristcoin.wristcoinpos

import com.mywristcoin.wristcoinpos.commands.*
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertTrue
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

}
