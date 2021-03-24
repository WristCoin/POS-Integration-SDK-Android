package com.mywristcoin.wristcoinpos.responses

import com.taptrack.tcmptappy2.TCMPMessageParseException
import com.mywristcoin.wristcoinpos.AbstractWristCoinPOSMessage

class DebitWristbandFullRespResponse : AbstractWristCoinPOSMessage {

    companion object {
        const val COMMAND_CODE: Byte = 0x04
    }

    private var _wristbandStatusTlv: ByteArray = byteArrayOf()

    var wristbandStatusTlv: ByteArray
        get() = _wristbandStatusTlv
        private set(value) {
            _wristbandStatusTlv = value
        }

    constructor() : super()

    @Throws(TCMPMessageParseException::class)
    constructor(payload: ByteArray) : this() {
        parsePayload(payload)
    }

    @Throws(TCMPMessageParseException::class)
    override fun parsePayload(payload: ByteArray) {
        if (payload.size < 3) {
            throw TCMPMessageParseException("Payload too short")
        }

        wristbandStatusTlv = payload
    }

    override fun getPayload(): ByteArray = wristbandStatusTlv

    override fun getCommandCode(): Byte = COMMAND_CODE

}
