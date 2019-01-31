/**
 *  AM Revenue Tile
 *
 *  Copyright 2019 Keni Barwick
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
 *  Please feel free to contact me on kenibarwick@gmail.com
 *  Revision History
 *  ==============================================
 *  2018-06-25 Version 0.0.1  initial upload
 *  2018-06-26 Version 0.0.2  spell checking
 *  2018-06-27 Version 0.0.3  changed version number and settings API key to Text not Number 
 *  2018-07-01 Version 0.0.4  Seetings for the GPU and Miner counts 
 *  2018-07-01 Version 0.0.5  Algo summary  
 *  2018-07-02 Version 0.0.6  Refresh delay implemented and minutes setting added  
 *  2019-01-29 Version 0.0.7  Added algo profit summary and power usage in kW
 *  2019-01-29 Version 0.0.8  Fixed missing algo
 *  2019-01-31 Version 0.0.9  Added options to caputre CPU, GPA, ASIC mines
*/

def clientVersion() {
    return "0.0.9"
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
                valueTile("profitPerDayMain", "device.profitPerDay", width: 2, height: 2) {
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
                    state "val", label:'Revenue Per Day \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: 100, color: "#00ff00"]
                    ]
                }
                valueTile("revenuePerMonth", "device.revenuePerMonth", width: 2, height: 2) {
                    state "val", label:'Revenue Per Month \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: 2000, color: "#00ff00"]
                    ]
                }
                valueTile("profitPerDay", "device.profitPerDay", width: 2, height: 2) {
                    state "val", label:'Profit Per Day \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: 100, color: "#00ff00"]
                    ]
                }
                valueTile("profitPerMonth", "device.profitPerMonth", width: 2, height: 2) {
                    state "val", label:'Profit Per Month \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: 2000, color: "#00ff00"]
                    ]
                }
                valueTile("powerUsage", "device.powerUsage", width: 2, height: 2) {
                    state "val", label:'Power \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: 100, color: "#00ff00"]
                    ]
                }
                valueTile("runningCount", "device.runningCount", width: 2, height: 2) {
                    state "val", label:'Running\nCount \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: AM_miner_count, color: "#00ff00"]
                    ]
                }
                valueTile("totalCount", "device.totalCount", width: 2, height: 2) {
                    state "val", label:'Total\nCount \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: AM_miner_count, color: "#00ff00"]
                    ]
                }
                valueTile("gpuCount", "device.gpuCount", width: 2, height: 2) {
                    state "val", label:'GPU\nCount \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: AM_gpu_count, color: "#00ff00"]
                    ]
                }
                valueTile("asicCount", "device.asicCount", width: 2, height: 2) {
                    state "val", label:'ASIC\nCount \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: AM_asic_count, color: "#00ff00"]
                    ]
                }
                valueTile("gpaCount", "device.gpaCount", width: 2, height: 2) {
                    state "val", label:'GPA\nCount \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: AM_gpa_count, color: "#00ff00"]
                    ]
                }
                valueTile("cpuCount", "device.cpuCount", width: 2, height: 2) {
                    state "val", label:'CPU\nCount \n ${currentValue}', defaultState: true, backgroundColors: [
                        [value: 0, color: "#ff0000"],
                        [value: AM_cpu_count, color: "#00ff00"]
                    ]
                }                
                standardTile("refresh", "device.switch", decoration: "flat", width: 2, height: 2) {
                    state "default", label: 'refresh', action: "refresh", icon: "st.secondary.refresh"
                }
            	standardTile("info", "device.info", decoration: "flat", width: 4, height: 2) {
                    state "default", label: 'last updated \n${currentValue}', action: "refresh"
                }

				// coinList                
                 standardTile("algorithmList", "device.algorithmList", decoration: "flat", width: 6, height: 2) {
                    state "default", label: 'Algorithm List \n${currentValue}', action: "refresh"
                }
                                                
            main("revenuePerDayMain")
            	details(["profitPerDay", "profitPerMonth", "powerUsage", "revenuePerDay", "revenuePerMonth", "refresh", "runningCount", "totalCount", "gpuCount", "asicCount", "gpaCount", "cpuCount", "exchangeRate", "info", "algorithmList"])
                }
    }
 	preferences {
	    input title: "Smart Awesome Miner API settings", description: "v${clientVersion()} (c) Keni Barwick", displayDuringSetup: true, type: "paragraph", element: "paragraph"
        input name: "AM_domain", type: "text", title: "Awesome Miner domain address", description: "Enter the Awesome Miner API domain address: i.e. domain.com", required: true, displayDuringSetup: true
        input name: "AM_port", type: "number", title: "Awesome Miner port number", description: "Enter the Awesome Miner API port number", required: true, displayDuringSetup: true
        input name: "AM_api_key", type: "text", title: "Awesome Miner api key", description: "Enter API key", required: true, displayDuringSetup: true
        input name: "AM_miner_count", type: "number", title: "Awesome Miner count", description: "Enter amount of miners", required: true
        input name: "AM_gpu_count", type: "number", title: "Count of GPUs", description: "Enter ammount of GPUs", required: true     
        input name: "AM_asic_count", type: "number", title: "Count of ASICs", description: "Enter ammount of ASICs", required: true     
        input name: "AM_gpa_count", type: "number", title: "Count of GPAs", description: "Enter ammount of GPAs", required: true     
        input name: "AM_cpu_count", type: "number", title: "Count of CPUs", description: "Enter ammount of CPUs", required: true     
        input name: "AM_refresh_delay", type: "number", title: "Details refresh delay in minutes", description: "Enter ammount of minutes", required: true        
    }
}
def refresh() {
	initialize()
}

def installed() {
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
    
    runIn(AM_refresh_delay*60, initialize)
}

def responseHandlerMethod(response, data) {
	
    // log.debug "parsing response"

	def respText = response.data as String
    def mineResult = new groovy.json.JsonSlurper().parseText(respText)
    def profitPerDayMain = mineResult.get("profitPerDay")
    log.debug "profitPerDayMain ${profitPerDayMain}"
    def revenuePerDay = mineResult.get("revenuePerDay")
    log.debug "revenuePerDay ${revenuePerDay}"
    def revenuePerMonth = mineResult.get("revenuePerMonth")
    log.debug "revenuePerMonth ${revenuePerMonth}"
    def profitPerDay = mineResult.get("profitPerDay")
    log.debug "profitPerDay ${profitPerDay}"
    def profitPerMonth = mineResult.get("profitPerMonth")
    log.debug "profitPerMonth ${profitPerMonth}"
    def powerUsage = mineResult.get("powerUsage")
    log.debug "powerUsage ${exchangeRate}"
    def exchangeRate = mineResult.get("exchangeRate")
    log.debug "exchangeRate ${exchangeRate}"
    def runningCount = mineResult.get("runningCount")
    log.debug "runningCount ${runningCount}"
    def totalCount = mineResult.get("totalCount")
    log.debug "totalCount ${totalCount}"
    def gpuCount = mineResult.get("gpuCount")
    log.debug "gpuCount ${gpuCount}"
    def asicCount = mineResult.get("asicCount")
    log.debug "asicCount ${gpuCount}"
    def apuCount = mineResult.get("apuCount")
    log.debug "apuCount ${gpuCount}"
     def cpuCount = mineResult.get("cpuCount")
    log.debug "cpuCount ${gpuCount}"       
    def metaData = mineResult.get("metaData")
    log.debug "updated ${metaData.updated}"
    def algorithmList = mineResult.get("algorithmList")
    
    sendEvent(name: "exchangeRate", value: exchangeRate)
    sendEvent(name: "revenuePerMonth", value: revenuePerMonth)
    sendEvent(name: "revenuePerDay", value: revenuePerDay)
     
    sendEvent(name: "profitPerDayMain", value: profitPerDay)

    sendEvent(name: "profitPerDay", value: profitPerDay)
    sendEvent(name: "profitPerMonth", value: profitPerMonth)
    sendEvent(name: "powerUsage", value: powerUsage)
    
    sendEvent(name: "runningCount", value: runningCount)
    sendEvent(name: "totalCount", value: totalCount)
    sendEvent(name: "gpuCount", value: gpuCount)
    sendEvent(name: "asicCount", value: asicCount)
    sendEvent(name: "apuCount", value: apuCount)
    sendEvent(name: "cpuCount", value: cpuCount)
        
    sendEvent(name: "algorithmList", value: algorithmListConcat(algorithmList))
    
    sendEvent(name: "info", value: metaData.updated)
}

    def algorithmListConcat(algorithmList)
    {
        def result = ""
        for (int i = 0; i < algorithmList.size; i++) {
            result += algorithmList[i].name + " @ " + algorithmList[i].hashRate5s + " = " + algorithmList[i].revenue + " - " + algorithmList[i].temperature + "c" +  "\n"
        }
		return result
    }
