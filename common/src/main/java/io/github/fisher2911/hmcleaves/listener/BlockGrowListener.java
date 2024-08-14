package io.github.fisher2911.hmcleaves.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockSpreadEvent;

public class BlockGrowListener implements Listener {


    private final int MAX_AGE = 22; // 定义最大年龄为 22  从0开始
    //0-21
    @EventHandler
    public void onBlockGrow(BlockGrowEvent event) {
        // 检查生长的方块是否为洞穴藤蔓
        if (event.getBlock().getType() == Material.CAVE_VINES || event.getBlock().getType() == Material.CAVE_VINES_PLANT) {
            BlockData blockData = event.getNewState().getBlockData();
            System.out.println("***************onBlockGrow");
            // 检查方块是否可被设置年龄（Ageable）
            if (blockData instanceof Ageable) {
                Ageable ageable = (Ageable) blockData;
                int age = ageable.getAge();
                // 如果年龄超过 MAX_AGE，则将其设置为 MAX_AGE
                if (age< MAX_AGE&&age<=1) {
                    ageable.setAge(MAX_AGE);
                    System.out.println("debug"+ageable.getAge());
                    event.getNewState().setBlockData(ageable);
                }else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent event) {
        Block sourceBlock = event.getSource();
        Block newBlock = event.getBlock();
        System.out.println("************onBlockSpread");
        // 检查是否是洞穴藤蔓向下延伸的行为
        if (sourceBlock.getType() == Material.CAVE_VINES && newBlock.getType() == Material.CAVE_VINES_PLANT) {


            Ageable ageable = (Ageable) sourceBlock.getBlockData();
            if (ageable.getAge() <MAX_AGE) {
                event.setCancelled(true); // 取消向下延伸
            }
        }
    }


}
