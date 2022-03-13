
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SacrificeAllEffect extends OneShotEffect {

    protected DynamicValue amount;
    protected FilterControlledPermanent filter;

    public SacrificeAllEffect(FilterControlledPermanent filter) {
        this(1, filter);
    }

    public SacrificeAllEffect(int amount, FilterControlledPermanent filter) {
        this(StaticValue.get(amount), filter);
    }

    public SacrificeAllEffect(DynamicValue amount, FilterControlledPermanent filter) {
        super(Outcome.Sacrifice);
        this.amount = amount;
        this.filter = filter;
        setText();
    }

    public SacrificeAllEffect(final SacrificeAllEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.filter = effect.filter.copy();
    }

    @Override
    public SacrificeAllEffect copy() {
        return new SacrificeAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        List<UUID> perms = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int numTargets = Math.min(amount.calculate(game, source, this), game.getBattlefield().countAll(filter, player.getId(), game));
                TargetControlledPermanent target = new TargetControlledPermanent(numTargets, numTargets, filter, true);
                if (target.canChoose(player.getId(), source, game)) {
                    while (!target.isChosen() && player.canRespond()) {
                        player.choose(Outcome.Sacrifice, target, source, game);
                    }
                    perms.addAll(target.getTargets());
                }
            }
        }
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("each player sacrifices ");
        if (amount.toString().equals("X")) {
            sb.append(amount.toString());
            sb.append(' ');
        } else if (!filter.getMessage().startsWith("a ")) {
            sb.append(CardUtil.numberToText(amount.toString(), "a"));
            sb.append(' ');
        }
        sb.append(filter.getMessage());
        staticText = sb.toString();
    }

}
