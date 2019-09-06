package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.BearToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlaxenIntruder extends AdventureCard {

    public FlaxenIntruder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{G}", "Welcome Home", "{5}{G}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Flaxen Intruder deals combat damage to a player, you may sacrifice it. When you do, destroy target artifact or enchantment.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(
                new FlaxenIntruderCreateReflexiveTriggerEffect(), new SacrificeSourceCost()
        ).setText("you may sacrifice it. When you do, destroy target artifact or enchantment."), false));

        // Welcome Home
        // Create three 2/2 green Bear creature tokens.
        this.getAdventureSpellAbility().addEffect(new CreateTokenEffect(new BearToken(), 3));
    }

    private FlaxenIntruder(final FlaxenIntruder card) {
        super(card);
    }

    @Override
    public FlaxenIntruder copy() {
        return new FlaxenIntruder(this);
    }
}

class FlaxenIntruderCreateReflexiveTriggerEffect extends OneShotEffect {

    FlaxenIntruderCreateReflexiveTriggerEffect() {
        super(Outcome.Benefit);
    }

    private FlaxenIntruderCreateReflexiveTriggerEffect(final FlaxenIntruderCreateReflexiveTriggerEffect effect) {
        super(effect);
    }

    @Override
    public FlaxenIntruderCreateReflexiveTriggerEffect copy() {
        return new FlaxenIntruderCreateReflexiveTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new FlaxenIntruderReflexiveTriggeredAbility(), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class FlaxenIntruderReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    FlaxenIntruderReflexiveTriggeredAbility() {
        super(new DestroyTargetEffect(), Duration.OneUse, true);
        this.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
    }

    private FlaxenIntruderReflexiveTriggeredAbility(final FlaxenIntruderReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FlaxenIntruderReflexiveTriggeredAbility copy() {
        return new FlaxenIntruderReflexiveTriggeredAbility(this);
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
        return "When you do, destroy target artifact or enchantment.";
    }
}