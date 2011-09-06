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
package mage.sets.lorwyn;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public class WortBoggartAuntie extends CardImpl<WortBoggartAuntie> {

    private final static FilterCard filter = new FilterCard("Goblin");

    static {
        filter.getSubtype().add("Goblin");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public WortBoggartAuntie(UUID ownerId) {
        super(ownerId, 252, "Wort, Boggart Auntie", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        this.expansionSetCode = "LRW";
        this.supertype.add("Legendary");
        this.subtype.add("Goblin");
        this.subtype.add("Shaman");
        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FearAbility.getInstance());
        // At the beginning of your upkeep, you may return target Goblin card from your graveyard to your hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ReturnToHandTargetEffect(), Constants.TargetController.YOU, true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public WortBoggartAuntie(final WortBoggartAuntie card) {
        super(card);
    }

    @Override
    public WortBoggartAuntie copy() {
        return new WortBoggartAuntie(this);
    }
}
