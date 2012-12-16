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
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.condition.common.UnlessCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DiscardControllerEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;

/**
 *
 * @author LevelX2
 */
public class SphinxOfLostTruths extends CardImpl<SphinxOfLostTruths> {

    public SphinxOfLostTruths(UUID ownerId) {
        super(ownerId, 69, "Sphinx of Lost Truths", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Sphinx");

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Kicker {1}{U} (You may pay an additional {1}{U} as you cast this spell.)
        this.addAbility(new KickerAbility("{1}{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Sphinx of Lost Truths enters the battlefield, draw three cards. Then if it wasn't kicked, discard three cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardControllerEffect(3));
        ability.addEffect(new ConditionalOneShotEffect(new DiscardControllerEffect(3), new UnlessCondition(KickedCondition.getInstance()),
                "Then if it wasn't kicked, discard three cards"));
        this.addAbility(ability);
    }

    public SphinxOfLostTruths(final SphinxOfLostTruths card) {
        super(card);
    }

    @Override
    public SphinxOfLostTruths copy() {
        return new SphinxOfLostTruths(this);
    }
}