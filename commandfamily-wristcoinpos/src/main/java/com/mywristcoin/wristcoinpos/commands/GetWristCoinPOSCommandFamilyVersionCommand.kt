package com.mywristcoin.wristcoinpos.commands

import com.mywristcoin.wristcoinpos.AbstractWristCoinPOSMessage

class GetWristCoinPOSCommandFamilyVersionCommand : AbstractWristCoinPOSMessage() {

    companion object {
        const val COMMAND_CODE: Byte = 0xFF.toByte()
    }

    override fun parsePayload(payload: ByteArray) = Unit

    override fun getPayload(): ByteArray = byteArrayOf()

    override fun getCommandCode(): Byte = COMMAND_CODE

}
