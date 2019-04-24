

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

    public BloodTithe(final BloodTithe card) {
        super(card);
    }

    @Override
    public BloodTithe copy() {
        return new BloodTithe(this);
    }

}

class BloodTitheEffect extends OneShotEffect {

    public BloodTitheEffect() {
        super(Outcome.Damage);
        staticText = "Each opponent loses 3 life. You gain life equal to the life lost this way";
    }

    public BloodTitheEffect(final BloodTitheEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 0;
        for (UUID opponentId: game.getOpponents(source.getControllerId())) {
            damage += game.getPlayer(opponentId).damage(3, source.getSourceId(), game, false, true);
        }
        game.getPlayer(source.getControllerId()).gainLife(damage, game, source);
        return true;
    }

    @Override
    public BloodTitheEffect copy() {
        return new BloodTitheEffect(this);
    }

}