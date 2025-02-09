package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class EntersBattlefieldTriggeredAbility extends TriggeredAbilityImpl {

    static public boolean ENABLE_TRIGGER_PHRASE_AUTO_FIX = false;

    public EntersBattlefieldTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public EntersBattlefieldTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.ALL, effect, optional); // Zone.All because a creature with trigger can be put into play and be sacrificed during the resolution of an effect (discard Obstinate Baloth with Smallpox)
        this.withRuleTextReplacement(true); // default true to replace "{this}" with "it" or "this creature"

        // warning, it's impossible to add text auto-replacement for creatures here (When this creature enters),
        // so it was implemented in CardImpl.addAbility instead
        // see https://github.com/magefree/mage/issues/12791
        setTriggerPhrase("When {this} enters, ");
    }

    protected EntersBattlefieldTriggeredAbility(final EntersBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getSourceId())) {
            this.getEffects().setValue("permanentEnteredBattlefield", game.getPermanent(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public EntersBattlefieldTriggeredAbility copy() {
        return new EntersBattlefieldTriggeredAbility(this);
    }

    @Override
    public EntersBattlefieldTriggeredAbility setTriggerPhrase(String triggerPhrase) {
        super.setTriggerPhrase(triggerPhrase);
        return this;
    }

    /**
     * Find description of "{this}" like "this creature"
     */
    static public String getThisObjectDescription(Card card) {
        // prepare {this} description

        // short names like Aatchik for Aatchik, Emerald Radian
        // except: Mu Yanling, Wind Rider (maybe related to spaces in name)
        List<String> parts = Arrays.asList(card.getName().split(","));
        if (parts.size() > 1 && !parts.get(0).contains(" ")) {
            return parts.get(0);
        }

        // some types have priority, e.g. Vehicle instead artifact, example: Boommobile
        if (card.getSubtype().contains(SubType.VEHICLE)) {
            return "this Vehicle";
        }
        if (card.getSubtype().contains(SubType.AURA)) {
            return "this Aura";
        }

        // by priority
        if (card.isCreature()) {
            return "this creature";
        } else if (card.isPlaneswalker()) {
            return "this planeswalker";
        } else if (card.isLand()) {
            return "this land";
        } else if (card.isEnchantment()) {
            return "this enchantment";
        } else if (card.isArtifact()) {
            return "this artifact";
        } else {
            return "this permanent";
        }
    }

    public static List<String> getPossibleTriggerPhrases() {
        // for verify tests - must be same list as above (only {this} relates phrases)
        return Arrays.asList(
                "when this creature enters",
                "when this planeswalker enters",
                "when this land enters",
                "when this enchantment enters",
                "when this artifact enters",
                "when this permanent enters"
        );
    }
}
