package mage.cards.o;

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
 * @author North
 */
public final class Oust extends CardImpl {

    public Oust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Put target creature into its owner's library second from the top. Its controller gains 3 life.
        this.getSpellAbility().addEffect(new OustEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Oust(final Oust card) {
        super(card);
    }

    @Override
    public Oust copy() {
        return new Oust(this);
    }
}

class OustEffect extends OneShotEffect {

    public OustEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target creature into its owner's library second from the top. Its controller gains 3 life";
    }

    private OustEffect(final OustEffect effect) {
        super(effect);
    }

    @Override
    public OustEffect copy() {
        return new OustEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            Player owner = game.getPlayer(permanent.getOwnerId());
            Player controller = game.getPlayer(permanent.getControllerId());
            if (owner == null || controller == null) {
                return false;
            }
            owner.putCardOnTopXOfLibrary(permanent, game, source, 2, true);
            controller.gainLife(3, game, source);
        }
        return true;
    }
}
