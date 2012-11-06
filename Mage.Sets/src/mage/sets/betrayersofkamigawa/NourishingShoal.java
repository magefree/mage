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
 *  CONTRIBUTORS BE LIAB8LE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.dynamicvalue.common.ExileFromHandCostCardConvertedMana;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class NourishingShoal extends CardImpl<NourishingShoal> {

    private static final String ALTERNATIVE_COST_DESCRIPTION = "You may exile a green card with converted mana cost X from your hand rather than pay Nourishing Shoal's mana cost";

    public NourishingShoal(UUID ownerId) {
        super(ownerId, 137, "Nourishing Shoal", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{X}{G}{G}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Arcane");
        this.color.setGreen(true);

        // You may exile a green card with converted mana cost X from your hand rather than pay Nourishing Shoal's mana cost.
        FilterOwnedCard filter = new FilterOwnedCard("green card from your hand");
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(Predicates.not(new CardIdPredicate(this.getId()))); // the exile cost can never be paid with the card itself
        this.getSpellAbility().addAlternativeCost(new AlternativeCostImpl(ALTERNATIVE_COST_DESCRIPTION, new ExileFromHandCost(new TargetCardInHand(filter))));

        // You gain X life.
        this.getSpellAbility().addEffect(new GainLifeEffect(new ExileFromHandCostCardConvertedMana()));

    }

    public NourishingShoal(final NourishingShoal card) {
        super(card);
    }

    @Override
    public NourishingShoal copy() {
        return new NourishingShoal(this);
    }
}