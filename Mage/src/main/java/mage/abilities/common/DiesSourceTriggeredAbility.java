package mage.abilities.common;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DiesSourceTriggeredAbility extends ZoneChangeTriggeredAbility {

    private final SetTargetPointer setTargetPointer;
    // for returning the source card, must set target pointer to include mutated cards

    public DiesSourceTriggeredAbility(Effect effect, boolean optional, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, Zone.GRAVEYARD, effect, "When {this} dies, ", optional);
        switch (setTargetPointer) {
            case NONE:
            case CARD:
                break; // supported cases
            default:
                throw new IllegalArgumentException("Unsupported SetTargetPointer in DiesSourceTriggeredAbility: " + setTargetPointer);
        }
        this.setTargetPointer = setTargetPointer;
        this.withRuleTextReplacement(true); // default true to replace "{this}" with "it"
    }

    public DiesSourceTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, SetTargetPointer.NONE);
    }

    public DiesSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    protected DiesSourceTriggeredAbility(final DiesSourceTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DiesSourceTriggeredAbility copy() {
        return new DiesSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent() || !event.getTargetId().equals(getSourceId())) {
            return false;
        }
        getEffects().setValue("permanentLeftBattlefield", zEvent.getTarget());
        if (setTargetPointer == SetTargetPointer.CARD) {
            getEffects().setTargetPointer(new FixedTargets(
                    CardUtil.getAllCardsFromPermanentLeftBattlefield(zEvent.getTarget(), game), game));
        }
        return true;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }
}
