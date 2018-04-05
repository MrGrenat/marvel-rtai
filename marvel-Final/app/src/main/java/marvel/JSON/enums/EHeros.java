package marvel.JSON.enums;

/**
 * Created by potmane on 21/03/2018.
 */

public enum EHeros {

    IRON_MAN("Iron Man"),
    DEADPOOL("Deadpool"),
    CAPTAIN_AMERICA("Captain America"),
    BLACK_WIDOW("Black Widow"),
    HULK("HULK"),
    ROGUE("Rogue"),
    TROIS_D_MAN("3-D Man"),
    SQUIRREL_GIRL("Squirrel Girl"),
    NOVA("Nova"),
    FALCON("Falcon");

    private String name;

    EHeros(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        return this.name;
    }
}
