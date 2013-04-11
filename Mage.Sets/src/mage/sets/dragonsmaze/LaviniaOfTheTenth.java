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

package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DetainAllEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */


public class LaviniaOfTheTenth  extends CardImpl<LaviniaOfTheTenth> {

    private static final FilterObject filter = new FilterObject("red");
    private static final FilterPermanent filterDetain = new FilterPermanent("each nonland permanent your opponents control with converted mana cost 4 or less");
    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filterDetain.add(new ControllerPredicate(Constants.TargetController.OPPONENT));
        filterDetain.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        filterDetain.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, 5));
    }

    public LaviniaOfTheTenth (UUID ownerId) {
        super(ownerId, 80, "Lavinia of the Tenth", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.supertype.add("Legendary");
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Protection from red
        this.addAbility(new ProtectionAbility(filter));
        // When Lavinia of the Tenth enters the battlefield, detain each nonland permanent your opponents control with converted mana cost 4 or less.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DetainAllEffect(filterDetain)));

    }

    public LaviniaOfTheTenth (final LaviniaOfTheTenth card) {
        super(card);
    }

    @Override
    public LaviniaOfTheTenth copy() {
        return new LaviniaOfTheTenth(this);
    }

}
