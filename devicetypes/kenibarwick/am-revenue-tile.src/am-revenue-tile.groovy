/**
 *  AM Revenue Tile
 *
 *  Copyright 2018 Keni Barwick
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

def clientVersion() {
    return "0.0.4"
}
 
definition(
    name: "AM Revenue Tile",
    namespace: "kenibarwick",
    author: "Keni Barwick",
    description: "AM Revenue Tile",
    category: "Awesome Miner App",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")
    {
        capability "Refresh"
        capability "Sensor"
        capability "Actuator"
    }

metadata {
    simulator {
        // TODO: define status and reply messages here

    }
    tiles {
        // TODO: define your main and details tiles here
        tiles(scale: 2) {
                valueTile("revenuePerDayMain", "device.revenuePerDay", width: 2, height: 2) {
                    state "val", label:'${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: 100, color: "#00ff00"]
                    ]
                }
                valueTile("exchangeRate", "device.exchangeRate", width: 2, height: 2) {
                    state "val", label:'BTC Rate \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: 10000, color: "#00ff00"]
                    ]
                }
                valueTile("revenuePerDay", "device.revenuePerDay", width: 2, height: 2) {
                    state "val", label:'Per Day \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: 100, color: "#00ff00"]
                    ]
                }
                valueTile("revenuePerMonth", "device.revenuePerMonth", width: 2, height: 2) {
                    state "val", label:'Per Month \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: 2000, color: "#00ff00"]
                    ]
                }
                valueTile("runningCount", "device.runningCount", width: 2, height: 2) {
                    state "val", label:'Running Count \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: AM_miner_count, color: "#00ff00"]
                    ]
                }
                valueTile("totalCount", "device.totalCount", width: 2, height: 2) {
                    state "val", label:'Total Count \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: AM_miner_count, color: "#00ff00"]
                    ]
                }
                valueTile("gpuCount", "device.gpuCount", width: 2, height: 2) {
                    state "val", label:'GPU Count \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: AM_gpu_count, color: "#00ff00"]
                    ]
                }
                
                standardTile("refresh", "device.switch", decoration: "flat", width: 2, height: 2) {
                    state "default", label: 'refresh', action: "refresh", icon: "st.secondary.refresh"
                }
                
                 standardTile("info", "device.info", decoration: "flat", width: 4, height: 2) {
                    state "default", label: 'last updated \n${currentValue}', action: "refresh"
                }
                
	// coinList                
                
            main("revenuePerDayMain")
            	details(["exchangeRate", "revenuePerDay", "revenuePerMonth", "runningCount", "totalCount", "gpuCount", "refresh", "info"])
                }
    }
 	preferences {
	    input title: "Smart Awesome Miner API settings", description: "v${clientVersion()} (c) Keni Barwick", displayDuringSetup: true, type: "paragraph", element: "paragraph"
        input name: "AM_domain", type: "text", title: "Awesome Miner domain address", description: "Enter the Awesome Miner API domain address: i.e. domain.com", required: true, displayDuringSetup: true
        input name: "AM_port", type: "number", title: "Awesome Miner port number", description: "Enter the Awesome Miner API port number", required: true
        input name: "AM_api_key", type: "text", title: "Awesome Miner api key", description: "Enter API key", required: true
        input name: "AM_miner_count", type: "number", title: "Awesome Miner count", description: "Enter amount of miners", required: true
        input name: "AM_gpu_count:", type: "number", title: "Count of GPUs", description: "Enter ammount of GPUs", required: true        
    }
}
def refresh() {
		initialize()
}

def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}

def unsubscribe() {
}

include 'asynchttp_v1'
import groovy.json.JsonSlurper

def initialize() {
    def params = [
        uri: 'http://' + AM_domain + ':' + AM_port, 
        contentType: 'application/json',
        Accept: 'application/json',
    	path: """/api/summary?key=""" + AM_api_key 
       ]
       
	// log.debug "sending request"    
    asynchttp_v1.get('responseHandlerMethod', params)
    
 
}

def responseHandlerMethod(response, data) {
	
    // log.debug "parsing response"

	def respText = response.data as String
    def mineResult = new groovy.json.JsonSlurper().parseText(respText)
    def revenuePerDay = mineResult.get("revenuePerDay")
    log.debug "revenuePerDay ${revenuePerDay}"
    def revenuePerMonth = mineResult.get("revenuePerMonth")
    log.debug "revenuePerMonth ${revenuePerMonth}"
    def exchangeRate = mineResult.get("exchangeRate")
    log.debug "exchangeRate ${exchangeRate}"
    def runningCount = mineResult.get("runningCount")
    log.debug "runningCount ${runningCount}"
    def totalCount = mineResult.get("totalCount")
    log.debug "totalCount ${totalCount}"
    def gpuCount = mineResult.get("gpuCount")
    log.debug "gpuCount ${gpuCount}"
    def metaData = mineResult.get("metaData")
    log.debug "updated ${metaData.updated}"

    sendEvent(name: "exchangeRate", value: exchangeRate)
    sendEvent(name: "revenuePerMonth", value: revenuePerMonth)
    sendEvent(name: "revenuePerDay", value: revenuePerDay)
    
    sendEvent(name: "runningCount", value: runningCount)
    sendEvent(name: "totalCount", value: totalCount)
    sendEvent(name: "gpuCount", value: gpuCount)
    
    sendEvent(name: "info", value: metaData.updated)
}