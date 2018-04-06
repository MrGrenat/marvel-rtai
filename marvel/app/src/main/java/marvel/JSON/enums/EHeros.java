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
    FALCON("Falcon"),

    BLADE("Blade"),
    BLACK_PANTHER("Black Panther"),
    DOCTORE_STRANGE("Doctor Strange"),
    IRON_FIST("Iron Fist (Danny Rand)"),
    GROOT("Groot"),
    ROCKET_RACOON("Rocket Raccoon"),
    ICEMAN("Iceman"),
    JEAN_GREY("Jean Grey"),
    WAR_MACHINE("War Machine (Ultimate)"),
    CYCLOPS("Cyclops"),

    DAREDEVIL("Daredevil"),
    THOR("Thor"),
    HOWKEYE("Hawkeye"),
    GHOST_RIDER("Johnny Blaze"),
    THE_PUNISHER("Punisher"),
    BLACK_BOLT("Black Bolt"),
    GAMBIT("Gambit"),
    MOON_KNIGHT("Moon Knight"),
    SPIDER_MAN("Spider-Man"),
    VISION("Vision"),

    ;

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
