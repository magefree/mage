
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class DeathsCaress extends CardImpl {

    public DeathsCaress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");


        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // If that creature was a Human, you gain life equal to its toughness.
        this.getSpellAbility().addEffect(new DeathsCaressEffect());
    }

    private DeathsCaress(final DeathsCaress card) {
        super(card);
    }

    @Override
    public DeathsCaress copy() {
        return new DeathsCaress(this);
    }
}

class DeathsCaressEffect extends OneShotEffect {

    public DeathsCaressEffect() {
        super(Outcome.GainLife);
        this.staticText = "If that creature was a Human, you gain life equal to its toughness";
    }

    private DeathsCaressEffect(final DeathsCaressEffect effect) {
        super(effect);
    }

    @Override
    public DeathsCaressEffect copy() {
        return new DeathsCaressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent creature = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        if (player != null && creature != null && creature.hasSubtype(SubType.HUMAN, game)) {
            player.gainLife(creature.getToughness().getValue(), game, source);
            return true;
        }
        return false;
    }
}
