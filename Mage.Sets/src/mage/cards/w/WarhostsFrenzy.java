package mage.cards.w;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarhostsFrenzy extends CardImpl {

    public WarhostsFrenzy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Kicker {B}
        this.addAbility(new KickerAbility("{B}"));

        // Creatures you control get +2/+0 until end of turn. If this spell was kicked, whenever a creature you control dies this turn, draw a card.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateDelayedTriggeredAbilityEffect(new WarhostsFrenzyDelayedTriggeredAbility()),
                KickedCondition.ONCE, "If this spell was kicked, " +
                "whenever a creature you control dies this turn, draw a card"
        ));
    }

    private WarhostsFrenzy(final WarhostsFrenzy card) {
        super(card);
    }

    @Override
    public WarhostsFrenzy copy() {
        return new WarhostsFrenzy(this);
    }
}

class WarhostsFrenzyDelayedTriggeredAbility extends DelayedTriggeredAbility {

    WarhostsFrenzyDelayedTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(1), Duration.EndOfTurn, false, false);
    }

    private WarhostsFrenzyDelayedTriggeredAbility(final WarhostsFrenzyDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WarhostsFrenzyDelayedTriggeredAbility copy() {
        return new WarhostsFrenzyDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent()
                && zEvent.getTarget() != null
                && zEvent.getTarget().isCreature(game)
                && zEvent.getTarget().isControlledBy(getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control dies this turn, draw a card";
    }
}
