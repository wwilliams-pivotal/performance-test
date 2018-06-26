package io.pivotal.perftest.orders.creational.pdx;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.PdxWriter;

import io.pivotal.perftest.orders.domain.Order;

public class OrderDetailPdxSerializer implements PdxSerializer
{

	@Override
	public Object fromData(Class<?> clz, PdxReader pdxReader)
	{
		
		Order orderDetail = new Order();
		orderDetail.setAvgPx(pdxReader.readDouble("avgPx"));
		orderDetail.setClOrdId(pdxReader.readString("clOrdId"));
		orderDetail.setCumQty(pdxReader.readDouble("cumQty"));
		orderDetail.setLeaves(pdxReader.readDouble("leaves"));
		orderDetail.setMsgType(pdxReader.readString("msgType"));
		orderDetail.setOrderId(pdxReader.readString("orderId"));
		orderDetail.setOrderQty(pdxReader.readDouble("orderQty"));
		orderDetail.setOrderSource(pdxReader.readString("orderSource"));
		orderDetail.setOrderStatus(pdxReader.readString("orderStatus"));
		orderDetail.setOrderString(pdxReader.readString("orderString"));
		orderDetail.setParentId(pdxReader.readString("parentId"));
		orderDetail.setRootId(pdxReader.readString("rootId"));
		orderDetail.setTsReceived(pdxReader.readString("tsReceived"));
		orderDetail.setTsSent(pdxReader.readString("tsSent"));
		
		return orderDetail;
	}

	@Override
	public boolean toData(Object obj, PdxWriter pdxWriter)
	{
		Order orderDetail = (Order)obj;
		
		pdxWriter.writeDouble("avgPx",orderDetail.getAvgPx());
		pdxWriter.writeString("clOrdId",orderDetail.getClOrdId());
		pdxWriter.writeDouble("cumQty",orderDetail.getCumQty());
		pdxWriter.writeDouble("leaves",orderDetail.getLeaves());
		pdxWriter.writeString("msgType",orderDetail.getMsgType());
		pdxWriter.writeString("orderId",orderDetail.getOrderId());
		pdxWriter.writeDouble("orderQty",orderDetail.getOrderQty());
		pdxWriter.writeString("orderSource",orderDetail.getOrderSource());
		pdxWriter.writeString("orderStatus",orderDetail.getOrderStatus());
		pdxWriter.writeString("orderString",orderDetail.getOrderString());
		pdxWriter.writeString("parentId",orderDetail.getParentId());
		pdxWriter.writeString("rootId",orderDetail.getRootId());
		pdxWriter.writeString("tsReceived",orderDetail.getTsReceived());
		pdxWriter.writeString("tsSent",orderDetail.getTsSent());
		
		
		return true;
	}

}
