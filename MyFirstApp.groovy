/**
 *  My First SmartApp
 *
 *  An app for experimentation.
 *  Based on https://docs.smartthings.com/en/latest/getting-started/first-smartapp.html
 *
 *  Copyright 2019 Marcos Wright-Kuhns
 *
 *  Licensed under the MIT License.
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
definition(
    name: "My First SmartApp",
    namespace: "metavida",
    author: "Marcos Wright-Kuhns",
    description: "Hello SmartThings World",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("When motion is detected:") {
        input "theMotion", "capability.motionSensor", required: true, multiple: true, title: "Where?"
    }
    section("Turn on this light") {
        input "theSwitch", "capability.switch", required: true
    }
    section("Turn off when there's been no movement for") {
        input "seconds", "number", required: true, title: "Seconds"
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(theMotion, "motion.active", motionDetectedHandler)
    subscribe(theSwitch, "switch", switchHandler)
}

def motionDetectedHandler(evt) {
    log.debug "motionDetectedHandler called: $evt"
    log.debug "motionDetectedHandler Src:   ${evt.source}"
    log.debug "motionDetectedHandler Data:  ${evt.data}"
    log.debug "motionDetectedHandler Descr: ${evt.description}"
    log.debug "motionDetectedHandler DevId: ${evt.deviceId}"
    log.debug "motionDetectedHandler DisName: ${evt.displayName}"
    theSwitch.on()
    log.debug "motionDetectedHandler waiting $seconds seconds"
    runIn(seconds, turnSwitchOff)
}

def	turnSwitchOff() {
	log.debug "turnSwitchOff turning theSwitch off"
    theSwitch.off()
}

def switchHandler(evt) {
	log.debug "switchHandler called: $evt"
    log.debug "switchHandler Value:    ${evt.value}"
    log.debug "switchHandler Src:      ${evt.source}"
    log.debug "switchHandler Physical: ${evt.isPhysical()}"
    log.debug "switchHandler Digital:  ${evt.isDigital()}"
    log.debug "switchHandler DisName:  ${evt.displayName}"
}

// TODO: implement event handlers
