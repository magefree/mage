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
package mage.sets.ravnika;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Loki
 */
public class SearingMeditation extends CardImpl<SearingMeditation> {

    public SearingMeditation(UUID ownerId) {
        super(ownerId, 226, "Searing Meditation", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{W}");
        this.expansionSetCode = "RAV";

        this.color.setRed(true);
        this.color.setWhite(true);

        // Whenever you gain life, you may pay {2}. If you do, Searing Meditation deals 2 damage to target creature or player.
        Ability ability = new SimpleTriggeredAbility(Constants.Zone.BATTLEFIELD, GameEvent.EventType.GAINED_LIFE, new DoIfCostPaid(new DamageTargetEffect(2), new GenericManaCost(2)), "Whenever you gain life, ", true);
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public SearingMeditation(final SearingMeditation card) {
        super(card);
    }

    @Override
    public SearingMeditation copy() {
        return new SearingMeditation(this);
    }
}
