
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class DevourFlesh extends CardImpl {

    public DevourFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target player sacrifices a creature, then gains life equal to that creature's toughness.
        this.getSpellAbility().addEffect(new DevourFleshSacrificeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private DevourFlesh(final DevourFlesh card) {
        super(card);
    }

    @Override
    public DevourFlesh copy() {
        return new DevourFlesh(this);
    }
}

class DevourFleshSacrificeEffect extends OneShotEffect {

    public DevourFleshSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Target player sacrifices a creature, then gains life equal to that creature's toughness";
    }

    public DevourFleshSacrificeEffect(final DevourFleshSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public DevourFleshSacrificeEffect copy() {
        return new DevourFleshSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        int realCount = game.getBattlefield().countAll(filter, player.getId(), game);
        if (realCount > 0) {
            Target target = new TargetControlledPermanent(1, 1, filter, true);
            while (player.canRespond() && !target.isChosen() && target.canChoose(player.getId(), source, game)) {
                player.chooseTarget(Outcome.Sacrifice, target, source, game);
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                int gainLife = permanent.getToughness().getValue();
                permanent.sacrifice(source, game);
                game.getState().processAction(game);
                player.gainLife(gainLife, game, source);
            } else {
                return false;
            }
        }
        return true;
    }
}
