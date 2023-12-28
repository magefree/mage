package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.CanBeSacrificedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * All opponents have to sacrifice [amount] permanents that match the [filter].
 *
 * @author LevelX2
 */
public class SacrificeOpponentsEffect extends OneShotEffect {

    private final DynamicValue amount;
    private final FilterPermanent filter;

    public SacrificeOpponentsEffect(FilterPermanent filter) {
        this(1, filter);
    }

    public SacrificeOpponentsEffect(int amount, FilterPermanent filter) {
        this(StaticValue.get(amount), filter);
    }

    public SacrificeOpponentsEffect(DynamicValue amount, FilterPermanent filter) {
        super(Outcome.Sacrifice);
        this.amount = amount;
        this.filter = filter.copy();
        setText();
    }

    protected SacrificeOpponentsEffect(final SacrificeOpponentsEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.filter = effect.filter.copy();
    }

    @Override
    public SacrificeOpponentsEffect copy() {
        return new SacrificeOpponentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int num = amount.calculate(game, source, this);
        if (num < 1) {
            return false;
        }
        Set<UUID> perms = new HashSet<>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            FilterPermanent checkFilter = filter.copy();
            checkFilter.add(CanBeSacrificedPredicate.instance);
            int numTargets = Math.min(num, game.getBattlefield().countAll(checkFilter, player.getId(), game));
            if (numTargets < 1) {
                continue;
            }
            TargetSacrifice target = new TargetSacrifice(numTargets, filter);
            while (!target.isChosen() && target.canChoose(player.getId(), source, game) && player.canRespond()) {
                player.choose(Outcome.Sacrifice, target, source, game);
            }
            perms.addAll(target.getTargets());
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
        sb.append("each opponent sacrifices ");
        switch (amount.toString()) {
            case "X":
                sb.append(amount.toString());
                sb.append(' ');
                sb.append(filter.getMessage());
                break;
            case "1":
                sb.append(CardUtil.addArticle(filter.getMessage()));
                break;
            default:
                sb.append(CardUtil.numberToText(amount.toString(), "a"));
                sb.append(' ');
                sb.append(filter.getMessage());
        }
        staticText = sb.toString();
    }
}
