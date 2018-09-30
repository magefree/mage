package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class Hypothesizzle extends CardImpl {

    public Hypothesizzle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{R}");

        // Draw two cards. Then you may discard a nonland card. When you do, Hypothesizzle deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new HypothesizzleCreateReflexiveTriggerEffect(),
                new DiscardCardCost(StaticFilters.FILTER_CARD_A_NON_LAND),
                "Discard a nonland card to deal 4 damage?"
        ).setText("Then you may discard a nonland card. "
                + "When you do, {this} deals 4 damage to target creature."));
    }

    public Hypothesizzle(final Hypothesizzle card) {
        super(card);
    }

    @Override
    public Hypothesizzle copy() {
        return new Hypothesizzle(this);
    }
}

class HypothesizzleCreateReflexiveTriggerEffect extends OneShotEffect {

    public HypothesizzleCreateReflexiveTriggerEffect() {
        super(Outcome.Benefit);
        this.staticText = "When you do, it deals 4 damage to target creature";
    }

    public HypothesizzleCreateReflexiveTriggerEffect(final HypothesizzleCreateReflexiveTriggerEffect effect) {
        super(effect);
    }

    @Override
    public HypothesizzleCreateReflexiveTriggerEffect copy() {
        return new HypothesizzleCreateReflexiveTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new HypothesizzleReflexiveTriggeredAbility(), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class HypothesizzleReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    public HypothesizzleReflexiveTriggeredAbility() {
        super(new DamageTargetEffect(4), Duration.OneUse, true);
        this.addTarget(new TargetCreaturePermanent());
    }

    public HypothesizzleReflexiveTriggeredAbility(final HypothesizzleReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HypothesizzleReflexiveTriggeredAbility copy() {
        return new HypothesizzleReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "When you discard a nonland card, "
                + "{this} deals 4 damage to target creature";
    }
}
