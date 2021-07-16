package com.mywristcoin.wristcoinpos.commands

import com.mywristcoin.wristcoinpos.AbstractWristCoinPOSMessage
import com.mywristcoin.wristcoinpos.toByteArray
import com.mywristcoin.wristcoinpos.toInt
import com.taptrack.tcmptappy2.MalformedPayloadException

abstract class AbstractTopupWristbandCommand : AbstractWristCoinPOSMessage {
    var topupAmountCentavos: Int = 0

    var timeout: Byte? = null

    constructor() : super()

    @Throws(MalformedPayloadException::class)
    constructor(payload: ByteArray) : this() {
        parsePayload(payload)
    }

    constructor(topupAmountCentavos: Int) : this() {
        this.topupAmountCentavos = topupAmountCentavos
    }

    constructor(topupAmountCentavos: Int, timeout: Byte) : this() {
        this.topupAmountCentavos = topupAmountCentavos
        this.timeout = timeout
    }

    @Throws(MalformedPayloadException::class)
    final override fun parsePayload(payload: ByteArray) {
        if (payload.size < 4) {
            throw MalformedPayloadException("Payload too short")
        }

        topupAmountCentavos = payload.toInt()

        if (payload.size > 4) {
            timeout = payload[4]
        }
    }

    final override fun getPayload(): ByteArray {
        val topupAmount = topupAmountCentavos.toByteArray()

        val localTimeout = timeout

        return if (localTimeout == null) {
            topupAmount
        } else {
            byteArrayOf(*topupAmount, localTimeout)
        }
    }

}