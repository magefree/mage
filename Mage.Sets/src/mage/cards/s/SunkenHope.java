package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class SunkenHope extends CardImpl {

    public SunkenHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // At the beginning of each player's upkeep, that player returns a creature they control to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SunkenHopeReturnToHandEffect(), TargetController.ANY, false, true));
    }

    private SunkenHope(final SunkenHope card) {
        super(card);
    }

    @Override
    public SunkenHope copy() {
        return new SunkenHope(this);
    }
}

class SunkenHopeReturnToHandEffect extends OneShotEffect {

    public SunkenHopeReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        staticText = "that player returns a creature they control to its owner's hand";
    }

    public SunkenHopeReturnToHandEffect(final SunkenHopeReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public SunkenHopeReturnToHandEffect copy() {
        return new SunkenHopeReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;

        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }

        Target target = new TargetControlledPermanent(1, 1, new FilterControlledCreaturePermanent(), true);
        if (target.canChoose(player.getId(), source, game)) {
            while (player.canRespond() && !target.isChosen()
                    && target.canChoose(player.getId(), source, game)) {
                player.chooseTarget(Outcome.ReturnToHand, target, source, game);
            }

            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    result |= player.moveCards(permanent, Zone.HAND, source, game);
                }
            }
        }
        return result;
    }
}
