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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantCounterAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public class IsaoEnlightenedBushi extends CardImpl<IsaoEnlightenedBushi> {

    private final static FilterPermanent filter = new FilterPermanent("Samurai");

    static {
        filter.getSubtype().add("Samurai");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public IsaoEnlightenedBushi(UUID ownerId) {
        super(ownerId, 129, "Isao, Enlightened Bushi", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Samurai");
        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        // Isao, Enlightened Bushi can't be countered.
        this.addAbility(new CantCounterAbility());
        this.addAbility(new BushidoAbility(2));
        // {2}: Regenerate target Samurai.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new RegenerateTargetEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public IsaoEnlightenedBushi(final IsaoEnlightenedBushi card) {
        super(card);
    }

    @Override
    public IsaoEnlightenedBushi copy() {
        return new IsaoEnlightenedBushi(this);
    }
}
