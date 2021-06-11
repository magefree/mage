package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PhyrexianRebirthHorrorToken;

import java.util.UUID;

/**
 * @author ayratn
 */
public final class PhyrexianRebirth extends CardImpl {

    public PhyrexianRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Destroy all creatures, then create an X/X colorless Horror artifact creature token, where X is the number of creatures destroyed this way.
        this.getSpellAbility().addEffect(new PhyrexianRebirthEffect());
    }

    private PhyrexianRebirth(final PhyrexianRebirth card) {
        super(card);
    }

    @Override
    public PhyrexianRebirth copy() {
        return new PhyrexianRebirth(this);
    }

    private static final class PhyrexianRebirthEffect extends OneShotEffect {

        private PhyrexianRebirthEffect() {
            super(Outcome.DestroyPermanent);
            staticText = "Destroy all creatures, then create an X/X colorless Phyrexian Horror artifact creature token, " +
                    "where X is the number of creatures destroyed this way";
        }

        private PhyrexianRebirthEffect(PhyrexianRebirthEffect ability) {
            super(ability);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            int count = 0;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(
                    StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game
            )) {
                count += permanent.destroy(source, game, false) ? 1 : 0;
            }
            new PhyrexianRebirthHorrorToken(count, count).putOntoBattlefield(
                    1, game, source, source.getControllerId()
            );
            return true;
        }

        @Override
        public PhyrexianRebirthEffect copy() {
            return new PhyrexianRebirthEffect(this);
        }
    }
}
