
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * <p>
 * <p>
 * http://tappedout.net/mtg-questions/must-be-blocked-if-able-effect-makes-other-attacking-creatures-essentially-unblockable/
 * <p>
 * When you Declare Blockers, you choose an arrangement for your blockers, then
 * check to see if there are any restrictions or requirements.
 * <p>
 * If any restrictions are violated, the block is illegal. (For example, trying
 * to block with Sightless Ghoul) If any requirements are violated, the least
 * possible number of requirements must be violated, otherwise the block is
 * illegal. (For example, your opponent control two creatures that he has cast
 * Deadly Allure on, but you control only one creature. Blocking either one will
 * violate a requirement, "This creature must be blocked this turn if able", but
 * it will also violate the least possible number of requirements, thus it is
 * legal.) If the block is illegal, the game state backs up and you declare
 * blockers again. (Note that while you can, in some cases, circumvent
 * requirements such as "This creature must be blocked" or "This creature must
 * block any attacking creature" you can never circumvent restrictions: "This
 * creature can't block" or "Only one creature may block this turn.") Because
 * you declare ALL your blockers at once, THEN check for
 * restrictions/requirements, you may block Deadly Allure'd creatures with only
 * one creature, if you choose. This still works with Lure: This card sets up a
 * requirement that ALL creatures must block it if able. Any block that violates
 * more than the minimum number of requirements is still illegal.
 *
 * @author notgreat
 */
public class MustBeBlockedByAtLeastOneAttachedEffect extends RequirementEffect {

    private final FilterPermanent filter;

    public MustBeBlockedByAtLeastOneAttachedEffect() {
        this((FilterPermanent)null);
    }
    public MustBeBlockedByAtLeastOneAttachedEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield);
        this.filter = filter;
        if (filter == null) {
            staticText = "must be blocked if able";
        } else {
            staticText = "must be blocked by "+(filter.getMessage())+" if able";
        }
    }

    protected MustBeBlockedByAtLeastOneAttachedEffect(final MustBeBlockedByAtLeastOneAttachedEffect effect) {
        super(effect);
        filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment == null || attachment.getAttachedTo() == null) {
            return false;
        }
        Permanent attachedCreature = game.getPermanent(attachment.getAttachedTo());
        if (attachedCreature != null && attachedCreature.isAttacking()
                && permanent.canBlock(attachment.getAttachedTo(), game)){
            return (filter == null || filter.match(permanent, attachment.getControllerId(), source, game));
        }
        return false;
    }
    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public UUID mustBlockAttackerIfElseUnblocked(Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        return attachment == null ? null : attachment.getAttachedTo();
    }

    @Override
    public int getMinNumberOfBlockers() {
        return filter == null ? 1 : 0;
    }

    @Override
    public MustBeBlockedByAtLeastOneAttachedEffect copy() {
        return new MustBeBlockedByAtLeastOneAttachedEffect(this);
    }
}
