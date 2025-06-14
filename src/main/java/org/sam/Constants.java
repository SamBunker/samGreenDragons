package org.sam;
import org.powbot.api.Area;
import org.powbot.api.Tile;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public Constants() {
        super();
    }

    //STRINGS
    public static final String GREEN_DRAGON_NAME = "Green dragon";

    //TILES
    //public static Tile LUMBRIDGE


    //AREAS
    public static Area FEROX_ENCLAVE_TELEPORT_AREA = new Area(new Tile(3149, 3636, 0), new Tile(3152,3633,0));
    public static Area FEROX_ENCLAVE_BANK_AREA = new Area(new Tile(3129, 3631, 0), new Tile(3131, 3629, 0));
    public static Area DRAGONS_NORTH_OF_ENCLAVE_AREA = new Area(new Tile(3134, 3697, 0), new Tile(3160, 3713, 0));
    public static Area FEROX_ENCLAVE = new Area(new Tile(3155, 3646, 0), new Tile(3123, 3627, 0));
    public static Area LUMBRIDGE_AREA = new Area(new Tile(3231, 3233, 0), new Tile(3190, 3203, 3));

    //Integer IDS
    public static final Integer LUMBRIDGE_STAIRS_ID = 36230;
    public static final Integer WILDERNESS_TELEPORT_LEVEL = 20;
    public static final Integer FEROX_REFRESHMENT_POOL_ID = 39651;

    public static final Integer FEROX_BARRIER = 39656;

    //ArrayLists
    public final ArrayList<Task> TASK_LIST = new ArrayList<Task>();

    //Lists
    public static final List<Integer> GREEN_DRAGON_IDS = List.of(
            260,
            261,
            262,
            263,
            264,
            8073,
            8076,
            8082
    );
}