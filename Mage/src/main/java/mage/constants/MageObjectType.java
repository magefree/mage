package mage.constants;

/**
 * 109.1. An object is an ability on the stack, a card, a copy of a card, a
 * token, a spell, a permanent, or an emblem. 109.2. If a spell or ability uses
 * a description of an object that includes a card type or subtype, but doesn't
 * include the word "card," "spell," "source," or "scheme," it means a permanent
 * of that card type or subtype on the battlefield. 109.2a If a spell or ability
 * uses a description of an object that includes the word "card" and the name of
 * a zone, it means a card matching that description in the stated zone. #
 * 109.2b If a spell or ability uses a description of an object that includes
 * the word "spell," it means a spell matching that description on the stack. #
 * 109.2c If a spell or ability uses a description of an object that includes
 * the word "source," it means a source matching that description -- either a
 * source of an ability or a source of damage -- in any zone. See rule 609.7. #
 * 109.2d If an ability of a scheme card includes the text "this scheme," it
 * means the scheme card in the command zone on which that ability is printed. #
 * 109.3. An object's characteristics are name, mana cost, color, color
 * indicator, card type, subtype, supertype, expansion symbol, rules text,
 * abilities, power, toughness, loyalty, hand modifier, and life modifier.
 * Objects can have some or all of these characteristics. Any other information
 * about an object isn't a characteristic. For example, characteristics don't
 * include whether a permanent is tapped, a spell's target, an object's owner or
 * controller, what an Aura enchants, and so on. # 109.4. Only objects on the
 * stack or on the battlefield have a controller. Objects that are neither on
 * the stack nor on the battlefield aren't controlled by any player. See rule
 * 108.4. There are four exceptions to this rule: # 109.4a An emblem is
 * controlled by the player that puts it into the command zone. See rule 113,
 * "Emblems." # 109.4b In a Planechase game, a face-up plane or phenomenon card
 * is controlled by the player designated as the planar controller. This is
 * usually the active player. See rule 901.6. # 109.4c In a Vanguard game, each
 * vanguard card is controlled by its owner. See rule 902.6. # 109.4d In an
 * Archenemy game, each scheme card is controlled by its owner. See rule 904.7.
 * # 109.5. The words "you" and "your" on an object refer to the object's
 * controller, its would-be controller (if a player is attempting to play, cast,
 * or activate it), or its owner (if it has no controller). For a static
 * ability, this is the current controller of the object it's on. For an
 * activated ability, this is the player who activated the ability. For a
 * triggered ability, this is the controller of the object when the ability
 * triggered, unless it's a delayed triggered ability. To determine the
 * controller of a delayed triggered ability, see rules 603.7d-f
 *
 * @author LevelX2
 */
public enum MageObjectType {
    ABILITY_STACK("Ability on the Stack", false, false),
    CARD("Card", false, true),
    COPY_CARD("Copy of a Card", false, true),
    TOKEN("Token", true, true),
    SPELL("Spell", false, true),
    PERMANENT("Permanent", true, true),
    DUNGEON("Dungeon", false, false),
    EMBLEM("Emblem", false, false),
    COMMANDER("Commander", false, false),
    DESIGNATION("Designation", false, false),
    PLANE("Plane", false, false),
    NULL("NullObject", false, false);

    private final String text;
    private final boolean permanent;
    private final boolean canHaveCounters;

    MageObjectType(String text, boolean permanent, boolean canHaveCounters) {
        this.text = text;
        this.permanent = permanent;
        this.canHaveCounters = canHaveCounters;
    }

    @Override
    public String toString() {
        return text;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public boolean canHaveCounters() {
        return canHaveCounters;
    }

}
