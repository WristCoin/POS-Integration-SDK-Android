package com.mywristcoin.wristcoinpos.commands

import com.taptrack.tcmptappy2.MalformedPayloadException
import com.mywristcoin.wristcoinpos.AbstractWristCoinPOSMessage
import com.mywristcoin.wristcoinpos.toByteArray
import com.mywristcoin.wristcoinpos.toInt

abstract class AbstractDebitWristbandCommand : AbstractWristCoinPOSMessage {

    var debitAmountCentavos: Int = 0

    var timeout: Byte? = null

    constructor() : super()

    @Throws(MalformedPayloadException::class)
    constructor(payload: ByteArray) : this() {
        parsePayload(payload)
    }

    constructor(debitAmountCentavos: Int) : this() {
        this.debitAmountCentavos = debitAmountCentavos
    }

    constructor(debitAmountCentavos: Int, timeout: Byte) : this() {
        this.debitAmountCentavos = debitAmountCentavos
        this.timeout = timeout
    }

    @Throws(MalformedPayloadException::class)
    final override fun parsePayload(payload: ByteArray) {
        if (payload.size < 4) {
            throw MalformedPayloadException("Payload too short")
        }

        debitAmountCentavos = payload.toInt()

        if (payload.size > 4) {
            timeout = payload[4]
        }
    }

    final override fun getPayload(): ByteArray {
        val debitAmount = debitAmountCentavos.toByteArray()

        val localTimeout = timeout

        return if (localTimeout == null) {
            debitAmount
        } else {
            byteArrayOf(*debitAmount, localTimeout)
        }
    }

}
