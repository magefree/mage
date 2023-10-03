package mage.cards.e;

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
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Loki
 */
public final class EchoingCalm extends CardImpl {

    public EchoingCalm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");


        // Destroy target enchantment and all other enchantments with the same name as that enchantment.
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addEffect(new EchoingCalmEffect());
    }

    private EchoingCalm(final EchoingCalm card) {
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

    private EchoingCalmEffect(final EchoingCalmEffect effect) {
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
            permanent.destroy(source, game, false);
            if (!CardUtil.haveEmptyName(permanent)) { // in case of face down enchantment creature
                for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                    if (!perm.getId().equals(permanent.getId()) && CardUtil.haveSameNames(perm, permanent) && perm.isEnchantment(game)) {
                        perm.destroy(source, game, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
