
package mage.abilities.effects.common.combat;

import mage.MageObject;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;


import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Quercitron
 */
public class CantBeBlockedByMoreThanOneSourceEffect extends ContinuousEffectImpl {

    protected int amount;

    public CantBeBlockedByMoreThanOneSourceEffect() {
        this(1);
    }

    public CantBeBlockedByMoreThanOneSourceEffect(int amount) {
        this(amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByMoreThanOneSourceEffect(int amount, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
        staticText = "{this} can't be blocked by more than " + CardUtil.numberToText(amount) + " creature" + (amount > 1 ? "s" : "")
                + (duration == Duration.EndOfTurn ? " each combat this turn" : "");
    }

    protected CantBeBlockedByMoreThanOneSourceEffect(final CantBeBlockedByMoreThanOneSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CantBeBlockedByMoreThanOneSourceEffect copy() {
        return new CantBeBlockedByMoreThanOneSourceEffect(this);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        for (MageObject object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            permanent.setMaxBlockedBy(amount);
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null ? Collections.singletonList(permanent) : Collections.emptyList();
    }
}
