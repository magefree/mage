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
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author LevelX2
 */
public class CantAttackBlockUnlessPaysSourceEffect extends PayCostToAttackBlockEffectImpl {

    public CantAttackBlockUnlessPaysSourceEffect(Cost cost, RestrictType restrictType) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, restrictType, cost);
        staticText = "{this} can't " + restrictType.toString() + " unless you "
                + cost == null ? "" : cost.getText()
                        + (restrictType.equals(RestrictType.ATTACK) ? " <i>(This cost is paid as attackers are declared.)</i>" : "");
    }

    public CantAttackBlockUnlessPaysSourceEffect(ManaCosts manaCosts, RestrictType restrictType) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK_AND_BLOCK, manaCosts);
        staticText = "{this} can't " + restrictType.toString() + " unless you pay "
                + manaCosts == null ? "" : manaCosts.getText();
    }

    public CantAttackBlockUnlessPaysSourceEffect(CantAttackBlockUnlessPaysSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!restrictType.equals(RestrictType.BLOCK) && event.getType().equals(EventType.DECLARE_ATTACKER)) {
            return event.getSourceId().equals(source.getSourceId());
        }
        if (!restrictType.equals(RestrictType.ATTACK) && event.getType().equals(EventType.DECLARE_BLOCKER)) {
            return event.getSourceId().equals(source.getSourceId());
        }
        return false;
    }

    @Override
    public CantAttackBlockUnlessPaysSourceEffect copy() {
        return new CantAttackBlockUnlessPaysSourceEffect(this);
    }
}
