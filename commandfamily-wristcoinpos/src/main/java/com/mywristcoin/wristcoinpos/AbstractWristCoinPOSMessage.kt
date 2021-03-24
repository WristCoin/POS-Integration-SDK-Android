package com.mywristcoin.wristcoinpos

import com.taptrack.tcmptappy2.AbstractTCMPMessage

abstract class AbstractWristCoinPOSMessage : AbstractTCMPMessage() {

    final override fun getCommandFamily(): ByteArray = WristCoinPOSCommandResolver.FAMILY_ID

}
