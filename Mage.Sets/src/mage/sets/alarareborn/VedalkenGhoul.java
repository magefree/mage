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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class VedalkenGhoul extends CardImpl<VedalkenGhoul> {

    public VedalkenGhoul(UUID ownerId) {
        super(ownerId, 32, "Vedalken Ghoul", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{U}{B}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Vedalken");
        this.subtype.add("Zombie");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Vedalken Ghoul becomes blocked, defending player loses 4 life.
        this.addAbility(new VedalkenGhoulTriggeredAbility());

    }

    public VedalkenGhoul(final VedalkenGhoul card) {
        super(card);
    }

    @Override
    public VedalkenGhoul copy() {
        return new VedalkenGhoul(this);
    }
}

class VedalkenGhoulTriggeredAbility extends TriggeredAbilityImpl<VedalkenGhoulTriggeredAbility> {

    public VedalkenGhoulTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(4), false);
    }

    public VedalkenGhoulTriggeredAbility(final VedalkenGhoulTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CREATURE_BLOCKED && event.getTargetId().equals(this.getSourceId())) {
            UUID defendingPlayer = game.getCombat().getDefenderId(this.getSourceId());
            if (defendingPlayer != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(defendingPlayer));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes blocked, defending player loses 4 life.";
    }

    @Override
    public VedalkenGhoulTriggeredAbility copy() {
        return new VedalkenGhoulTriggeredAbility(this);
    }
}
