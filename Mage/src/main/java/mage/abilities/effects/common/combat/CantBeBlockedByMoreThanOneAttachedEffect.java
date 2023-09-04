
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.EvasionEffect;
import mage.constants.*;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Quercitron
 */
public class CantBeBlockedByMoreThanOneAttachedEffect extends EvasionEffect {

    protected int amount;
    protected AttachmentType attachmentType;

    public CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType attachmentType, int amount) {
        this(attachmentType, amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType attachmentType, int amount, Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.amount = amount;
        this.attachmentType = attachmentType;
        this.staticCantBeBlockedMessage = "can't be blocked by more than "
                + (CardUtil.numberToText(amount)) + " creature" + (amount > 1 ? "s" : "")
                + (duration == Duration.EndOfTurn ? " each combat this turn" : "");
        staticText = attachmentType.verb() + " creature "
                + this.staticCantBeBlockedMessage;
    }

    protected CantBeBlockedByMoreThanOneAttachedEffect(final CantBeBlockedByMoreThanOneAttachedEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public CantBeBlockedByMoreThanOneAttachedEffect copy() {
        return new CantBeBlockedByMoreThanOneAttachedEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null && attachment.getAttachedTo() != null) {
            if (permanent != null && permanent.getId().equals(attachment.getAttachedTo())) {
                return true;
            }
        }
        return false;
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
                if (count > amount) {
                    return true;
                }
            }
        }
        return false;
    }
}