
package mage.cards.c;

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
 * @author LevelX2
 */
public final class Chronostutter extends CardImpl {

    public Chronostutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{U}");

        // Put target creature into its owner's library second from the top.
        this.getSpellAbility().addEffect(new ChronostutterEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private Chronostutter(final Chronostutter card) {
        super(card);
    }

    @Override
    public Chronostutter copy() {
        return new Chronostutter(this);
    }
}

class ChronostutterEffect extends OneShotEffect {

    public ChronostutterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target creature into its owner's library second from the top";
    }

    public ChronostutterEffect(final ChronostutterEffect effect) {
        super(effect);
    }

    @Override
    public ChronostutterEffect copy() {
        return new ChronostutterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                controller.putCardOnTopXOfLibrary(permanent, game, source, 2, true);
            }
            return true;
        }
        return false;
    }
}
