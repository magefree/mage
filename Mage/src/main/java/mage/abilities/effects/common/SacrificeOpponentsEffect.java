package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * All opponents have to sacrifice [amount] permanents that match the [filter].
 *
 * @author LevelX2
 */
public class SacrificeOpponentsEffect extends OneShotEffect {

    protected DynamicValue amount;
    protected FilterPermanent filter;

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
        this.filter.add(TargetController.YOU.getControllerPredicate());
        setText();
    }

    public SacrificeOpponentsEffect(final SacrificeOpponentsEffect effect) {
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
        List<UUID> perms = new ArrayList<>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int numTargets = Math.min(amount.calculate(game, source, this), game.getBattlefield().countAll(filter, player.getId(), game));
                if (numTargets > 0) {
                    TargetPermanent target = new TargetPermanent(numTargets, numTargets, filter, true);
                    if (target.canChoose(player.getId(), source, game)) {
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
                    }
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
        sb.append("each opponent sacrifices ");
        switch (amount.toString()) {
            case "X":
                sb.append(amount.toString()).append(' ');
                break;
            case "1":
                sb.append(CardUtil.addArticle(filter.getMessage()));
                break;
            default:
                sb.append(CardUtil.numberToText(amount.toString())).append(' ').append(filter.getMessage());
        }
        staticText = sb.toString();
    }
}
