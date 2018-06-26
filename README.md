# Performance Test

This is a new version of the performance application to more closely simulates  actual order routing application load. 

## Testing Case


It simulates the process of orders at a rate of multiples of 200/s. Example, 200, 400, then go to 800 orders.


* The normal expected order rate is 200 orders/sec

For every order there will be:

* an inmsg insert
* an orderDetail insert
* an outmsg insert

Shortly (3 milliseconds) after that will be the execution. For every execution, there will be:
* an inmsg insert
* an orderDetail read
* an orderDetail update
* an execution insert
* an out msg insert
* for every order, 1.2 executions


There will be an insert into the QFMessage for each inmsg and outmsg

The application will capture  the following READ/WRITE region timings
* mean                 
* min                                     
* 90.00%                 
* 99.00%             
* 99.90%             
* 99.99%             
* max               
* count    


**Setup Environment**

The following environment variables are required

    export LOCATOR_HOST=localhost
    export LOCATOR_PORT=10000
    
**Performance Improvements**

The following environment variables should be set to improve performance

	export PDX_SERIALIZER_CLASS_NM=io.pivotal.perftest.orders.creational.pdx.PdxSerializers
	export POOL_PR_SINGLE_HOP_ENABLED=true
	export PDX_READ_SERIALIZED=true

The following are optional set with the current default values. Do not change these unless advised.

	export WARMUP_CNT=400
	export USE_CACHING_PROXY=false
	export MAX_WINDOW_SIZE=1600
	export PDX_CLASS_PATTERN=.*
	



This test includes a PCF app that generates GemFire calls from a single call to PCF. It will generate sample payloads for the Order, Execution, InMsg,OutMsg and QF Message. The timing will only include the time between PCF and GemFire  READ/WRITE operations. It will produce a report that contains the times for GET/PUT in standard output.
 

 
 ## Cucumber Test Definition



	Given PCF app instances 1

	"""
	    InMsg Structure
	    ===============
	      {
				"destSession": "",
				"itemFormat": "fix",
				"itemType": "infix",
				"msgId": "pvt.17535.${InMsgCounter}",
				"msgType": "8",
				"payload": {
					"map": {
						"fixString": "8u003dFIX.4.2u00019u003d174u000135u003d8u000134u003d52u000149u003dFIXIMULATORu000152u003d20180104-16:07:49.361u000156u003dpvt1u00016u003d0u000111u003dpvt.17535.13u000114u003d0u000117u003dE1515082069374u000120u003d0u000131u003d0u000132u003d0u000137u003dO1515082069374u000138u003d300u000139u003d0u000154u003d2u000155u003dGOOGu0001150u003d0u0001151u003d300u000110u003d148u0001"
					}
				},
				"srcSession": "FIXIMULATOR",
				"timeStamp": "2018-01-04 16:07:49.373"
			}
	
	    OrderDetails Structure
	    ===============
		{
			"clOrdId": "fix_0104_160148415_04082",
			"orderId": "pvt.17535.${orderIdCounter}",
			"orderString": "{\\\"11\\\":\\\"fix_0104_160148415_04082\\\", \\\"55\\\":\\\"MSFT\\\", \\\"34\\\":\\\"20\\\", \\\"56\\\":\\\"pvt1\\\", \\\"35\\\":\\\"D\\\", \\\"59\\\":\\\"0\\\", \\\"9876\\\":\\\"1\\\", \\\"37\\\":\\\"pvt.17535.18\\\", \\\"49\\\":\\\"FBSI1\\\", \\\"38\\\":\\\"800\\\", \\\"1\\\":\\\"X31639780\\\", \\\"100\\\":\\\"NYSE1\\\", \\\"8\\\":\\\"FIX.4.2\\\", \\\"9\\\":\\\"166\\\", \\\"60\\\":\\\"20180104-16:07:48\\\", \\\"40\\\":\\\"1\\\", \\\"52\\\":\\\"20180104-16:07:48.415\\\", \\\"21\\\":\\\"3\\\", \\\"54\\\":\\\"1\\\", \\\"10\\\":\\\"207\\\"  }",
			"tsReceived": "2018-01-04 16:07:49.668",
			"tsSent": "2018-01-04 16:07:49.689",
			"avgPx": 0.0,
			"cumQty": 0.0,
			"orderQty": 800.0,
			"leaves": 800.0,
			"orderStatus": "OrderAck",
			"orderSource": "SOURCE_FIX",
			"rootId": "root.pvt.17535.18",
			"parentId": "pvt.17535.18",
			"msgType": "D"
		}
	
	
	    Execution Structure
	    ===============
	      {
	"fixString": "${executionId}\\u003dFIX.4.2\\u00019\\u003d225\\u000135\\u003d8\\u000134\\u003d15\\u000149\\u003dpvt1\\u000152\\u003d20180104-16:07:49.777\\u000156\\u003dFBSI1\\u00011\\u003dX31639780\\u00016\\u003d0.0\\u000111\\u003dfix_0104_160148237_04077\\u000114\\u003d0.0\\u000117\\u003dpvt.17535.10013\\u000120\\u003d0\\u000131\\u003d0.0\\u000132\\u003d0.0\\u000137\\u003dpvt.17535.13\\u000138\\u003d300.0\\u000139\\u003d0\\u000154\\u003d2\\u000155\\u003dGOOG\\u000159\\u003d0\\u0001150\\u003d0\\u0001151\\u003d300.0\\u0001376\\u003dpvt.17535.13\\u000110\\u003d176\\u0001"
	
	}
	    OutMsg Structure
	    ===============
	      {
			"destSession": "FBSI1",
			"itemFormat": "fix",
			"itemType": "outfix",
			"msgId": "pvt.17535.${msgIdCounter}",
			"msgType": "8",
			"payload": {
				"map": {
					"fixString": "8\\u003dFIX.4.2\\u00019\\u003d225\\u000135\\u003d8\\u000134\\u003d15\\u000149\\u003dpvt1\\u000152\\u003d20180104-16:07:49.777\\u000156\\u003dFBSI1\\u00011\\u003dX31639780\\u00016\\u003d0.0\\u000111\\u003dfix_0104_160148237_04077\\u000114\\u003d0.0\\u000117\\u003dpvt.17535.10013\\u000120\\u003d0\\u000131\\u003d0.0\\u000132\\u003d0.0\\u000137\\u003dpvt.17535.13\\u000138\\u003d300.0\\u000139\\u003d0\\u000154\\u003d2\\u000155\\u003dGOOG\\u000159\\u003d0\\u0001150\\u003d0\\u0001151\\u003d300.0\\u0001376\\u003dpvt.17535.13\\u000110\\u003d176\\u0001"
				}
			},
			"srcSession": "",
			"timeStamp": "2018-01-04 16:07:49.778"
		}
	
	    QFMessage Structure
	    ===============
	        {
				"destSession": "",
				"itemFormat": "fix",
				"itemType": "infix",
				"msgId": "pvt.17535.${qfMsgIdCounter}",
				"msgType": "8",
				"payload": {
					"map": {
						"fixString": "8u003dFIX.4.2u00019u003d174u000135u003d8u000134u003d52u000149u003dFIXIMULATORu000152u003d20180104-16:07:49.361u000156u003dpvt1u00016u003d0u000111u003dpvt.17535.13u000114u003d0u000117u003dE1515082069374u000120u003d0u000131u003d0u000132u003d0u000137u003dO1515082069374u000138u003d300u000139u003d0u000154u003d2u000155u003dGOOGu0001150u003d0u0001151u003d300u000110u003d148u0001"
					}
				},
				"srcSession": "FIXIMULATOR",
				"timeStamp": "2018-01-04 16:07:49.373"
			}
			
	    """

