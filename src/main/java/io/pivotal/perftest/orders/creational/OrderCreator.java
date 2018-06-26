package io.pivotal.perftest.orders.creational;


import io.pivotal.perftest.orders.domain.Order;

public class OrderCreator implements Creator<Order>
{
	private String nodeId;
	
	public OrderCreator(String nodeId) {
		this.nodeId = nodeId;
	}

	public Order create()
	{
		String orderId = nodeId + "pvt.17535"+orderIdCounter;
		String clOrdId = "fix_0104_160148415_04082";
		String orderStatus = "OrderAck";
		String orderSource = "SOURCE_FIX";
		String rootId = "root.pvt.17535.18";
		String parentId = "pvt.17535.18";
		String msgType = "D";

		String orderString = "{\\\"11\\\":\\\"fix_0104_160148415_04082\\\", \\\"55\\\":\\\"MSFT\\\", \\\"34\\\":\\\"20\\\", \\\"56\\\":\\\"pvt1\\\", \\\"35\\\":\\\"D\\\", \\\"59\\\":\\\"0\\\", \\\"9876\\\":\\\"1\\\", \\\"37\\\":\\\"pvt.17535.18\\\", \\\"49\\\":\\\"FBSI1\\\", \\\"38\\\":\\\"800\\\", \\\"1\\\":\\\"X31639780\\\", \\\"100\\\":\\\"NYSE1\\\", \\\"8\\\":\\\"FIX.4.2\\\", \\\"9\\\":\\\"166\\\", \\\"60\\\":\\\"20180104-16:07:48\\\", \\\"40\\\":\\\"1\\\", \\\"52\\\":\\\"20180104-16:07:48.415\\\", \\\"21\\\":\\\"3\\\", \\\"54\\\":\\\"1\\\", \\\"10\\\":\\\"207\\\"  }";
		
		String tsReceived = "2018-01-04 16:07:49.668", tsSent = "2018-01-04 16:07:49.689";
		double avgPx= 0.0, cumQty = 0.0, orderQty = 800.0;
		
		double leaves = 800.0;
		
		Order orderDetail = new Order(orderId,clOrdId,orderStatus,
		orderSource,rootId, parentId,
		msgType,orderString,tsReceived,tsSent,
		avgPx,cumQty,orderQty,leaves);
			
		orderIdCounter++;
		
		return orderDetail;
		
	}
	


	private int orderIdCounter= 0;

}
