
package mage.abilities.effects.common.combat;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author emerald000
 */

public class CanBlockAdditionalCreatureAllEffect extends ContinuousEffectImpl {

    protected int amount;
    protected FilterPermanent filter;

    public CanBlockAdditionalCreatureAllEffect(int amount, FilterPermanent filter, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
        this.filter = filter;
        staticText = setText();
    }

    protected CanBlockAdditionalCreatureAllEffect(final CanBlockAdditionalCreatureAllEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.filter = effect.filter;
    }

    @Override
    public CanBlockAdditionalCreatureAllEffect copy() {
        return new CanBlockAdditionalCreatureAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder(filter.getMessage());
        sb.append(" can block ");
        switch (amount) {
            case 0:
                sb.append("any number of creatures");
                break;
            case 1:
                sb.append("an additional creature each combat");
                break;
            default:
                sb.append(CardUtil.numberToText(amount));
                sb.append(" additional creatures");
        }
        return sb.toString();
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            if (amount > 0) {
                if (permanent.getMaxBlocks() > 0) {
                    permanent.setMaxBlocks(permanent.getMaxBlocks() + amount);
                }
            } else {
                permanent.setMaxBlocks(0);
            }
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return game.getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
