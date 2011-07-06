package mage.sets.championsofkamigawa;

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

import mage.Constants;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Loki
 */
public class SakuraTribeElder extends CardImpl<SakuraTribeElder> {

    final static FilterBasicLandCard filterLands = new FilterBasicLandCard();

    public SakuraTribeElder(UUID ownerId) {
        super(ownerId, 239, "Sakura-Tribe Elder", Constants.Rarity.COMMON, new Constants.CardType[]{Constants.CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Snake");
        this.subtype.add("Shaman");
        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        TargetCardInLibrary target = new TargetCardInLibrary(filterLands);
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(target, true, Constants.Outcome.PutLandInPlay), new SacrificeSourceCost()));
    }

    public SakuraTribeElder(final SakuraTribeElder card) {
        super(card);
    }

    @Override
    public SakuraTribeElder copy() {
        return new SakuraTribeElder(this);
    }

}
