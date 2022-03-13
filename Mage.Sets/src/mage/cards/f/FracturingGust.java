
package mage.cards.f;

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

/**
 *
 * @author LevelX2
 */
public final class FracturingGust extends CardImpl {

    public FracturingGust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G/W}{G/W}{G/W}");

        // Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way.
        this.getSpellAbility().addEffect(new FracturingGustDestroyEffect());
    }

    private FracturingGust(final FracturingGust card) {
        super(card);
    }

    @Override
    public FracturingGust copy() {
        return new FracturingGust(this);
    }
}
class FracturingGustDestroyEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();
    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                                 CardType.ENCHANTMENT.getPredicate()));
    }
    public FracturingGustDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way";
    }

    public FracturingGustDestroyEffect(final FracturingGustDestroyEffect effect) {
        super(effect);
    }

    @Override
    public FracturingGustDestroyEffect copy() {
        return new FracturingGustDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int destroyedPermanents = 0;
            for (Permanent permanent: game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (permanent.destroy(source, game, false)) {
                    ++destroyedPermanents;
                }
            }
            game.getState().processAction(game); // needed in case a destroyed permanent did prevent life gain
            if (destroyedPermanents > 0) {
                controller.gainLife(2 * destroyedPermanents, game, source);
            }
            return true;
        }
        return false;
    }
}
