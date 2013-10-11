/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.abilities.effects.common.combat;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;


/**
 * http://tappedout.net/mtg-questions/must-be-blocked-if-able-effect-makes-other-attacking-creatures-essentially-unblockable/
 *
 * When you Declare Blockers, you choose an arrangement for your blockers,
 * then check to see if there are any restrictions or requirements.
 *
 * If any restrictions are violated, the block is illegal. (For example,
 * trying to block with Sightless Ghoul)
 * If any requirements are violated, the least possible number of requirements
 * must be violated, otherwise the block is illegal. (For example, your opponent
 * control two creatures that he has cast Deadly Allure on, but you control only
 * one creature. Blocking either one will violate a requirement, "This creature
 * must be blocked this turn if able", but it will also violate the least
 * possible number of requirements, thus it is legal.)
 * If the block is illegal, the game state backs up and you declare blockers
 * again. (Note that while you can, in some cases, circumvent requirements
 * such as "This creature must be blocked" or "This creature must block
 * any attacking creature" you can never circumvent restrictions: "This creature
 * can't block" or "Only one creature may block this turn.")
 * Because you declare ALL your blockers at once, THEN check for
 * restrictions/requirements, you may block Deadly Allure'd creatures
 * with only one creature, if you choose.
 * This still works with Lure: This card sets up a requirement that ALL
 * creatures must block it if able. Any block that violates more than
 * the minimum number of requirements is still illegal.
 *
 * @author LevelX2
 */
public class MustBeBlockedByAtLeastOneSourceEffect extends RequirementEffect<MustBeBlockedByAtLeastOneSourceEffect> {

    public MustBeBlockedByAtLeastOneSourceEffect() {
        this(Duration.EndOfTurn);
    }

    public MustBeBlockedByAtLeastOneSourceEffect(Duration duration) {
        super(duration);
        staticText = "{this} must be blocked this turn if able";
    }

    public MustBeBlockedByAtLeastOneSourceEffect(final MustBeBlockedByAtLeastOneSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.canBlock(source.getSourceId(), game);
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
        return source.getSourceId();
    }

    @Override
    public MustBeBlockedByAtLeastOneSourceEffect copy() {
        return new MustBeBlockedByAtLeastOneSourceEffect(this);
    }

}
