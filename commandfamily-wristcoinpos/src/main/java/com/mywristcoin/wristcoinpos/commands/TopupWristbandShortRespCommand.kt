package com.mywristcoin.wristcoinpos.commands

import com.taptrack.tcmptappy2.MalformedPayloadException

class TopupWristbandShortRespCommand : AbstractTopupWristbandCommand {

    companion object {
        const val COMMAND_CODE: Byte = 0x05
    }

    constructor() : super()

    @Throws(MalformedPayloadException::class)
    constructor(payload: ByteArray) : super(payload)

    constructor(topupAmountCentavos: Int) : super(topupAmountCentavos)

    constructor(topupAmountCentavos: Int, timeout: Byte) : super(topupAmountCentavos, timeout)

    override fun getCommandCode(): Byte = COMMAND_CODE

}