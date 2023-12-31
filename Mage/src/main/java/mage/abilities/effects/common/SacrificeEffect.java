package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public class SacrificeEffect extends OneShotEffect {

    private final FilterPermanent filter;
    private final String preText;
    private DynamicValue count;

    /**
     * Target player sacrifices N permanents matching the filter
     */
    public SacrificeEffect(FilterPermanent filter, int count, String preText) {
        this(filter, StaticValue.get(count), preText);
    }

    /**
     * Target player sacrifices X permanents matching the filter
     */
    public SacrificeEffect(FilterPermanent filter, DynamicValue count, String preText) {
        super(Outcome.Sacrifice);
        this.filter = filter;
        this.count = count;
        this.preText = preText;
        setText();
    }

    protected SacrificeEffect(final SacrificeEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.count = effect.count;
        this.preText = effect.preText;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        for (UUID playerId : targetPointer.getTargets(game, source)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int amount = Math.min(
                    count.calculate(game, source, this),
                    game.getBattlefield().count(TargetSacrifice.makeFilter(filter), player.getId(), source, game)
            );
            if (amount < 1) {
                continue;
            }
            TargetSacrifice target = new TargetSacrifice(amount, filter);
            while (!target.isChosen() && target.canChoose(player.getId(), source, game) && player.canRespond()) {
                player.choose(Outcome.Sacrifice, target, source, game);
            }
            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null && permanent.sacrifice(source, game)) {
                    applied = true;
                }
            }
        }
        return applied;
    }

    public void setAmount(DynamicValue amount) {
        this.count = amount;
    }

    @Override
    public SacrificeEffect copy() {
        return new SacrificeEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        if (preText != null) {
            sb.append(preText);
        }
        if (preText != null && (preText.endsWith("player") || preText.endsWith("opponent") || preText.endsWith("controller"))) {
            sb.append(" sacrifices ");
        } else {
            if (preText == null || preText.isEmpty()) {
                sb.append("sacrifice ");
            } else {
                sb.append(" sacrifice ");
            }
        }
        if (count.toString().equals("1")) {
            sb.append(CardUtil.addArticle(filter.getMessage()));
        } else {
            sb.append(CardUtil.numberToText(count.toString(), "a"));
            sb.append(" ");
            sb.append(filter.getMessage());
        }
        staticText = sb.toString();
    }
}
