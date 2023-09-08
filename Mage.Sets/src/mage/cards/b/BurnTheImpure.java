package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author ayratn
 */
public final class BurnTheImpure extends CardImpl {

    public BurnTheImpure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Burn the Impure deals 3 damage to target creature. If that creature has infect, Burn the Impure deals 3 damage to that creature’s controller.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BurnTheImpureEffect());
    }

    private BurnTheImpure(final BurnTheImpure card) {
        super(card);
    }

    @Override
    public BurnTheImpure copy() {
        return new BurnTheImpure(this);
    }

}

class BurnTheImpureEffect extends OneShotEffect {

    public BurnTheImpureEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 3 damage to target creature. If that creature has infect, {this} deals 3 damage to that creature's controller.";
    }

    private BurnTheImpureEffect(final BurnTheImpureEffect effect) {
        super(effect);
    }

    @Override
    public BurnTheImpureEffect copy() {
        return new BurnTheImpureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.damage(3, source.getSourceId(), source, game, false, true);
            if (permanent.getAbilities().contains(InfectAbility.getInstance())) {
                Player controller = game.getPlayer(permanent.getControllerId());
                if (controller != null) {
                    controller.damage(3, source.getSourceId(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
