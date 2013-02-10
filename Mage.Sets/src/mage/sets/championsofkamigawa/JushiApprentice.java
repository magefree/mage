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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.condition.common.CardsInHandCondition.CountType;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.CopyTokenEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class JushiApprentice extends CardImpl<JushiApprentice> {

    public JushiApprentice(UUID ownerId) {
        super(ownerId, 70, "Jushi Apprentice", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Tomoya the Revealer";

        // {2}{U}, {tap}: Draw a card. If you have nine or more cards in hand, flip Jushi Apprentice.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardControllerEffect(1), new ManaCostsImpl("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(new FlipSourceEffect(), new CardsInHandCondition(CountType.MORE_THAN, 8),
                    "If you have nine or more cards in hand, flip {this}"));
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(new CopyTokenEffect(new TomoyaTheRevealer()), FlippedCondition.getInstance(), "")));
    }

    public JushiApprentice(final JushiApprentice card) {
        super(card);
    }

    @Override
    public JushiApprentice copy() {
        return new JushiApprentice(this);
    }
}

class TomoyaTheRevealer extends Token {

    TomoyaTheRevealer() {
        super("Tomoya the Revealer", "");
        supertype.add("Legendary");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add("Human");
        subtype.add("Wizard");
        power = new MageInt(2);
        toughness = new MageInt(3);

        // {3}{U}{U},{T} : Target player draws X cards, where X is the number of cards in your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardTargetEffect(new CardsInControllerHandCount()), new ManaCostsImpl("{3}{U}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }
}
