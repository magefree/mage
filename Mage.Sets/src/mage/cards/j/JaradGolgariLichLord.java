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
package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public class JaradGolgariLichLord extends CardImpl {

    private static final FilterControlledPermanent filterSwamp = new FilterControlledPermanent("a Swamp");
    private static final FilterControlledPermanent filterForest = new FilterControlledPermanent("a Forest");

    static {
        filterSwamp.add(new SubtypePredicate(SubType.SWAMP));
        filterForest.add(new SubtypePredicate(SubType.FOREST));
    }

    public JaradGolgariLichLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Zombie");
        this.subtype.add("Elf");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Jarad, Golgari Lich Lord gets +1/+1 for each creature card in your graveyard.
        DynamicValue amount = new CardsInControllerGraveyardCount(new FilterCreatureCard());
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(amount, amount, Duration.WhileOnBattlefield));
        this.addAbility(ability);

        // {1}{B}{G}, Sacrifice another creature: Each opponent loses life equal to the sacrificed creature's power.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeOpponentsEffect(new SacrificeCostCreaturesPower()), new ManaCostsImpl("{1}{B}{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, false)));
        this.addAbility(ability);

        // Sacrifice a Swamp and a Forest: Return Jarad from your graveyard to your hand.
        ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(filterSwamp)));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filterForest)));
        this.addAbility(ability);

    }

    public JaradGolgariLichLord(final JaradGolgariLichLord card) {
        super(card);
    }

    @Override
    public JaradGolgariLichLord copy() {
        return new JaradGolgariLichLord(this);
    }
}
