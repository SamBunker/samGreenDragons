package org.sam;
import com.google.common.eventbus.Subscribe;
import org.powbot.api.Color;
import org.powbot.api.event.MessageEvent;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;
import org.sam.Tasks.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ScriptConfiguration.List({
        @ScriptConfiguration(
                name = "Mining Method",
                description = "How would you like to mine shale?",
                optionType = OptionType.STRING,
                allowedValues = {"3T Mining", "Mining", "AFK Mining"}
        ),
        @ScriptConfiguration(
                name = "SelectedRocks",
                description = "Select the rocks you'd like to interact with",
                optionType = OptionType.GAMEOBJECT_ACTIONS
        )
})

@ScriptManifest(
        name = "Sam Green Dragons",
        description = "3T, Tick Manipulation, Regular Mining, AFK Mining",
        author = "Sam",
        version = "1.0",
        category = ScriptCategory.Mining
)
public class samGreenDragons extends AbstractScript {
    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("Sam Infernal Shale", "", "R52T90A6VCM", true, false);
    }

    Variables vars = new Variables();
    Constants constants = new Constants();

    @Subscribe
    public void onMessageEvent(MessageEvent messageEvent) {
        if (!messageEvent.getSender().isEmpty()) {
            return;
        }
        String msg = messageEvent.getMessage().toLowerCase();
        System.out.println("msg: " + msg);

        Pattern pattern = Pattern.compile("Oh dear, you are dead!", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(msg.toLowerCase());
    }

    @Override
    public void onStart() {

        constants.TASK_LIST.add(new Running(this));

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

                if (!Functions.hasItem("pickaxe")) {
                    ScriptManager.INSTANCE.stop();
                    break;
                }
                if (ScriptManager.INSTANCE.isStopping()) {
                    break;
                }
            }
        }
    }
}