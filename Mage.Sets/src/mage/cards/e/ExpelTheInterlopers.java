package mage.cards.e;

import mage.abilities.Ability;
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
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ExpelTheInterlopers extends CardImpl {

    public ExpelTheInterlopers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Choose a number between 0 and 10. Destroy all creatures with power greater than or equal to the chosen number.
        this.getSpellAbility().addEffect(new ExpelTheInterlopersEffect());
    }

    private ExpelTheInterlopers(final ExpelTheInterlopers card) {
        super(card);
    }

    @Override
    public ExpelTheInterlopers copy() {
        return new ExpelTheInterlopers(this);
    }
}

class ExpelTheInterlopersEffect extends OneShotEffect {

    ExpelTheInterlopersEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "choose a number between 0 and 10. Destroy all creatures with power greater than or equal to the chosen number";
    }

    private ExpelTheInterlopersEffect(final ExpelTheInterlopersEffect effect) {
        super(effect);
    }

    @Override
    public ExpelTheInterlopersEffect copy() {
        return new ExpelTheInterlopersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        // Choose a number between 0 and 10.
        int number = player.getAmount(0, 10, "Choose a number between 0 and 10", game);
        game.informPlayers(player.getLogName() + " has chosen the number " + number + "." + CardUtil.getSourceLogName(game, source));

        // Destroy all creatures with power greater than or equal to the chosen number.
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, number));

        return new DestroyAllEffect(filter).apply(game, source);
    }

}