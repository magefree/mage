
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiderToken;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class GloomwidowsFeast extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public GloomwidowsFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");


        // Destroy target creature with flying. If that creature was blue or black, create a 1/2 green Spider creature token with reach.
        this.getSpellAbility().addEffect(new GloomwidowsFeastEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

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

    public GloomwidowsFeastEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature with flying. " +
                          "If that creature was blue or black, create a 1/2 green Spider creature token with reach";
    }

    public GloomwidowsFeastEffect(final GloomwidowsFeastEffect effect) {
        super(effect);
    }

    @Override
    public GloomwidowsFeastEffect copy() {
        return new GloomwidowsFeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature == null) {
            return false;
        }

        targetCreature.destroy(source, game, false);
        Permanent destroyedCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (destroyedCreature == null) {
            return false;
        }

        if (destroyedCreature.getColor(game).isBlue() || destroyedCreature.getColor(game).isBlack()) {
            SpiderToken token = new SpiderToken();
            token.putOntoBattlefield(1, game, source, source.getControllerId());
            return true;
        }
        return false;
    }
}
