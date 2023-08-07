
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class RighteousFury extends CardImpl {

    public RighteousFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}");

        // Destroy all tapped creatures. You gain 2 life for each creature destroyed this way.
        this.getSpellAbility().addEffect(new RighteousFuryEffect());
    }

    private RighteousFury(final RighteousFury card) {
        super(card);
    }

    @Override
    public RighteousFury copy() {
        return new RighteousFury(this);
    }
}

class RighteousFuryEffect extends OneShotEffect {

    public RighteousFuryEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all tapped creatures. You gain 2 life for each creature destroyed this way";
    }

    public RighteousFuryEffect(final RighteousFuryEffect effect) {
        super(effect);
    }

    @Override
    public RighteousFuryEffect copy() {
        return new RighteousFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int destroyedCreature = 0;
            FilterCreaturePermanent filter = new FilterCreaturePermanent("all tapped creatures");
            filter.add(TappedPredicate.TAPPED);
            for(Permanent creature: game.getState().getBattlefield().getActivePermanents(filter, controller.getId(), game)) {
                if (creature.destroy(source, game, false)) {
                    destroyedCreature++;
                }
            }
            if (destroyedCreature > 0) {
                game.getState().processAction(game);
                new GainLifeEffect(destroyedCreature * 2).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
