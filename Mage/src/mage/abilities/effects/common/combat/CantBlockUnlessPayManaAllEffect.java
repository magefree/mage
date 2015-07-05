/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl.RestrictType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class CantBlockUnlessPayManaAllEffect extends PayCostToAttackBlockEffectImpl {

    private final FilterCreaturePermanent filterCreaturePermanent;

    public CantBlockUnlessPayManaAllEffect(ManaCosts manaCosts) {
        this(manaCosts, false);
    }

    public CantBlockUnlessPayManaAllEffect(ManaCosts manaCosts, boolean payAlsoForAttackingPlaneswalker) {
        this(manaCosts, payAlsoForAttackingPlaneswalker, null);
    }

    public CantBlockUnlessPayManaAllEffect(ManaCosts manaCosts, boolean payAlsoForAttackingPlaneswalker, FilterCreaturePermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.BLOCK, manaCosts);
        this.filterCreaturePermanent = filter;
        staticText = (filterCreaturePermanent == null ? "Creatures" : filterCreaturePermanent.getMessage())
                + " can't block "
                + "unless their controller pays "
                + (manaCosts == null ? "" : manaCosts.getText())
                + " for each blocking creature he or she controls";
    }

    public CantBlockUnlessPayManaAllEffect(CantBlockUnlessPayManaAllEffect effect) {
        super(effect);
        this.filterCreaturePermanent = effect.filterCreaturePermanent;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // check if blocking creature fullfills filter criteria
        if (filterCreaturePermanent != null) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (!filterCreaturePermanent.match(permanent, source.getSourceId(), source.getControllerId(), game)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public CantBlockUnlessPayManaAllEffect copy() {
        return new CantBlockUnlessPayManaAllEffect(this);
    }
}
