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
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class JundSojourners extends CardImpl<JundSojourners> {

    public JundSojourners(UUID ownerId) {
        super(ownerId, 116, "Jund Sojourners", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}{R}{G}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Viashino");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When you cycle Jund Sojourners or it dies, you may have it deal 1 damage to target creature or player.
        Ability ability1 = new CycleTriggeredAbility(new DamageTargetEffect(1));
        Ability ability2 = new DiesTriggeredAbility(new DamageTargetEffect(1));
        ability1.addTarget(new TargetCreatureOrPlayer());
        ability2.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability1);
        this.addAbility(ability2);
        
        // Cycling {2}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}{R}")));
    }

    public JundSojourners(final JundSojourners card) {
        super(card);
    }

    @Override
    public JundSojourners copy() {
        return new JundSojourners(this);
    }
}
