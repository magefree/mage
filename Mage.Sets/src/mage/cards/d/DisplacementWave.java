
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */

public final class DisplacementWave extends CardImpl {

    public DisplacementWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{U}{U}");

        // Return all nonland permanents with converted mana cost X or less to their owners' hands.
        this.getSpellAbility().addEffect(new DisplacementWaveEffect());
    }

    public DisplacementWave(final DisplacementWave card) {
        super(card);
    }

    @Override
    public DisplacementWave copy() {
        return new DisplacementWave(this);
    }
}

class DisplacementWaveEffect extends OneShotEffect {

    public DisplacementWaveEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return all nonland permanents with converted mana cost X or less to their owners' hands";
    }

    public DisplacementWaveEffect(final DisplacementWaveEffect effect) {
        super(effect);
    }

    @Override
    public DisplacementWaveEffect copy() {
        return new DisplacementWaveEffect(this);
    }    
    
    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (!permanent.isLand() && permanent.getConvertedManaCost() <= source.getManaCostsToPay().getX()) {
                permanent.moveToZone(Zone.HAND, source.getSourceId(), game, true);
            }
        }
        return true;
    }
}