
package mage.cards.e;

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
import mage.target.common.TargetEnchantmentPermanent;

/**
 * @author Loki
 */
public final class EchoingCalm extends CardImpl {

    public EchoingCalm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Destroy target enchantment and all other enchantments with the same name as that enchantment.
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addEffect(new EchoingCalmEffect());
    }

    public EchoingCalm(final EchoingCalm card) {
        super(card);
    }

    @Override
    public EchoingCalm copy() {
        return new EchoingCalm(this);
    }
}

class EchoingCalmEffect extends OneShotEffect {
    EchoingCalmEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target enchantment and all other enchantments with the same name as that enchantment";
    }

    EchoingCalmEffect(final EchoingCalmEffect effect) {
        super(effect);
    }

    @Override
    public EchoingCalmEffect copy() {
        return new EchoingCalmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && permanent != null) {
            permanent.destroy(source.getSourceId(), game, false);
            if (!permanent.getName().isEmpty()) { // in case of face down enchantment creature
                for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                    if (!perm.getId().equals(permanent.getId()) && perm.getName().equals(permanent.getName()) && perm.isEnchantment()) {
                        perm.destroy(source.getSourceId(), game, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
