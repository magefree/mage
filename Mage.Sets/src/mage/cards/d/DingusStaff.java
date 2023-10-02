package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class DingusStaff extends CardImpl {

    public DingusStaff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever a creature dies, Dingus Staff deals 2 damage to that creature's controller.
        addAbility(new DiesCreatureTriggeredAbility(new DingusStaffEffect(), false, false, true));
    }

    private DingusStaff(final DingusStaff card) {
        super(card);
    }

    @Override
    public DingusStaff copy() {
        return new DingusStaff(this);
    }
}

class DingusStaffEffect extends OneShotEffect {

    public DingusStaffEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 2 damage to that creature's controller";
    }

    private DingusStaffEffect(final DingusStaffEffect effect) {
        super(effect);
    }

    @Override
    public DingusStaffEffect copy() {
        return new DingusStaffEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        if (permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller != null) {
                controller.damage(2, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }
}
