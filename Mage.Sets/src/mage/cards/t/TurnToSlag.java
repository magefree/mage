

package mage.cards.t;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class TurnToSlag extends CardImpl {

    public TurnToSlag (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");

        this.getSpellAbility().addEffect(new TurnToSlagEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public TurnToSlag (final TurnToSlag card) {
        super(card);
    }

    @Override
    public TurnToSlag copy() {
        return new TurnToSlag(this);
    }
}

class TurnToSlagEffect extends OneShotEffect {

    public TurnToSlagEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "{this} deals 5 damage to target creature. Destroy all Equipment attached to that creature";
    }

    public TurnToSlagEffect(final TurnToSlagEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            List<Permanent> attachments = new ArrayList<>();
            for (UUID uuid : target.getAttachments()) {
                Permanent attached = game.getBattlefield().getPermanent(uuid);
                if (attached.hasSubtype(SubType.EQUIPMENT, game)) {
                    attachments.add(attached);
                }
            }
            for (Permanent p : attachments) {
                p.destroy(source, game, false);
            }
            target.damage(5, source.getSourceId(), source, game, false, false);
            return true;
        }
        return false;
    }

    @Override
    public TurnToSlagEffect copy() {
        return new TurnToSlagEffect(this);
    }

}