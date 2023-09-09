
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateTargetEffect;
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
 * @author Styxo
 */
public final class HealTheScars extends CardImpl {

    public HealTheScars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");

        // Regenerate target creature. You gain life equal to that creature's toughness. 
        this.getSpellAbility().addEffect(new RegenerateTargetEffect());
        this.getSpellAbility().addEffect(new HealTheScarsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private HealTheScars(final HealTheScars card) {
        super(card);
    }

    @Override
    public HealTheScars copy() {
        return new HealTheScars(this);
    }
}

class HealTheScarsEffect extends OneShotEffect {

    public HealTheScarsEffect() {
        super(Outcome.GainLife);
        staticText = "You gain life equal to that creature's toughness";
    }

    private HealTheScarsEffect(final HealTheScarsEffect effect) {
        super(effect);
    }

    @Override
    public HealTheScarsEffect copy() {
        return new HealTheScarsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(permanent.getToughness().getValue(), game, source);
            }
            return true;
        }
        return false;
    }
}
