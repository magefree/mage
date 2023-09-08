
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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
 * @author LevelX2
 */
public final class KikusShadow extends CardImpl {

    public KikusShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}");


        // Target creature deals damage to itself equal to its power.
        this.getSpellAbility().addEffect(new KikusShadowEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private KikusShadow(final KikusShadow card) {
        super(card);
    }

    @Override
    public KikusShadow copy() {
        return new KikusShadow(this);
    }
}

class KikusShadowEffect extends OneShotEffect {

    public KikusShadowEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature deals damage to itself equal to its power";
    }

    private KikusShadowEffect(final KikusShadowEffect effect) {
        super(effect);
    }

    @Override
    public KikusShadowEffect copy() {
        return new KikusShadowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                targetCreature.damage(targetCreature.getPower().getValue(), source.getSourceId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }
}
