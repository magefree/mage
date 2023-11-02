

package mage.cards.b;

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
 * @author BetaSteward_at_googlemail.com
 */
public final class BloodTithe extends CardImpl {

    public BloodTithe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        this.getSpellAbility().addEffect(new BloodTitheEffect());
    }

    private BloodTithe(final BloodTithe card) {
        super(card);
    }

    @Override
    public BloodTithe copy() {
        return new BloodTithe(this);
    }

}

class BloodTitheEffect extends OneShotEffect {

    public BloodTitheEffect() {
        super(Outcome.GainLife);
        staticText = "Each opponent loses 3 life. You gain life equal to the life lost this way";
    }

    private BloodTitheEffect(final BloodTitheEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lifeLost = 0;
        for (UUID opponentId: game.getOpponents(source.getControllerId())) {
            lifeLost += game.getPlayer(opponentId).loseLife(3, game, source, false);
        }
        game.getPlayer(source.getControllerId()).gainLife(lifeLost, game, source);
        return true;
    }

    @Override
    public BloodTitheEffect copy() {
        return new BloodTitheEffect(this);
    }

}