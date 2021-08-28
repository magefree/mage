package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecklessEndeavor extends CardImpl {

    public RecklessEndeavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // Roll two d12 and choose one result. Reckless Endeavor deals damage equal to that result to each creature. Then create a number of Treasure tokens equal to the other result.
        this.getSpellAbility().addEffect(new RecklessEndeavorEffect());
    }

    private RecklessEndeavor(final RecklessEndeavor card) {
        super(card);
    }

    @Override
    public RecklessEndeavor copy() {
        return new RecklessEndeavor(this);
    }
}

class RecklessEndeavorEffect extends OneShotEffect {

    RecklessEndeavorEffect() {
        super(Outcome.Benefit);
        staticText = "roll two d12 and choose one result. {this} deals damage equal to that result " +
                "to each creature. Then create a number of Treasure tokens equal to the other result";
    }

    private RecklessEndeavorEffect(final RecklessEndeavorEffect effect) {
        super(effect);
    }

    @Override
    public RecklessEndeavorEffect copy() {
        return new RecklessEndeavorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Integer> results = player.rollDice(outcome, source, game, 12, 2, 0);
        int firstResult = results.get(0);
        int secondResult = results.get(1);
        int first, second;
        if (firstResult != secondResult && player.chooseUse(
                outcome, "Choose a number to deal damage to each creature",
                "The other number will be the amount of treasures you create",
                "" + firstResult, "" + secondResult, source, game
        )) {
            first = firstResult;
            second = secondResult;
        } else {
            first = secondResult;
            second = firstResult;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game
        )) {
            permanent.damage(first, source.getSourceId(), source, game);
        }
        new TreasureToken().putOntoBattlefield(second, game, source, source.getControllerId());
        return true;
    }
}
