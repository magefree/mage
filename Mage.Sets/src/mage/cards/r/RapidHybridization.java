
package mage.cards.r;

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
import mage.game.permanent.token.RapidHybridizationToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class RapidHybridization extends CardImpl {

    public RapidHybridization(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Destroy target creature. It can't be regenerated. That creature's controller creates a 3/3 green Frog Lizard creature token.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addEffect(new RapidHybridizationEffect());
    }

    public RapidHybridization(final RapidHybridization card) {
        super(card);
    }

    @Override
    public RapidHybridization copy() {
        return new RapidHybridization(this);
    }
}

class RapidHybridizationEffect extends OneShotEffect {

    public RapidHybridizationEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "That creature's controller creates a 3/3 green Frog Lizard creature token";
    }

    public RapidHybridizationEffect(final RapidHybridizationEffect effect) {
        super(effect);
    }

    @Override
    public RapidHybridizationEffect copy() {
        return new RapidHybridizationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(targetPointer.getFirst(game, source));
        if (permanent != null) {
            RapidHybridizationToken token = new RapidHybridizationToken();
            token.putOntoBattlefield(1, game, source.getSourceId(), permanent.getControllerId());
        }
        return true;
    }

}
