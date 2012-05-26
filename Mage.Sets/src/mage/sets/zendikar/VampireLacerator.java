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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.TenOrLessLifeCondition;
import mage.abilities.condition.common.UnlessCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceEffect;
import mage.cards.CardImpl;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class VampireLacerator extends CardImpl<VampireLacerator> {

	public VampireLacerator(UUID ownerId) {
		super(ownerId, 115, "Vampire Lacerator", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}");
		this.expansionSetCode = "ZEN";
		this.subtype.add("Vampire");
		this.subtype.add("Warrior");
		this.color.setBlack(true);
		this.power = new MageInt(2);
		this.toughness = new MageInt(2);

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
            new ConditionalOneShotEffect(
					new LoseLifeSourceEffect(1),
					new UnlessCondition( new TenOrLessLifeCondition(TenOrLessLifeCondition.CheckType.AN_OPPONENT) ),
					"you lose 1 life unless an opponent has 10 or less life"), Constants.TargetController.YOU, false));
	}

	public VampireLacerator(final VampireLacerator card) {
		super(card);
	}

	@Override
	public VampireLacerator copy() {
		return new VampireLacerator(this);
	}
}
