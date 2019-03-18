
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class DarettiScrapSavantEmblem extends Emblem {
    // You get an emblem with "Whenever an artifact is put into your graveyard from the battlefield, return that card to the battlefield at the beginning of the next end step."

    public DarettiScrapSavantEmblem() {
        setName("Emblem Daretti");
        this.setExpansionSetCodeForImage("C14");

        this.getAbilities().add(new DarettiScrapSavantTriggeredAbility());
    }
}

class DarettiScrapSavantTriggeredAbility extends TriggeredAbilityImpl {

    DarettiScrapSavantTriggeredAbility() {
        super(Zone.COMMAND, new DarettiScrapSavantEffect(), false);
    }

    DarettiScrapSavantTriggeredAbility(final DarettiScrapSavantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DarettiScrapSavantTriggeredAbility copy() {
        return new DarettiScrapSavantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getFromZone() == Zone.BATTLEFIELD
                && zEvent.getTarget().isArtifact()
                && zEvent.getTarget().isOwnedBy(this.controllerId)) {
            this.getEffects().setTargetPointer(new FixedTarget(zEvent.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an artifact is put into your graveyard from the battlefield, " + super.getRule();
    }
}

class DarettiScrapSavantEffect extends OneShotEffect {

    DarettiScrapSavantEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return that card to the battlefield at the beginning of the next end step";
    }

    DarettiScrapSavantEffect(final DarettiScrapSavantEffect effect) {
        super(effect);
    }

    @Override
    public DarettiScrapSavantEffect copy() {
        return new DarettiScrapSavantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
            effect.setText("return that card to the battlefield at the beginning of the next end step");
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(Zone.COMMAND, effect, TargetController.ANY), source);
            return true;
        }
        return false;
    }
}
