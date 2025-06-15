package org.sam;
import org.powbot.api.Color;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;
import org.sam.Tasks.*;
import org.sam.Tasks.Configs.EquipmentConfig;
import org.sam.Tasks.Configs.InventoryConfig;

import java.util.Map;

import static org.sam.Functions.equipmentConfig;


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
//
//    Map<Integer, Integer> equipmentMap = getOption("Equipment");
//    Set<Integer> itemIds = equipmentMap.keySet(); // Extract only the item IDs
//    EquipmentConfig equipmentConfig = new EquipmentConfig(itemIds, );

    InventoryConfig inventoryConfig;

    Variables vars = new Variables();
    Constants constants = new Constants();

    @Override
    public void onStart() {
        inventoryConfig = new InventoryConfig(
                getOption("Inventory")
        );
        //Map<Integer, Integer> equipment = getOption("Equipment"); // This is your Map<Integer, Integer>
        // Get the arrow/bolt in the quiver slot
        Item quiverItem = Equipment.itemAt(Equipment.Slot.QUIVER);
        int quiverStackSize = quiverItem.valid() ? quiverItem.stackSize() : 0;

        equipmentConfig = new EquipmentConfig(getOption("Equipment"), quiverStackSize);

        constants.TASK_LIST.add(new Running(this));
        constants.TASK_LIST.add(new WalkToLumbridgeBank(this));
        constants.TASK_LIST.add(new OpenBank(this, inventoryConfig, equipmentConfig));
        constants.TASK_LIST.add(new Resupply(this, inventoryConfig, equipmentConfig));
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
        for (Task task : constants.TASK_LIST) {
            if (task.activate()) {
                vars.currentTask = task.name;
                task.execute();

                if (ScriptManager.INSTANCE.isStopping()) {
                    break;
                }
            }
        }
    }
}