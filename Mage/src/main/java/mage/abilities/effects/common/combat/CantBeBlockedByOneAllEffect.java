
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.EvasionEffect;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class CantBeBlockedByOneAllEffect extends EvasionEffect {

    private FilterCreaturePermanent filter;
    protected int amount;

    public CantBeBlockedByOneAllEffect(int amount, FilterCreaturePermanent filter) {
        this(amount, filter, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByOneAllEffect(int amount, FilterCreaturePermanent filter, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
        this.filter = filter;
        this.staticCantBeBlockedMessage = "can't be blocked"
                + (duration == Duration.EndOfTurn ? " this turn" : "")
                + (" except by ")
                + (CardUtil.numberToText(amount))
                + (" or more creatures");
        staticText = "Each " + (filter.getMessage()) + " "
                + this.staticCantBeBlockedMessage;
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
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent != null && filter.match(permanent, source.getControllerId(), source, game);
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
