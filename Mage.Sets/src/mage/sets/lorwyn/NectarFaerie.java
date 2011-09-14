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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public class NectarFaerie extends CardImpl<NectarFaerie> {

    private final static FilterPermanent filter = new FilterPermanent("Faerie or Elf");

    static {
        filter.getSubtype().add("Faerie");
        filter.getSubtype().add("Elf");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public NectarFaerie(UUID ownerId) {
        super(ownerId, 130, "Nectar Faerie", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Faerie");
        this.subtype.add("Wizard");
        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        // {B}, {tap}: Target Faerie or Elf gains lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Constants.Duration.EndOfTurn), new ColoredManaCost(Constants.ColoredManaSymbol.B));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public NectarFaerie(final NectarFaerie card) {
        super(card);
    }

    @Override
    public NectarFaerie copy() {
        return new NectarFaerie(this);
    }
}
