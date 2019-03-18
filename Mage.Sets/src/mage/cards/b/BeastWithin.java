
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BeastToken;
import mage.target.TargetPermanent;

/**
 *
 * @author North, Loki
 */
public final class BeastWithin extends CardImpl {

    public BeastWithin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Destroy target permanent. Its controller creates a 3/3 green Beast creature token.
        this.getSpellAbility().addTarget(new TargetPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new BeastWithinEffect());
    }

    public BeastWithin(final BeastWithin card) {
        super(card);
    }

    @Override
    public BeastWithin copy() {
        return new BeastWithin(this);
    }
}

class BeastWithinEffect extends OneShotEffect {

    public BeastWithinEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Its controller creates a 3/3 green Beast creature token";
    }

    public BeastWithinEffect(final BeastWithinEffect effect) {
        super(effect);
    }

    @Override
    public BeastWithinEffect copy() {
        return new BeastWithinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            new BeastToken().putOntoBattlefield(1, game, source.getSourceId(), permanent.getControllerId());
        }
        return true;
    }

}
