
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SpiritBlueToken;
import mage.game.permanent.token.Token;
import mage.target.TargetSpell;

/**
 *
 * @author noxx
 */
public final class GeistSnatch extends CardImpl {

    public GeistSnatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Counter target creature spell. Create a 1/1 blue Spirit creature token with flying.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));
        this.getSpellAbility().addEffect(new GeistSnatchCounterTargetEffect());
    }

    private GeistSnatch(final GeistSnatch card) {
        super(card);
    }

    @Override
    public GeistSnatch copy() {
        return new GeistSnatch(this);
    }
}

class GeistSnatchCounterTargetEffect extends OneShotEffect {

    public GeistSnatchCounterTargetEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target creature spell. Create a 1/1 blue Spirit creature token with flying";
    }

    private GeistSnatchCounterTargetEffect(final GeistSnatchCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public GeistSnatchCounterTargetEffect copy() {
        return new GeistSnatchCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getStack().counter(source.getFirstTarget(), source, game);
        Token token = new SpiritBlueToken();
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        return true;
    }
}
