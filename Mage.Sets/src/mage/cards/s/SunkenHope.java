package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
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

    SunkenHopeReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        staticText = "that player returns a creature they control to its owner's hand";
    }

    private SunkenHopeReturnToHandEffect(final SunkenHopeReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public SunkenHopeReturnToHandEffect copy() {
        return new SunkenHopeReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;

        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }

        Target target = new TargetControlledCreaturePermanent().withNotTarget(true);
        if (target.canChoose(player.getId(), source, game)) {
            while (player.canRespond() && !target.isChosen(game)
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
