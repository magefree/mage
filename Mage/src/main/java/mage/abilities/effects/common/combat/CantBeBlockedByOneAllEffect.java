
package mage.abilities.effects.common.combat;

import mage.MageObject;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public class CantBeBlockedByOneAllEffect extends ContinuousEffectImpl {

    private FilterCreaturePermanent filter;
    protected int amount;

    public CantBeBlockedByOneAllEffect(int amount, FilterCreaturePermanent filter) {
        this(amount, filter, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByOneAllEffect(int amount, FilterCreaturePermanent filter, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
        this.filter = filter;
        StringBuilder sb = new StringBuilder("each ").append(filter.getMessage()).append(" can't be blocked ");
        if (duration == Duration.EndOfTurn) {
            sb.append("this turn ");
        }
        sb.append("except by ").append(CardUtil.numberToText(amount)).append(" or more creatures");
        staticText = sb.toString();
    }

    protected CantBeBlockedByOneAllEffect(final CantBeBlockedByOneAllEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.filter = effect.filter;
    }

    @Override
    public CantBeBlockedByOneAllEffect copy() {
        return new CantBeBlockedByOneAllEffect(this);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        for (MageObject object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            permanent.setMinBlockedBy(amount);
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return game.getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
