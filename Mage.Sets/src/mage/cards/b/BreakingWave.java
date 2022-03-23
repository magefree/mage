
package mage.cards.b;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LoneFox
 *
 */
public final class BreakingWave extends CardImpl {

    public BreakingWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        Effect effect = new BreakingWaveEffect();
        // You may cast Breaking Wave as though it had flash if you pay {2} more to cast it.
        Ability ability = new PayMoreToCastAsThoughtItHadFlashAbility(this, new ManaCostsImpl("{2}"));
        ability.addEffect(effect);
        this.addAbility(ability);
        // Simultaneously untap all tapped creatures and tap all untapped creatures.
        this.getSpellAbility().addEffect(effect);
    }

    private BreakingWave(final BreakingWave card) {
        super(card);
    }

    @Override
    public BreakingWave copy() {
        return new BreakingWave(this);
    }
}

class BreakingWaveEffect extends OneShotEffect {

    public BreakingWaveEffect() {
        super(Outcome.Neutral);
        staticText = "Simultaneously untap all tapped creatures and tap all untapped creatures.";
    }

    public BreakingWaveEffect(BreakingWaveEffect copy) {
        super(copy);
    }

    @Override
    public BreakingWaveEffect copy() {
        return new BreakingWaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> creatures = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game);
        for (Permanent creature : creatures) {
            if (creature.isTapped()) {
                creature.untap(game);
            } else {
                creature.tap(source, game);
            }
        }
        return true;
    }
}
