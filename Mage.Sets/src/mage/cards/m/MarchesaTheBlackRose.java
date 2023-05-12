
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DethroneAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class MarchesaTheBlackRose extends CardImpl {

    public MarchesaTheBlackRose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Dethrone
        this.addAbility(new DethroneAbility());

        // Other creatures you control have dethrone.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(new DethroneAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, true)));

        // Whenever a creature you control with a +1/+1 counter on it dies, return that card to the battlefield under your control at the beginning of the next end step.
        this.addAbility(new MarchesaTheBlackRoseTriggeredAbility());

    }

    private MarchesaTheBlackRose(final MarchesaTheBlackRose card) {
        super(card);
    }

    @Override
    public MarchesaTheBlackRose copy() {
        return new MarchesaTheBlackRose(this);
    }
}

class MarchesaTheBlackRoseTriggeredAbility extends TriggeredAbilityImpl {

    public MarchesaTheBlackRoseTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MarchesaTheBlackRoseEffect());
        setTriggerPhrase("Whenever a creature you control with a +1/+1 counter on it dies, ");
    }

    public MarchesaTheBlackRoseTriggeredAbility(final MarchesaTheBlackRoseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MarchesaTheBlackRoseTriggeredAbility copy() {
        return new MarchesaTheBlackRoseTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = ((ZoneChangeEvent) event).getTarget();
            if (permanent != null
                    && permanent.isControlledBy(this.getControllerId())
                    && permanent.isCreature(game)
                    && permanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game) + 1));
                }
                return true;
            }
        }
        return false;
    }
}

class MarchesaTheBlackRoseEffect extends OneShotEffect {

    MarchesaTheBlackRoseEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return that card to the battlefield under your control at the beginning of the next end step.";
    }

    MarchesaTheBlackRoseEffect(final MarchesaTheBlackRoseEffect effect) {
        super(effect);
    }

    @Override
    public MarchesaTheBlackRoseEffect copy() {
        return new MarchesaTheBlackRoseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            Effect effect = new ReturnToBattlefieldUnderYourControlTargetEffect();
            effect.setText("return that card to the battlefield under your control at the beginning of the next end step");
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
            delayedAbility.getEffects().get(0).setTargetPointer(getTargetPointer());
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}
