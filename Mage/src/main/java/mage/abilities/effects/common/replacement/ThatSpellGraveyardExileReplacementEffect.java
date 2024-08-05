package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author xenohedron
 */
public class ThatSpellGraveyardExileReplacementEffect extends ReplacementEffectImpl {

    public static final String RULE_A = "If that spell would be put into a graveyard, exile it instead.";
    public static final String RULE_YOUR = "If that spell would be put into your graveyard, exile it instead.";

    /**
     * If that spell would be put into a graveyard, exile it instead.
     * Must set target pointer to fixed target.
     */
    public ThatSpellGraveyardExileReplacementEffect(boolean yourGraveyard) {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = yourGraveyard ? RULE_YOUR : RULE_A;
    }

    protected ThatSpellGraveyardExileReplacementEffect(final ThatSpellGraveyardExileReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ThatSpellGraveyardExileReplacementEffect copy() {
        return new ThatSpellGraveyardExileReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() != Zone.GRAVEYARD) {
            return false;
        }
        Card cardMoving = game.getCard(zEvent.getTargetId());
        Card cardTarget = game.getCard(((FixedTarget) getTargetPointer()).getTarget());
        if (cardMoving == null 
                || cardTarget == null) {
            return false;
        }
        // for MDFC.
        Card mainCardMoving = cardMoving.getMainCard();
        Card mainCardTarget = cardTarget.getMainCard();
        return mainCardMoving != null
                && mainCardTarget != null
                && mainCardMoving.getId().equals(mainCardTarget.getId())
                && game.getState().getZoneChangeCounter(mainCardTarget.getId()) 
                == (game.getState().getZoneChangeCounter(mainCardMoving.getId()));
    }
}
