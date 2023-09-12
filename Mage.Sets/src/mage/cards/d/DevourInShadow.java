
package mage.cards.d;

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
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class DevourInShadow extends CardImpl {

    public DevourInShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{B}");


        // Destroy target creature. It can't be regenerated. You lose life equal to that creature's toughness.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DevourInShadowEffect());
    }

    private DevourInShadow(final DevourInShadow card) {
        super(card);
    }

    @Override
    public DevourInShadow copy() {
        return new DevourInShadow(this);
    }
    
    
}

class DevourInShadowEffect extends OneShotEffect {

    public DevourInShadowEffect() {
        super(Outcome.Damage);
        staticText = "You lose life equal to that creature's toughness";
    }

    private DevourInShadowEffect(final DevourInShadowEffect effect) {
        super(effect);
    }

    @Override
    public DevourInShadowEffect copy() {
        return new DevourInShadowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent target = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (player != null && target != null) {
            player.loseLife(target.getToughness().getValue(), game, source, false);
            return true;
        }
        return false;
    }

}