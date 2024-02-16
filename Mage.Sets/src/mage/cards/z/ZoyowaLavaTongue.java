package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.DescendedThisTurnCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.DescendedThisTurnCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.DescendedWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class ZoyowaLavaTongue extends CardImpl {

    public ZoyowaLavaTongue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // At the beginning of your end step, if you descended this turn, each opponent may discard a card or sacrifice a permanent. Zoyowa Lava-Tongue deals 3 damage to each opponent who didn't.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ZoyowaLavaTongueEffect(), TargetController.YOU,
                DescendedThisTurnCondition.instance, false
        ).addHint(DescendedThisTurnCount.getHint()), new DescendedWatcher());
    }

    private ZoyowaLavaTongue(final ZoyowaLavaTongue card) {
        super(card);
    }

    @Override
    public ZoyowaLavaTongue copy() {
        return new ZoyowaLavaTongue(this);
    }
}

class ZoyowaLavaTongueEffect extends OneShotEffect {

    ZoyowaLavaTongueEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent may discard a card or sacrifice a permanent. "
                + "{this} deals 3 damage to each opponent who didn't";
    }

    private ZoyowaLavaTongueEffect(final ZoyowaLavaTongueEffect effect) {
        super(effect);
    }

    @Override
    public ZoyowaLavaTongueEffect copy() {
        return new ZoyowaLavaTongueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> opponentsIds = game.getOpponents(source.getControllerId());

        List<Player> opponentThatDidNotPay = new ArrayList<>();
        for (UUID id : game.getState().getPlayerList(source.getControllerId())) {
            if (!opponentsIds.contains(id)) {
                continue;
            }

            Player opponent = game.getPlayer(id);
            if (opponent == null) {
                continue;
            }

            Cost cost = new OrCost(
                    "discard a card or sacrifice a permanent?",
                    new DiscardCardCost(),
                    new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_SHORT_TEXT)
            );

            boolean choseToPay = cost.canPay(source, source, id, game)
                    && opponent.chooseUse(
                    Outcome.Neutral,
                    "Do you wish to discard a card or sacrifice a permanent "
                            + "to avoid 3 damage to you?",
                    source, game
            );
            if (choseToPay && cost.pay(source, game, source, id, false)) {
                continue;
            }

            opponentThatDidNotPay.add(opponent);
        }

        for (Player opponent : opponentThatDidNotPay) {
            opponent.damage(3, source.getSourceId(), source, game);
        }
        return true;
    }
}