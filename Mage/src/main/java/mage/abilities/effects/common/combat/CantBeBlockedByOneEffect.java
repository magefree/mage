package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.EvasionEffect;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author North
 */
public class CantBeBlockedByOneEffect extends EvasionEffect {

    protected int amount;

    public CantBeBlockedByOneEffect(int amount) {
        this(amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByOneEffect(int amount, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
        this.staticCantBeBlockedMessage = "can't be blocked except by "
                + (CardUtil.numberToText(amount))
                + " or more creatures";
        staticText = "{this} " + this.staticCantBeBlockedMessage;
    }

    protected CantBeBlockedByOneEffect(final CantBeBlockedByOneEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CantBeBlockedByOneEffect copy() {
        return new CantBeBlockedByOneEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent != null && permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean cantBeBlockedCheckAfter(Permanent attacker, Ability source, Game game, boolean canUseChooseDialogs) {
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getAttackers().contains(attacker.getId())) {
                int count = 0;
                for (UUID blockerId : combatGroup.getBlockers()) {
                    Permanent blockingCreature = game.getPermanent(blockerId);
                    if (blockingCreature != null) {
                        count++;
                    }
                }
                if (count < amount) {
                    return true;
                }
            }
        }
        return false;
    }
}
