#!/bin/bash


# Issue commands to gfsh to start locator and launch a server
echo "Starting locator and server..."
gfsh <<!
start locator --name=locator1 --port=10334 --properties-file=config/locator.properties --initial-heap=256m --max-heap=256m

start server --name=server1 --server-port=0 --properties-file=config/gemfire.properties --cache-xml-file=./config/cache.xml --initial-heap=1g --max-heap=1g

create region --name=Account --type=PARTITION_REDUNDANT
create region --name=ClOrdIdToOrdId --type=PARTITION_REDUNDANT
create region --name=ClientToSession --type=PARTITION_REDUNDANT
create region --name=DKExecution --type=PARTITION_REDUNDANT
create region --name=DestToSession --type=PARTITION_REDUNDANT
create region --name=Destination --type=PARTITION_REDUNDANT
create region --name=ErrorCode --type=PARTITION_REDUNDANT
create region --name=Execution --type=PARTITION_REDUNDANT
create region --name=FixEngineAdmin --type=PARTITION_REDUNDANT
create region --name=HashUniqueDestId --type=PARTITION_REDUNDANT
create region --name=InMsg --type=PARTITION_REDUNDANT
create region --name=MassAction --type=PARTITION_REDUNDANT
create region --name=MktExecIdToGfrExecId --type=PARTITION_REDUNDANT
create region --name=MsgId --type=PARTITION_REDUNDANT
create region --name=Order --type=PARTITION_REDUNDANT
create region --name=OutMsg --type=PARTITION_REDUNDANT
create region --name=QFEventLog --type=PARTITION_REDUNDANT
create region --name=QFMessage --type=PARTITION_REDUNDANT
create region --name=QFMessageLog --type=PARTITION_REDUNDANT
create region --name=QFSession --type=PARTITION_REDUNDANT
create region --name=RoutingRule --type=PARTITION_REDUNDANT
create region --name=Session --type=PARTITION_REDUNDANT
create region --name=TagLibraryDestination --type=PARTITION_REDUNDANT
create region --name=TagLibraryRule --type=PARTITION_REDUNDANT
create region --name=TagLibrarySession --type=PARTITION_REDUNDANT
create region --name=User --type=PARTITION_REDUNDANT
create region --name=UserEntitlement --type=PARTITION_REDUNDANT
create region --name=UserFunction --type=PARTITION_REDUNDANT
create region --name=UserRole --type=PARTITION_REDUNDANT

list members;
list regions;

exit;
!
