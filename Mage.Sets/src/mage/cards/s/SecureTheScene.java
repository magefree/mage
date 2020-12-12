package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SecureTheScene extends CardImpl {

    public SecureTheScene(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Exile target nonland permanent. Its controller creates a 1/1 white Soldier creature token.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new SecureTheSceneEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private SecureTheScene(final SecureTheScene card) {
        super(card);
    }

    @Override
    public SecureTheScene copy() {
        return new SecureTheScene(this);
    }
}

class SecureTheSceneEffect extends OneShotEffect {

    private static final Token token = new SoldierToken();

    SecureTheSceneEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Its controller creates a 1/1 white Soldier creature token";
    }

    private SecureTheSceneEffect(final SecureTheSceneEffect effect) {
        super(effect);
    }

    @Override
    public SecureTheSceneEffect copy() {
        return new SecureTheSceneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent == null) {
            return false;
        }
        return token.putOntoBattlefield(1, game, source, permanent.getControllerId());
    }
}
