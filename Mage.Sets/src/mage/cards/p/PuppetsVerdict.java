
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class PuppetsVerdict extends CardImpl {

    public PuppetsVerdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}{R}");

        // Flip a coin. If you win the flip, destroy all creatures with power 2 or less. If you lose the flip, destroy all creatures with power 3 or greater.
        this.getSpellAbility().addEffect(new PuppetsVerdictEffect());
    }

    private PuppetsVerdict(final PuppetsVerdict card) {
        super(card);
    }

    @Override
    public PuppetsVerdict copy() {
        return new PuppetsVerdict(this);
    }
}

class PuppetsVerdictEffect extends OneShotEffect {

    public PuppetsVerdictEffect() {
        super(Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, destroy all creatures with power 2 or less. If you lose the flip, destroy all creatures with power 3 or greater";
    }

    private PuppetsVerdictEffect(final PuppetsVerdictEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.flipCoin(source, game, true)) {
                
                FilterCreaturePermanent filterPower2OrLess = new FilterCreaturePermanent("all creatures power 2 or less");
                filterPower2OrLess.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
                for (Permanent permanent: game.getBattlefield().getAllActivePermanents(filterPower2OrLess, game)) {
                    permanent.destroy(source, game, false);
                }
                return true;
            } else {
                FilterCreaturePermanent filterPower3OrGreater = new FilterCreaturePermanent("all creatures power 3 or greater");
                filterPower3OrGreater.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
                for (Permanent permanent: game.getBattlefield().getAllActivePermanents(filterPower3OrGreater, game)) {
                    permanent.destroy(source, game, false);
                }
                return true;
                }
            }
        return false;
    }

    @Override
    public PuppetsVerdictEffect copy() {
        return new PuppetsVerdictEffect(this);
    }
}
