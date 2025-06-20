package org.sam;
import org.powbot.api.Color;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.service.ScriptUploader;
import org.sam.Branches.Tree;
import org.sam.Tasks.*;
import org.sam.Configs.EquipmentConfig;
import org.sam.Configs.InventoryConfig;

import java.util.Map;

import static org.sam.Utils.Functions.equipmentConfig;


@ScriptConfiguration.List({
        @ScriptConfiguration(
                name = "Inventory",
                description = "Parse Inventory",
                optionType = OptionType.INVENTORY
        ),
        @ScriptConfiguration(
                name = "Equipment",
                description = "Parse Equipment",
                optionType = OptionType.EQUIPMENT
        )
})

@ScriptManifest(
        name = "Sam Green Dragons",
        description = "Green Dragon Slayer",
        author = "Sam",
        version = "1.0",
        category = ScriptCategory.Combat
)
public class samGreenDragons extends AbstractScript {
    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("Sam Green Dragons", "", "R52T90A6VCM", true, false);
    }

    private Branch tree;

//
//    Map<Integer, Integer> equipmentMap = getOption("Equipment");
//    Set<Integer> itemIds = equipmentMap.keySet(); // Extract only the item IDs
//    EquipmentConfig equipmentConfig = new EquipmentConfig(itemIds, );

    InventoryConfig inventoryConfig;

    Variables vars = new Variables();
    Constants constants = new Constants();

    @Override
    public void onStart() {
        tree = new Tree();
        inventoryConfig = new InventoryConfig(
                getOption("Inventory")
        );
        //Map<Integer, Integer> equipment = getOption("Equipment"); // This is your Map<Integer, Integer>
        // Get the arrow/bolt in the quiver slot

        Map<Integer, Integer> map = getOption("Equipment");
        int[] ids = map.keySet().stream().mapToInt(Integer::intValue).toArray();

        Item quiverItem = Equipment.itemAt(Equipment.Slot.QUIVER);
        int quiverStackSize = quiverItem.valid() ? quiverItem.stackSize() : 0;

        equipmentConfig = new EquipmentConfig(ids, quiverStackSize);

        constants.TASK_LIST.add(new Running(this));
        constants.TASK_LIST.add(new WalkToLumbridgeBank(this));
        constants.TASK_LIST.add(new OpenBank(this, inventoryConfig, equipmentConfig));
        constants.TASK_LIST.add(new ResupplyGear(this, equipmentConfig));
        constants.TASK_LIST.add(new Equip(this, equipmentConfig));
        constants.TASK_LIST.add(new ResupplyInventory(this, inventoryConfig));
        constants.TASK_LIST.add(new TeleportToFerox(this, inventoryConfig, equipmentConfig));

        addPaint(
                PaintBuilder.newBuilder()
                        .minHeight(150)
                        .minWidth(450)
                        .backgroundColor(Color.argb(175, 0, 0, 0))
                        .withTextSize(14F)
                        .addString(() -> "Task: " + vars.currentTask)
                        .trackSkill(Skill.Combat)
                        .build()
        );
        vars.currentTask = "Idle";
    }

    @Override
    public void poll() {
        if (tree != null && tree.validate()) {

        }
//        for (Task task : constants.TASK_LIST) {
//            if (task.activate()) {
//                vars.currentTask = task.name;
//                task.execute();
//
//                if (ScriptManager.INSTANCE.isStopping()) {
//                    break;
//                }
//            }
//        }
    }
}