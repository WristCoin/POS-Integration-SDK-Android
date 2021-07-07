# WristCoinPOS Integration SDK for Android

[![Version](https://img.shields.io/maven-central/v/com.mywristcoin/commandfamily-wristcoinpos)](https://mvnrepository.com/artifact/com.mywristcoin)
[![Kotlin](https://img.shields.io/badge/kotlin-1.4.31-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![License](https://img.shields.io/github/license/WristCoin/POS-Integration-SDK-Android)](https://github.com/WristCoin/POS-Integration-SDK-Android/blob/master/LICENSE)

## Introduction

This project is an SDK to allow POS vendors to integrate WristCoin contactless cashless payments and accept WristCoin wallets as a form of payment.

A prerequisite to using this SDK is having the WristCoin external terminal that accepts NFC payments using WristCoin wristbands/cards/fobs.  The terminal abstracts all of the NFC details away from your POS application.  Terminals exist in both Bluetooth and USB modes.  Communcation modes can be interchanged freely with no change in end-user library usage.  Detailed specifications about the Bluetooth connection handling, communication protocol, and requirements are outside the scope of this SDK.

The [Android TCMP Tappy SDK](https://github.com/TapTrack/TCMPTappy-Android) is required in order to connect and communicate with the terminal.  This library is not included in this project.  Please refer to the TCMP Tappy SDK project page for details regarding required dependencies for Bluetooth and USB communication, and information on how to perform common operations such as scanning for terminals, connecting, sending commands, and receiving responses.

We are currently in a closed beta program for our POS integration program.  To enroll in the closed beta please email [info@mywristcoin.com](mailto:info@mywristcoin.com) to obtain your development terminal.

## Features

The SDK currently supports the following operations:

* Event selection
* Reading wristband status and balance information
* Debiting the wristband a specified amount
* Getting the terminal's firmware version

### Sending commands to the WristCoin NFC terminal

After each command has been created, to send it to the terminal (assuming the the terminal is connected):

``` kotlin
yourWristCoinTerminal.sendMessage(setEventIdCommand)
```

Where `yourWristCoinTerminal` is of type `Tappy` (provided by the TCMP Tappy SDK).

### Receiving responses from the WristCoin NFC terminal

Once connected to a WristCoin NFC terminal, you must set a response listener to handle data being received from the terminal:

``` kotlin
yourWristCoinTerminal.registerResponseListener { message: TCMPMessage ->
    // Handle message here
}
```

Where `yourWristCoinTerminal` is of type `Tappy` (provided by the TCMP Tappy SDK).

Alternatively, you can pass an object implementing the `Tappy.ResponseListener` interface, or an equivalent lambda.

### Event Selection

At the moment, WristCoin terminals are pre-configured at the factory for the event(s) you specify when ordering the terminals.  Setting the event id selects the event your POS is using.  Passing apodn event id that the terminal was not configured for at the factory will result in an error response from the WristCoin terminal.  Future versions of the POS integration program will allow on-the-fly configuration of terminals for various events but at the moment the factory configuration will have to match the event id you're working with.

Whether your `eventId` is a `UUID` or an equivalent 16-byte `ByteArray` , construct a `SetEventIdCommand` like so:

``` kotlin
val eventId = ...
val command = SetEventIdCommand(eventId)
```

The `SetEventIdCommand` constructor can handle both types.

Then send the command to the terminal as shown above. 

It is recommended that you handle exceptions that may be thrown in the constructor if passing a `ByteArray` .  If a constructor can throw exceptions, it is annotated as such, and possible exceptions that can be thrown are explicitly listed.  The examples provided are intended for explanatory purposes only and hence are shortened for brevity.  In practice, you will want to handle these exceptions.

### Debiting Wristband Credit - Short Response

The short response version of this command is intended to allow a quick and easy integration if there is no need to have the terminal report back the full wristband status.  This command simply returns the available balance on the wristband after the requested amount is debited.  This avoids having to handle and parse TLV data if all that is needed  for the integration is to know the resulting balance from the transaction.

To request to debit one dollar (or pesos, euros, pounds, etc..):

``` kotlin
val command = DebitWristbandShortRespCommand(debitAmountCentavos = 100)
```

This will use the terminal's default timeout of about eight seconds.  To specify your own timeout, e.g. 12 seconds:

``` kotlin
val command = DebitWristbandShortRespCommand(debitAmountCentavos = 100, timeout = 12)
```
 
 Note: Specifying a timeout is interpreted as seconds from 0-255 seconds, where 0 indicates no timeout (i.e.  poll for a wristband until interrupted).
 
 Then send the command to the terminal as shown above.

### Debiting Wristband Credit - Full Response

For integrations that require the full resulting wristband status after a debit transaction, the full response version of the debit command is used. This returns the complete resulting wristband status as a TLV array which is parsed into a ```AppWristbandState``` object called ```resultingWristbandState```.

To request to debit one dollar (or pesos, euros, pounds, etc..):

``` kotlin
val command = DebitWristbandFullRespCommand(debitAmountCentavos = 100)
```

This will use the terminal's default timeout of about eight seconds.  To specify your own timeout, e.g. 12 seconds:

``` kotlin
val command = DebitWristbandFullRespCommand(debitAmountCentavos = 100, timeout = 12)
```

 
 Note: Specifying a timeout is interpreted as seconds from 0-255 seconds, where 0 indicates no timeout (i.e.  poll for a wristband until interrupted).
 
 Then send the command to the terminal as shown above.

### Getting Terminal Firmware Version

This command is handy to know what version of terminal firmware you have - especially useful as the beta program continues as new features are added:

``` kotlin
val command = GetWristCoinPOSCommandFamilyVersionCommand()
```

Then send the command to the terminal as shown above.

## AppWristbandState

The ```AppWristbandState``` class makes it easy to store the wristband data.

To access the lazy init variables (`balance`, `refundableOfflineBalance`, `rewardBalance`, `isClosedOut`, `isDeactivated`, `isConfiguredForOnlineOperation`):

When an instance of ```AppWristbandState``` is returned in the parsed ```DebitWristbandFullRespResponse()```:

```kotlin
// DebitWristbandFullRespResponse received from the WristCoin NFC terminal
val resultingWristbandState = response.getResultingWristbandState()
```
For ```GetWristbandStatusResponse```:
```kotlin
// GetWristbandStatusResponse received from the WristCoin NFC terminal
val wristbandState = response.getWristbandState()
```
To access the ```AppWristbandState``` members:
```kotlin
val uid = resultingWristbandState.uid // get the UID of the wristband
```
or for ```GetWristbandStatusResponse```:
```kotlin
val uid = wristbandState.uid // get the UID of the wristband
```

To read the values computed in the ```AppWristbandState``` class:
```kotlin
val resultingBalance = resultingWristbandState.balance // resulting balance from DebitWristbandFullRespResponse
val balance = wristbandState.balance // balance from GetWristbandStatusResponse
```

## Tag - Length - Value (TLV) Data Encoding Specification

In v1.1.0 TLV parsing is now supported and is handled by the `KotlinTLV` dependency 

## Requirements

* Minimum Android version for deployment is Android KitKat (4.4)
* Kotlin 1.4.32

This SDK depends on the `TCMP Tappy SDK` for NFC reader communication and `KotlinTLV` version `2.0.0` minimum.

## Installation

WristCoinPOS is available on Maven Central.  To install it, ensure you have `mavenCentral()` listed as a repository in your build.gradle file:

``` groovy
repositories {
    ...
    mavenCentral()
    ...
}
```

Then, simply add the following line to your dependencies:

``` groovy
dependencies {
    ...
    implementation 'com.mywristcoin:commandfamily-wristcoinpos:1.1.0'
    ...
}
```

## License

WristCoinPOS is available under the Apache 2.0 license.  See the LICENSE file for more info.
