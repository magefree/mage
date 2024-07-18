package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MistbreathElder extends CardImpl {

    public MistbreathElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, return another creature you control to its owner's hand. If you do, put a +1/+1 counter on Mistbreath Elder. Otherwise, you may return Mistbreath Elder to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new MistbreathElderEffect(),
                new ReturnToHandChosenControlledPermanentCost(
                        new TargetControlledPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL)
                ), false
        ), TargetController.YOU, false));
    }

    private MistbreathElder(final MistbreathElder card) {
        super(card);
    }

    @Override
    public MistbreathElder copy() {
        return new MistbreathElder(this);
    }
}

class MistbreathElderEffect extends OneShotEffect {

    MistbreathElderEffect() {
        super(Outcome.Benefit);
        staticText = "you may return {this} to its owner's hand";
    }

    private MistbreathElderEffect(final MistbreathElderEffect effect) {
        super(effect);
    }

    @Override
    public MistbreathElderEffect copy() {
        return new MistbreathElderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return player != null && permanent != null
                && player.chooseUse(Outcome.ReturnToHand, "Return " +
                permanent.getIdName() + " to its owner's hand?", source, game)
                && player.moveCards(permanent, Zone.HAND, source, game);
    }
}
