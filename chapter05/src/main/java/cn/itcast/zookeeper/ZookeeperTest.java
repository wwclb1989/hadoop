package cn.itcast.zookeeper;

import org.apache.zookeeper.*;

public class ZookeeperTest {
    public static void main(String[] args) throws Exception {
        // 步骤一：创建zookeeper客户端
        // 参数1：zk地址; 参数2：会话超时时间（与系统默认一致）; 参数3：监视器
        ZooKeeper zk = new ZooKeeper("hadoop01:2181,hadoop02:2181,hadoop03:2181", 30000, new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                System.out.println("事件类型为：" + event.getType());
                System.out.println("事件发生的路径：" + event.getPath());
                System.out.println("通知状态为：" + event.getState());
            }
        });

        // 步骤二：创建目录节点
        // 参数1：要创建的节点的路径;参数2：节点数据;参数3：节点权限;参数4：节点类型
        zk.create("/testRootPath", "testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 步骤三：创建子目录节点
        zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 步骤四：获取目录节点数据
        // 参数1：存储子目录节点数据的路径
        // 参数2：是否需要监控此节点
        // 参数3：stat节点的统计信息（一般设置为null）
        System.out.println("testRootData节点数据为：" + new String(zk.getData("/testRootPath", false, null)));

        // 步骤五：获取子目录节点数据
        System.out.println(zk.getChildren("/testRootPath", true));

        // 步骤六：修改子目录节点数据，使得监听触发
        // 参数1：存储子目录节点数据
        // 参数2：要修改的数据
        // 参数3：预期要匹配的版本（设置为-1，则可匹配任何节点的版本）
        zk.setData("/testRootPath/testChildPathOne", "modifyChildDataOne".getBytes(), -1);

        // 步骤七：判断目录节点是否存在
        System.out.println("目录节点状态：[" + zk.exists("/testRootPath", true) + "]");

        // 步骤八：删除子目录节点
        zk.delete("/testRootPath/testChildPathOne", -1);

        // 步骤九：删除目录节点
        zk.delete("/testRootPath", -1);
        zk.close();
    }
}
