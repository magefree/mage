package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraveEndeavor extends CardImpl {

    public GraveEndeavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{B}{B}");

        // Roll two d10 and choose one result. Return a creature card from your graveyard to the battlefield with a number of +1/+1 counters on it equal to that result. Then each opponent loses X life and you gain X life, where X is the other result.
        this.getSpellAbility().addEffect(new GraveEndeavorEffect());
    }

    private GraveEndeavor(final GraveEndeavor card) {
        super(card);
    }

    @Override
    public GraveEndeavor copy() {
        return new GraveEndeavor(this);
    }
}

class GraveEndeavorEffect extends OneShotEffect {

    GraveEndeavorEffect() {
        super(Outcome.Benefit);
        staticText = "roll two d10 and choose one result. Return a creature card from your graveyard " +
                "to the battlefield with a number of +1/+1 counters on it equal to that result. " +
                "Then each opponent loses X life and you gain X life, where X is the other result";
    }

    private GraveEndeavorEffect(final GraveEndeavorEffect effect) {
        super(effect);
    }

    @Override
    public GraveEndeavorEffect copy() {
        return new GraveEndeavorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Integer> results = player.rollDice(outcome, source, game, 10, 2, 0);
        int firstResult = results.get(0);
        int secondResult = results.get(1);
        int first, second;
        if (firstResult != secondResult && player.chooseUse(
                outcome, "Choose a number of +1/+1 counters to put on the creature you return",
                "The other number will be the amount of life your opponents lose and you gain",
                "" + firstResult, "" + secondResult, source, game
        )) {
            first = firstResult;
            second = secondResult;
        } else {
            first = secondResult;
            second = firstResult;
        }
        if (player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) > 0) {
            TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
            target.setNotTarget(true);
            player.choose(outcome, target, source, game);
            if (target.getFirstTarget() != null) {
                new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(
                        CounterType.P1P1.createInstance(first)
                ).setTargetPointer(new FixedTarget(target.getFirstTarget(), game)).apply(game, source);
            }
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            opponent.loseLife(second, game, source, false);
        }
        player.gainLife(second, game, source);
        return true;
    }
}
