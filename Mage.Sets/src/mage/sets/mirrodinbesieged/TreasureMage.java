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

package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.search.SearchLibraryRevealPutInHandEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public class TreasureMage extends CardImpl<TreasureMage> {

    private static FilterCard filter = new FilterCard("an artifact card with converted mana cost 6 or more");

    static {
        filter.getCardType().add(CardType.ARTIFACT);
        filter.setConvertedManaCost(5);
        filter.setConvertedManaCostComparison(Filter.ComparisonType.GreaterThan);
    }

    public TreasureMage (UUID ownerId) {
        super(ownerId, 34, "Treasure Mage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
        SearchEffect effect = new SearchLibraryRevealPutInHandEffect(target, false);
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, true));
    }

    public TreasureMage (final TreasureMage card) {
        super(card);
    }

    @Override
    public TreasureMage copy() {
        return new TreasureMage(this);
    }

}
