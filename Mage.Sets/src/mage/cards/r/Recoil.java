
package mage.cards.r;

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
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class Recoil extends CardImpl {

    public Recoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{B}");

        // Return target permanent to its owner's hand. Then that player discards a card.
        this.getSpellAbility().addEffect(new RecoilEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());

    }

    private Recoil(final Recoil card) {
        super(card);
    }

    @Override
    public Recoil copy() {
        return new Recoil(this);
    }
}

class RecoilEffect extends OneShotEffect {

    public RecoilEffect() {
        super(Outcome.Detriment);
        this.staticText = "return target permanent to its owner's hand. Then that player discards a card";
    }

    private RecoilEffect(final RecoilEffect effect) {
        super(effect);
    }

    @Override
    public RecoilEffect copy() {
        return new RecoilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }
        Player controller = game.getPlayer(target.getControllerId());
        if (controller != null) {
            controller.moveCards(target, Zone.HAND, source, game);
            controller.discard(1, false, false, source, game);
            return true;
        }
        return false;
    }
}
