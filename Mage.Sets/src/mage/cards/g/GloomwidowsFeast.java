
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiderToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GloomwidowsFeast extends CardImpl {

    public GloomwidowsFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Destroy target creature with flying. If that creature was blue or black, create a 1/2 green Spider creature token with reach.
        this.getSpellAbility().addEffect(new GloomwidowsFeastEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));

    }

    private GloomwidowsFeast(final GloomwidowsFeast card) {
        super(card);
    }

    @Override
    public GloomwidowsFeast copy() {
        return new GloomwidowsFeast(this);
    }
}

class GloomwidowsFeastEffect extends OneShotEffect {

    boolean applied = false;

    public GloomwidowsFeastEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature with flying. If that creature was blue or black, create a 1/2 green Spider creature token with reach";
    }

    private GloomwidowsFeastEffect(final GloomwidowsFeastEffect effect) {
        super(effect);
    }

    @Override
    public GloomwidowsFeastEffect copy() {
        return new GloomwidowsFeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            targetCreature.destroy(source, game, false);
            Permanent destroyedCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
            if (destroyedCreature.getColor(game).isBlue()
                    || destroyedCreature.getColor(game).isBlack()) {
                SpiderToken token = new SpiderToken();
                token.putOntoBattlefield(1, game, source, source.getControllerId());
                return true;
            }
        }
        return false;
    }
}
