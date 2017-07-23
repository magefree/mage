/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterBySubtypeCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class KnightOfTheWhiteOrchid extends CardImpl {

    public KnightOfTheWhiteOrchid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add("Human");
        this.subtype.add("Knight");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // When Knight of the White Orchid enters the battlefield, if an opponent controls more lands than you, you may search your library for a Plains card, put it onto the battlefield, then shuffle your library.
        this.addAbility(new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 1, new FilterBySubtypeCard(SubType.PLAINS)), false), true),
                new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS),
                "When {this} enters the battlefield, if an opponent controls more lands than you, you may search your library for a Plains card, put it onto the battlefield, then shuffle your library"));
        
    }

    public KnightOfTheWhiteOrchid(final KnightOfTheWhiteOrchid card) {
        super(card);
    }

    @Override
    public KnightOfTheWhiteOrchid copy() {
        return new KnightOfTheWhiteOrchid(this);
    }

}
