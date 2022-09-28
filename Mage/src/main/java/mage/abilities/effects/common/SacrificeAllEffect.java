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
import mage.target.TargetPermanent;
import mage.target.common.TargetSacrifice;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
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

    protected SacrificeAllEffect(final SacrificeAllEffect effect) {
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
        int amount = this.amount.calculate(game, source, this);
        if (amount < 1) {
            return false;
        }
        Set<UUID> perms = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int numTargets = Math.min(amount, game.getBattlefield().countAll(filter, player.getId(), game));
            if (numTargets < 1) {
            }
            TargetPermanent target = new TargetSacrifice(numTargets, filter);
            if (target.canChoose(player.getId(), source, game)) {
                player.chooseTarget(Outcome.Sacrifice, target, source, game);
                perms.addAll(target.getTargets());
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
            sb.append(filter.getMessage());
        } else if (amount.toString().equals("1")) {
            sb.append(CardUtil.addArticle(filter.getMessage()));
        } else {
            sb.append(CardUtil.numberToText(amount.toString(), "a"));
            sb.append(' ');
            sb.append(filter.getMessage());
        }
        staticText = sb.toString();
    }

}
