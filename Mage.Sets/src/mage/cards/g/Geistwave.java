package mage.cards.g;

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
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Geistwave extends CardImpl {

    public Geistwave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent to its owner's hand. If you controlled that permanent, draw a card.
        this.getSpellAbility().addEffect(new GeistwaveEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private Geistwave(final Geistwave card) {
        super(card);
    }

    @Override
    public Geistwave copy() {
        return new Geistwave(this);
    }
}

class GeistwaveEffect extends OneShotEffect {

    GeistwaveEffect() {
        super(Outcome.Benefit);
        staticText = "return target nonland permanent to its owner's hand. " +
                "If you controlled that permanent, draw a card";
    }

    private GeistwaveEffect(final GeistwaveEffect effect) {
        super(effect);
    }

    @Override
    public GeistwaveEffect copy() {
        return new GeistwaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        boolean flag = permanent.isControlledBy(source.getControllerId());
        player.moveCards(permanent, Zone.HAND, source, game);
        if (flag) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
