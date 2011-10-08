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

import java.awt.*;
import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author Loki
 */
public class AgrusKosWojekVeteran extends CardImpl<AgrusKosWojekVeteran> {

    private final static FilterAttackingCreature filterRed = new FilterAttackingCreature("attacking red creatures");
    private final static FilterAttackingCreature filterWhite = new FilterAttackingCreature("attacking white creatures");

    static {
        filterRed.setUseColor(true);
        filterRed.setColor(ObjectColor.RED);
        filterWhite.setUseColor(true);
        filterWhite.setColor(ObjectColor.WHITE);

    }

    public AgrusKosWojekVeteran(UUID ownerId) {
        super(ownerId, 190, "Agrus Kos, Wojek Veteran", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        this.expansionSetCode = "RAV";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.color.setRed(true);
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        // Whenever Agrus Kos, Wojek Veteran attacks, attacking red creatures get +2/+0 and attacking white creatures get +0/+2 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(2, 0, Constants.Duration.EndOfTurn, filterRed, false), false));
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(0, 2, Constants.Duration.EndOfTurn, filterWhite, false), false));
    }

    public AgrusKosWojekVeteran(final AgrusKosWojekVeteran card) {
        super(card);
    }

    @Override
    public AgrusKosWojekVeteran copy() {
        return new AgrusKosWojekVeteran(this);
    }
}
