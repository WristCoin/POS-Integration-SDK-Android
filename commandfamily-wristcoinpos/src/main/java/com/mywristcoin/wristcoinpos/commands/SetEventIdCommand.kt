package com.mywristcoin.wristcoinpos.commands

import androidx.annotation.Size
import com.taptrack.tcmptappy2.TCMPMessageParseException
import com.mywristcoin.wristcoinpos.AbstractWristCoinPOSMessage
import com.mywristcoin.wristcoinpos.UUID_SIZE_BYTES
import com.mywristcoin.wristcoinpos.toByteArray
import java.util.*

class SetEventIdCommand : AbstractWristCoinPOSMessage {

    companion object {
        const val COMMAND_CODE: Byte = 0x01
    }

    @Size(UUID_SIZE_BYTES)
    var eventId: ByteArray = ByteArray(UUID_SIZE_BYTES.toInt())

    constructor() : super()

    @Throws(TCMPMessageParseException::class)
    constructor(@Size(UUID_SIZE_BYTES) eventId: ByteArray) : this() {
        parsePayload(eventId)
    }

    @Throws(TCMPMessageParseException::class)
    constructor(eventId: UUID) : this() {
        val payload = eventId.toByteArray()
        parsePayload(payload)
    }

    @Throws(TCMPMessageParseException::class)
    override fun parsePayload(@Size(UUID_SIZE_BYTES) payload: ByteArray) {
        if (payload.size < 16) {
            throw TCMPMessageParseException("Payload too short")
        }

        eventId = payload
    }

    override fun getPayload(): ByteArray = eventId

    override fun getCommandCode(): Byte = COMMAND_CODE

}
