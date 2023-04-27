
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author Plopman
 */
public final class Chastise extends CardImpl {

    public Chastise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");


        // Destroy target attacking creature. You gain life equal to its power.
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new ChastiseEffect());
    }

    private Chastise(final Chastise card) {
        super(card);
    }

    @Override
    public Chastise copy() {
        return new Chastise(this);
    }
}

class ChastiseEffect extends OneShotEffect {

    public ChastiseEffect() {
        super(Outcome.GainLife);
        this.staticText = "You gain life equal to its power";
    }

    public ChastiseEffect(final ChastiseEffect effect) {
        super(effect);
    }

    @Override
    public ChastiseEffect copy() {
        return new ChastiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            int power = permanent.getPower().getValue();
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(power, game, source);
            }
            return true;
        }
        return false;
    }
}
