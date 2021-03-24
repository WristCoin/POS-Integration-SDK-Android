package com.mywristcoin.wristcoinpos.responses

import com.mywristcoin.wristcoinpos.AbstractWristCoinPOSMessage

class SetEventIdResponse : AbstractWristCoinPOSMessage() {

    companion object {
        const val COMMAND_CODE: Byte = 0x01
    }

    override fun parsePayload(payload: ByteArray) = Unit

    override fun getPayload(): ByteArray = byteArrayOf()

    override fun getCommandCode(): Byte = COMMAND_CODE

}
