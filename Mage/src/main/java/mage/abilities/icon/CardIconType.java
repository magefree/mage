package mage.abilities.icon;

/**
 * Icons for GUI card panel
 *
 * @author JayDi85
 */
public enum CardIconType {

    /*
      - Use svg icon file name from folder ..\Mage.Client\src\main\resources\card\icons
      - Look at readme-icons.txt file for more info about files format
      - Sort order can be same for icons (it will be sorted by text)
      - Sort order goes from 0 to 1000 (from first to last icons in normal order like START,
        but CENTER and END order can be different)
     */

    PLAYABLE_COUNT("prepared/cog.svg", CardIconCategory.PLAYABLE_COUNT, 100),
    //
    ABILITY_FLYING("prepared/feather-alt.svg", CardIconCategory.ABILITY, 100),
    ABILITY_DEFENDER("prepared/chess-rook.svg", CardIconCategory.ABILITY, 100),
    ABILITY_DEATHTOUCH("prepared/skull-crossbones.svg", CardIconCategory.ABILITY, 100),
    ABILITY_LIFELINK("prepared/link.svg", CardIconCategory.ABILITY, 100),
    ABILITY_DOUBLE_STRIKE("prepared/swords-two.svg", CardIconCategory.ABILITY, 100),
    ABILITY_FIRST_STRIKE("prepared/swords-one.svg", CardIconCategory.ABILITY, 100),
    ABILITY_CREW("prepared/truck-monster.svg", CardIconCategory.ABILITY, 100),
    ABILITY_TRAMPLE("prepared/grimace.svg", CardIconCategory.ABILITY, 100),
    ABILITY_HEXPROOF("prepared/expand-arrows-alt.svg", CardIconCategory.ABILITY, 100),
    ABILITY_INFECT("prepared/flask.svg", CardIconCategory.ABILITY, 100),
    ABILITY_INDESTRUCTIBLE("prepared/ankh.svg", CardIconCategory.ABILITY, 100),
    ABILITY_VIGILANCE("prepared/eye.svg", CardIconCategory.ABILITY, 100),
    ABILITY_CLASS_LEVEL("prepared/hexagon-fill.svg", CardIconCategory.ABILITY, 100),
    ABILITY_REACH("prepared/child-reaching.svg", CardIconCategory.ABILITY, 100),
    //
    OTHER_FACEDOWN("prepared/reply-fill.svg", CardIconCategory.ABILITY, 100),
    OTHER_COST_X("prepared/square-fill.svg", CardIconCategory.ABILITY, 100),
    //
    RINGBEARER("prepared/ring.svg", CardIconCategory.COMMANDER, 100),
    COMMANDER("prepared/crown.svg", CardIconCategory.COMMANDER, 100), // TODO: fix big size, see CardIconsPanel
    //
    SYSTEM_COMBINED("prepared/square-fill.svg", CardIconCategory.SYSTEM, 1000), // inner usage, must use last order
    SYSTEM_DEBUG("prepared/link.svg", CardIconCategory.SYSTEM, 1000); // used for test render dialog

    private final String resourceName;
    private final CardIconCategory category;
    private final int sortOrder;

    CardIconType(String resourceName, CardIconCategory category, int sortOrder) {
        this.resourceName = resourceName;
        this.category = category;
        this.sortOrder = sortOrder;
    }

    public CardIconCategory getCategory() {
        return category;
    }

    public String getResourceName() {
        return resourceName;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    @Override
    public String toString() {
        return resourceName;
    }
}
