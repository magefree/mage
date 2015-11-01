/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.stack.StackObject;

/**
 * @author BetaSteward_at_googlemail.com
 *
 */
public class ChooseNewTargetsTargetEffect extends OneShotEffect {

    private boolean forceChange;
    private boolean onlyOneTarget;
    private FilterPermanent filterNewTarget;

    public ChooseNewTargetsTargetEffect() {
        this(false, false);
    }

    public ChooseNewTargetsTargetEffect(boolean forceChange, boolean onlyOneTarget) {
        this(forceChange, onlyOneTarget, null);
    }

    /**
     *
     * @param forceChange forces the user to choose another target (only targets
     * with maxtargets = 1 supported)
     * @param onlyOneTarget only one target can be selected for the change
     * @param filterNewTarget restriction to the new target
     */
    public ChooseNewTargetsTargetEffect(boolean forceChange, boolean onlyOneTarget, FilterPermanent filterNewTarget) {
        super(Outcome.Benefit);
        this.forceChange = forceChange;
        this.onlyOneTarget = onlyOneTarget;
        this.filterNewTarget = filterNewTarget;
    }

    public ChooseNewTargetsTargetEffect(final ChooseNewTargetsTargetEffect effect) {
        super(effect);
        this.forceChange = effect.forceChange;
        this.onlyOneTarget = effect.onlyOneTarget;
        this.filterNewTarget = effect.filterNewTarget;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        if (stackObject != null) {
            return stackObject.chooseNewTargets(game, source.getControllerId(), forceChange, onlyOneTarget, filterNewTarget);
        }
        return false;
    }

    @Override
    public ChooseNewTargetsTargetEffect copy() {
        return new ChooseNewTargetsTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (forceChange) {
            sb.append("change the target of target ");
        } else {
            sb.append("you may choose new targets for target ");
        }
        sb.append(mode.getTargets().get(0).getTargetName());
        return sb.toString();
    }
}
