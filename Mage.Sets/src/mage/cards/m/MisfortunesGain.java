
package mage.cards.m;

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
 * @author Scott-Crawford
 */
public final class MisfortunesGain extends CardImpl {

    public MisfortunesGain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Destroy target creature. Its owner gains 4 life.
        this.getSpellAbility().addEffect(new MisfortunesGainEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MisfortunesGain(final MisfortunesGain card) {
        super(card);
    }

    @Override
    public MisfortunesGain copy() {
        return new MisfortunesGain(this);
    }
}

class MisfortunesGainEffect extends OneShotEffect {

    public MisfortunesGainEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature. Its owner gains 4 life";
    }

    private MisfortunesGainEffect(final MisfortunesGainEffect effect) {
        super(effect);
    }

    @Override
    public MisfortunesGainEffect copy() {
        return new MisfortunesGainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && target != null) {
            target.destroy(source, game, false);
            Player owner = game.getPlayer(target.getOwnerId());
            if (owner != null) {
                owner.gainLife(4, game, source);
            }
            return true;
        }
        return false;
    }
}
