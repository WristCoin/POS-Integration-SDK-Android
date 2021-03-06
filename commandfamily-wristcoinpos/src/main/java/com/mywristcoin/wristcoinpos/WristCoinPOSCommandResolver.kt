package com.mywristcoin.wristcoinpos

import androidx.annotation.Size
import com.mywristcoin.wristcoinpos.commands.*
import com.mywristcoin.wristcoinpos.responses.*
import com.taptrack.tcmptappy2.CommandFamilyMessageResolver
import com.taptrack.tcmptappy2.MalformedPayloadException
import com.taptrack.tcmptappy2.TCMPMessage

class WristCoinPOSCommandResolver : CommandFamilyMessageResolver {

    companion object {
        @JvmField
        val FAMILY_ID = byteArrayOf(0x00, 0x09)

        @Throws(IllegalArgumentException::class)
        private fun assertFamilyMatches(message: TCMPMessage) {
            if (!message.commandFamily.contentEquals(FAMILY_ID)) {
                throw IllegalArgumentException("Specified message is for a different command family")
            }
        }
    }

    @Throws(IllegalArgumentException::class, MalformedPayloadException::class)
    override fun resolveCommand(message: TCMPMessage): TCMPMessage? {
        assertFamilyMatches(message)

        val parsedMessage: TCMPMessage? = when (message.commandCode) {
            SetEventIdCommand.COMMAND_CODE -> {
                SetEventIdCommand()
            }
            GetWristbandStatusCommand.COMMAND_CODE -> {
                GetWristbandStatusCommand()
            }
            DebitWristbandShortRespCommand.COMMAND_CODE -> {
                DebitWristbandShortRespCommand()
            }
            DebitWristbandFullRespCommand.COMMAND_CODE -> {
                DebitWristbandFullRespCommand()
            }
            GetWristCoinPOSCommandFamilyVersionCommand.COMMAND_CODE -> {
                GetWristCoinPOSCommandFamilyVersionCommand()
            }
            else -> {
                null
            }
        }

        parsedMessage?.parsePayload(message.payload)
        return parsedMessage
    }

    @Throws(IllegalArgumentException::class, MalformedPayloadException::class)
    override fun resolveResponse(message: TCMPMessage): TCMPMessage? {
        assertFamilyMatches(message)

        val parsedMessage: TCMPMessage? = when (message.commandCode) {
            WristCoinPOSApplicationErrorMessage.COMMAND_CODE -> {
                WristCoinPOSApplicationErrorMessage()
            }
            SetEventIdResponse.COMMAND_CODE -> {
                SetEventIdResponse()
            }
            GetWristbandStatusResponse.COMMAND_CODE -> {
                GetWristbandStatusResponse()
            }
            DebitWristbandShortRespResponse.COMMAND_CODE -> {
                DebitWristbandShortRespResponse()
            }
            DebitWristbandFullRespResponse.COMMAND_CODE -> {
                DebitWristbandFullRespResponse()
            }
            GetWristCoinPOSCommandFamilyVersionResponse.COMMAND_CODE -> {
                GetWristCoinPOSCommandFamilyVersionResponse()
            }
            else -> {
                null
            }
        }

        parsedMessage?.parsePayload(message.payload)
        return parsedMessage
    }

    @Size(2)
    override fun getCommandFamilyId(): ByteArray = FAMILY_ID

}
