package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.token.KnightToken;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author zeffirojoe
 */
public final class ValiantEndeavor extends CardImpl {

    public ValiantEndeavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Roll two d6 and choose on result. Destroy each creature with power greater
        // than or equal to that result. Then create a number of 2/2 white Knight
        // creature tokens with vigilance equal to the other result.
        this.getSpellAbility().addEffect(new ValiantEndeavorEffect());
    }

    private ValiantEndeavor(final ValiantEndeavor card) {
        super(card);
    }

    @Override
    public ValiantEndeavor copy() {
        return new ValiantEndeavor(this);
    }
}

class ValiantEndeavorEffect extends OneShotEffect {

    ValiantEndeavorEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Roll two d6 and choose one result. Destroy each creature " +
                "with power greater than or equal to that result. Then create a number of " +
                "2/2 white Knight creature tokens with vigilance equal to the other result.";
    }

    private ValiantEndeavorEffect(final ValiantEndeavorEffect effect) {
        super(effect);
    }

    @Override
    public ValiantEndeavorEffect copy() {
        return new ValiantEndeavorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Integer> results = controller.rollDice(outcome, source, game, 6, 2, 0);
        int firstResult = results.get(0);
        int secondResult = results.get(1);
        int first, second;
        if (firstResult != secondResult
                && controller.chooseUse(outcome, "Destroy each creature with power greater than or equal to your choice",
                "The other number will be the amount of 2/2 white Knight tokens with vigilance.",
                "" + firstResult, "" + secondResult, source, game)) {
            first = firstResult;
            second = secondResult;
        } else {
            first = secondResult;
            second = firstResult;
        }

        final FilterCreaturePermanent filter = new FilterCreaturePermanent(
                String.format("creatures with power greater than or equal to %s", first));
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, first - 1));

        Effect wrathEffect = new DestroyAllEffect(filter);
        wrathEffect.apply(game, source);

        new KnightToken().putOntoBattlefield(second, game, source, source.getControllerId());
        return true;
    }
}
