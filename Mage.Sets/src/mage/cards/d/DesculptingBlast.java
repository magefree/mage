package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DroneToken2;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DesculptingBlast extends CardImpl {

    public DesculptingBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent to its owner's hand. If it was attacking, create a 1/1 colorless Drone artifact creature token with flying and "This token can block only creatures with flying."
        this.getSpellAbility().addEffect(new DesculptingBlastEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private DesculptingBlast(final DesculptingBlast card) {
        super(card);
    }

    @Override
    public DesculptingBlast copy() {
        return new DesculptingBlast(this);
    }
}

class DesculptingBlastEffect extends OneShotEffect {

    DesculptingBlastEffect() {
        super(Outcome.Benefit);
        staticText = "return target nonland permanent to its owner's hand. If it was attacking, " +
                "create a 1/1 colorless Drone artifact creature token with flying and " +
                "\"This token can block only creatures with flying.\"";
    }

    private DesculptingBlastEffect(final DesculptingBlastEffect effect) {
        super(effect);
    }

    @Override
    public DesculptingBlastEffect copy() {
        return new DesculptingBlastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        boolean flag = permanent.isAttacking();
        player.moveCards(permanent, Zone.HAND, source, game);
        if (flag) {
            new DroneToken2().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
