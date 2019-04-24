
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class SacrificeEffect extends OneShotEffect {

    private FilterPermanent filter;
    private String preText;
    private DynamicValue count;

    public SacrificeEffect(FilterPermanent filter, int count, String preText) {
        this(filter, new StaticValue(count), preText);
    }

    public SacrificeEffect(FilterPermanent filter, DynamicValue count, String preText) {
        super(Outcome.Sacrifice);
        this.filter = filter;
        this.count = count;
        this.preText = preText;
        setText();
    }

    public SacrificeEffect(final SacrificeEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.count = effect.count;
        this.preText = effect.preText;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }

        FilterPermanent newFilter = filter.copy(); // filter can be static, so it's important to copy here
        newFilter.add(new ControllerIdPredicate(player.getId()));

        int amount = count.calculate(game, source, this);
        int realCount = game.getBattlefield().countAll(newFilter, player.getId(), game);
        amount = Math.min(amount, realCount);

        Target target = new TargetPermanent(amount, amount, newFilter, true);

        if (amount > 0 && target.canChoose(source.getSourceId(), player.getId(), game)) {
            while (!target.isChosen() && target.canChoose(player.getId(), game) && player.canRespond()) {
                player.chooseTarget(Outcome.Sacrifice, target, source, game);
            }

            for (int idx = 0; idx < target.getTargets().size(); idx++) {
                Permanent permanent = game.getPermanent(target.getTargets().get(idx));

                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }
        }
        return true;
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
        if (preText != null && (preText.endsWith("player") || preText.endsWith("opponent"))) {
            sb.append(" sacrifices ");
        } else {
            if (preText == null || preText.isEmpty()) {
                sb.append("sacrifice ");
            } else {
                sb.append(" sacrifice ");
            }
        }
        sb.append(CardUtil.numberToText(count.toString(), "a")).append(' ');
        sb.append(filter.getMessage());
        staticText = sb.toString();
    }
}
