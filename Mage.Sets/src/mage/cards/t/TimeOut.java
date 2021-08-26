
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author spjspj
 */
public final class TimeOut extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nonland permanent");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public TimeOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // Roll a six-sided die. Put target nonland permanent into its owner's library just beneath the top X cards of that library, where X is the result.
        this.getSpellAbility().addEffect(new TimeOutEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

    }

    private TimeOut(final TimeOut card) {
        super(card);
    }

    @Override
    public TimeOut copy() {
        return new TimeOut(this);
    }
}

class TimeOutEffect extends OneShotEffect {

    public TimeOutEffect() {
        super(Outcome.Benefit);
        this.staticText = "Roll a six-sided die. Put target nonland permanent into its owner's library just beneath the top X cards of that library, where X is the result";
    }

    public TimeOutEffect(final TimeOutEffect effect) {
        super(effect);
    }

    @Override
    public TimeOutEffect copy() {
        return new TimeOutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                Player owner = game.getPlayer(permanent.getOwnerId());
                if (owner == null) {
                    return false;
                }
                int amount = controller.rollDice(outcome, source, game, 6);
                controller.putCardOnTopXOfLibrary(permanent, game, source, amount, true);
                return true;
            }
        }
        return false;
    }
}
