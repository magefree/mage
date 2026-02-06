package mage.player.ai.synergy;

/**
 * AI: Types of synergies the AI should recognize and protect.
 * These are powerful card interactions that aren't infinite combos
 * but provide significant strategic value.
 *
 * @author duxbuse
 */
public enum SynergyType {
    /**
     * Creatures that reduce or eliminate equipment equip costs.
     * Examples: Puresteel Paladin, Sigarda's Aid, Brass Squire
     */
    EQUIPMENT_ENABLER("Equipment Enabler", "Reduces or eliminates equipment costs"),

    /**
     * Creatures that buff other creatures of a specific type.
     * Examples: Lord of Atlantis, Goblin Chieftain
     */
    TRIBAL_LORD("Tribal Lord", "Buffs creatures of a specific type"),

    /**
     * Permanents that can sacrifice other permanents for value.
     * Examples: Viscera Seer, Ashnod's Altar
     */
    SACRIFICE_OUTLET("Sacrifice Outlet", "Enables sacrifice-based strategies"),

    /**
     * Creatures that trigger on enchantment events.
     * Examples: Enchantress's Presence, Mesa Enchantress
     */
    ENCHANTRESS("Enchantress", "Triggers on enchantment casts/ETB"),

    /**
     * Creatures that trigger on instant/sorcery casts.
     * Examples: Guttersnipe, Young Pyromancer
     */
    SPELLSLINGER("Spellslinger", "Triggers on instant/sorcery casts"),

    /**
     * Creatures that benefit from +1/+1 counters.
     * Examples: Hardened Scales, Winding Constrictor
     */
    COUNTER_SYNERGY("Counter Synergy", "Enhances +1/+1 counter strategies"),

    /**
     * Cards that trigger on artifact ETB or reduce artifact costs.
     * Examples: Foundry Inspector, Sai Master Thopterist
     */
    ARTIFACT_SYNERGY("Artifact Synergy", "Benefits from artifact density"),

    /**
     * Cards that enable graveyard-based strategies.
     * Examples: Entomb, Reanimate
     */
    GRAVEYARD_ENABLER("Graveyard Enabler", "Enables graveyard strategies"),

    /**
     * Cards that mill opponents as a win condition.
     * Examples: Hedron Crab, Glimpse the Unthinkable, Bruvac
     */
    MILL_STRATEGY("Mill Strategy", "Mills opponent's library as win condition");

    private final String displayName;
    private final String description;

    SynergyType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}
