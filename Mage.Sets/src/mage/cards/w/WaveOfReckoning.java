
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class WaveOfReckoning extends CardImpl {

    public WaveOfReckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");


        // Each creature deals damage to itself equal to its power.
        getSpellAbility().addEffect(new WaveOfReckoningDamageEffect());
    }

    private WaveOfReckoning(final WaveOfReckoning card) {
        super(card);
    }

    @Override
    public WaveOfReckoning copy() {
        return new WaveOfReckoning(this);
    }
}

class WaveOfReckoningDamageEffect extends OneShotEffect {

    public WaveOfReckoningDamageEffect() {
            super(Outcome.Detriment);
            staticText = "each creature deals damage to itself equal to its power";
        }

        public WaveOfReckoningDamageEffect(final WaveOfReckoningDamageEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {

            FilterPermanent filter = new FilterPermanent();
            filter.add(CardType.CREATURE.getPredicate());

            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                int amount = permanent.getPower().getValue();
                permanent.damage(amount, permanent.getId(), source, game, false, true);
            }
            return true;
        }

        @Override
        public WaveOfReckoningDamageEffect copy() {
            return new WaveOfReckoningDamageEffect(this);
        }
    }