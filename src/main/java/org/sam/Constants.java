package org.sam;
import org.powbot.api.Area;
import org.powbot.api.Tile;

import java.util.ArrayList;

public class Constants {

    public Constants() {
        super();
    }

    //STRINGS
    public static final String ORE_NAME = "Infernal shale rocks";

    //AREAS
    public static Area INFERNAL_SHALE_AREA = new Area(new Tile(1463,10105,0), new Tile(1475,10085,0));

    //Integer IDS
    public static final Integer JIM_ID = 14202;

    //ArrayLists
    public final ArrayList<Task> TASK_LIST = new ArrayList<Task>();
}