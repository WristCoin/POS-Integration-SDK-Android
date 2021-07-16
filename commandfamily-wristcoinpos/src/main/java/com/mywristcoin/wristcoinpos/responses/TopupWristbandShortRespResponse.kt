package com.mywristcoin.wristcoinpos.responses

import com.mywristcoin.wristcoinpos.AbstractWristCoinPOSMessage
import com.mywristcoin.wristcoinpos.toByteArray
import com.mywristcoin.wristcoinpos.toInt
import com.taptrack.tcmptappy2.TCMPMessageParseException

class TopupWristbandShortRespResponse : AbstractWristCoinPOSMessage {
    companion object {
        const val COMMAND_CODE: Byte = 0x05
    }

    private var _remainingBalanceCentavos: Int? = null

    var remainingBalanceCentavos
        get() = _remainingBalanceCentavos
        private set(value) {
            _remainingBalanceCentavos = value
        }

    constructor() : super()

    @Throws(TCMPMessageParseException::class)
    constructor(payload: ByteArray) : this() {
        parsePayload(payload)
    }

    @Throws(TCMPMessageParseException::class)
    override fun parsePayload(payload: ByteArray) {
        if (payload.size < 4) {
            throw TCMPMessageParseException("Payload too short")
        }

        remainingBalanceCentavos = payload.toInt()
    }

    override fun getPayload(): ByteArray {
        return remainingBalanceCentavos?.toByteArray() ?: byteArrayOf()
    }

    override fun getCommandCode(): Byte = COMMAND_CODE

}