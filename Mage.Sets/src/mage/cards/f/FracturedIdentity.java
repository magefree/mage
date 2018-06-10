
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class FracturedIdentity extends CardImpl {

    public FracturedIdentity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{U}");

        // Exile target nonland permanent. Each player other than its controller creates a token that's a copy of it.
        this.getSpellAbility().addEffect(new FracturedIdentityEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    public FracturedIdentity(final FracturedIdentity card) {
        super(card);
    }

    @Override
    public FracturedIdentity copy() {
        return new FracturedIdentity(this);
    }
}

class FracturedIdentityEffect extends OneShotEffect {

    public FracturedIdentityEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target nonland permanent. Each player other than its controller creates a token that's a copy of it";
    }

    public FracturedIdentityEffect(final FracturedIdentityEffect effect) {
        super(effect);
    }

    @Override
    public FracturedIdentityEffect copy() {
        return new FracturedIdentityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }

        permanent.moveToExile(null, null, source.getSourceId(), game);
        UUID controllerId = permanent.getControllerId();
        for (UUID opponentId : game.getOpponents(controllerId)) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(opponentId, null, false);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            effect.apply(game, source);
        }
        return true;
    }
}
