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
package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TimingRule;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class DreadWanderer extends CardImpl {

    public DreadWanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add("Zombie");
        this.subtype.add("Jackal");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Dread Wanderer enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {2}{B}: Return Dread Wanderer from your graveyard to the battlefield.
        // Activate this ability only any time you could cast a sorcery and only if you have one or fewer cards in hand.
        CardsInHandCondition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 2);
        ConditionalActivatedAbility ability = new ConditionalActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(), new ManaCostsImpl("{2}{B}"), condition);
        ability.setTiming(TimingRule.SORCERY);
        addAbility(ability);
    }

    public DreadWanderer(final DreadWanderer card) {
        super(card);
    }

    @Override
    public DreadWanderer copy() {
        return new DreadWanderer(this);
    }
}
