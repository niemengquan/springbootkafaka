package com.nmq.zookeeper.test;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;
import java.util.List;

/**
 * Created by niemengquan on 2017/11/24.
 */
public class HelloZookeeper {
    private static final String ZK_HOST="192.168.132.129";
    private static final Integer ZK_PORT=2181;
    private static final Integer SESSION_TIMEOUT=20000;
    private static  ZooKeeper zk=null;

    static {
        try{
            zk=new ZooKeeper(ZK_HOST + ":" + ZK_PORT, SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    //监控所有被触发的事件
                    System.out.println("已经触发了"+event.getType()+"事件");
                }
            });
        }catch (Exception err){
            err.printStackTrace();
        }

    }
    public static void main(String[] args) throws Exception{
        createNodeEphemeral();
    }
    public static void helloZookeeper() throws Exception{
        String rootPath="/mytest";
        //创建一个目录节点,watch参数表名是否在本此操作之后继续watch当前节点
        Stat exists = zk.exists(rootPath, true);
        if(null==exists){
            zk.create(rootPath,"myTest data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        //创建子目录节点
        if(null==zk.exists(rootPath+"/mytestChildren", true))
            zk.create(rootPath+"/mytestChildren","mytestChildren data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData(rootPath,true,null)));
        System.out.println(new String(zk.getData(rootPath+"/mytestChildren",true,null)));

        //删除节点
        zk.delete(rootPath+"/mytestChildren",-1);
        zk.delete(rootPath,-1);

        zk.close();
    }

    public static void createNodeEphemeral() throws Exception{
        if(zk.exists("/myEphemeralRoot",true)==null){
            zk.create("/myEphemeralRoot","ephemeral data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }
        zk.create("/myEphemeralRoot/mytest1","ephemeral test children".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        zk.create("/myEphemeralRoot/mytest2","ephemeral test children".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        zk.create("/myEphemeralRoot/mytest3","ephemeral test children".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        zk.create("/myEphemeralRoot/mytest4","ephemeral test children".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        String currentNode = zk.create("/myEphemeralRoot/mytest5", "ephemeral test children".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        //获取myEphemeralRoot 的children
        List<String> myEphemeralRootChild = zk.getChildren("/myEphemeralRoot", true);
        //比对children中的最小的id值是否是自己创建的值，来决定是否获取锁
        String[] nodes = myEphemeralRootChild.toArray(new String[myEphemeralRootChild.size()]);
        //排序
        Arrays.sort(nodes);
        if(currentNode.equals("/myEphemeralRoot/"+nodes[0])){
            //可以获取锁
            System.out.println("获取到锁，可以操作");
        }else{
            //其他人正在当前节点操作，拥有锁对象，不能获取
            System.out.println("锁被占用请等待");
        }

        zk.close();
    }
}
