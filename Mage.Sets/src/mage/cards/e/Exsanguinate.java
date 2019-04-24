

package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author Loki
 */
public final class Exsanguinate extends CardImpl {

    public Exsanguinate (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{B}{B}");

        this.getSpellAbility().addEffect(new ExsanguinateEffect());
    }

    public Exsanguinate (final Exsanguinate card) {
        super(card);
    }

    @Override
    public Exsanguinate copy() {
        return new Exsanguinate(this);
    }

}

class ExsanguinateEffect extends OneShotEffect {
    public ExsanguinateEffect() {
        super(Outcome.Damage);
        staticText = "Each opponent loses X life. You gain life equal to the life lost this way";
    }

    public ExsanguinateEffect(final ExsanguinateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int loseLife = 0;
        int damage = source.getManaCostsToPay().getX();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            loseLife += game.getPlayer(opponentId).loseLife(damage, game, false);
        }
        if (loseLife > 0)
            game.getPlayer(source.getControllerId()).gainLife(loseLife, game, source);
        return true;
    }

    @Override
    public ExsanguinateEffect copy() {
        return new ExsanguinateEffect(this);
    }

}