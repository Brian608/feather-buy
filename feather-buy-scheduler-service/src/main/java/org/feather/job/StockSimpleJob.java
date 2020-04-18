package org.feather.job;

;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.feather.stock.dao.StockFlowMapper;
import org.feather.stock.dao.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;



public class StockSimpleJob implements SimpleJob{

    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private StockFlowMapper stockFlowMapper;
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(String.format("------Thread ID: %s, 任务总片数: %s, 当前分片项: %s",
                Thread.currentThread().getId(), shardingContext.getShardingTotalCount(), shardingContext.getShardingItem()));



        /**
         * 实际开发中，有了任务总片数和当前分片项，就可以对任务进行分片执行了
         * 比如 SELECT * FROM user WHERE status = 0 AND MOD(id, shardingTotalCount) = shardingItem
         */
    }
}
