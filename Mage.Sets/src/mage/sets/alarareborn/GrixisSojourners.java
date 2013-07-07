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
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInASingleGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public class GrixisSojourners extends CardImpl<GrixisSojourners> {

    public GrixisSojourners(UUID ownerId) {
        super(ownerId, 112, "Grixis Sojourners", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Zombie");
        this.subtype.add("Ogre");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When you cycle Grixis Sojourners or it dies, you may exile target card from a graveyard.
        Ability ability1 = new CycleTriggeredAbility(new ExileTargetEffect(), true);
        Ability ability2 = new DiesTriggeredAbility(new ExileTargetEffect(), true);
        ability1.addTarget(new TargetCardInASingleGraveyard(1, 1, new FilterCard()));
        ability2.addTarget(new TargetCardInASingleGraveyard(1, 1, new FilterCard()));
        this.addAbility(ability1);
        this.addAbility(ability2);
        
        // Cycling {2}{B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}{B}")));
    }

    public GrixisSojourners(final GrixisSojourners card) {
        super(card);
    }

    @Override
    public GrixisSojourners copy() {
        return new GrixisSojourners(this);
    }
}
