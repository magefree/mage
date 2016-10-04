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
package mage.sets.invasion;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LoneFox

 */
public class Tek extends CardImpl {

    private static final FilterControlledPermanent filterPlains = new FilterControlledPermanent("Plains");
    private static final FilterControlledPermanent filterIsland = new FilterControlledPermanent("Island");
    private static final FilterControlledPermanent filterSwamp = new FilterControlledPermanent("Swamp");
    private static final FilterControlledPermanent filterMountain = new FilterControlledPermanent("Mountain");
    private static final FilterControlledPermanent filterForest = new FilterControlledPermanent("Forest");

    static {
        filterPlains.add(new SubtypePredicate("Plains"));
        filterIsland.add(new SubtypePredicate("Island"));
        filterSwamp.add(new SubtypePredicate("Swamp"));
        filterMountain.add(new SubtypePredicate("Mountain"));
        filterForest.add(new SubtypePredicate("Forest"));
    }


    public Tek(UUID ownerId) {
        super(ownerId, 313, "Tek", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.expansionSetCode = "INV";
        this.subtype.add("Dragon");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Tek gets +0/+2 as long as you control a Plains, has flying as long as you control an Island, gets +2/+0 as long as you control a Swamp, has first strike as long as you control a Mountain, and has trample as long as you control a Forest.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostSourceEffect(0, 2, Duration.WhileOnBattlefield),
            new PermanentsOnTheBattlefieldCondition(filterPlains), "{this} gets +0/+2 as long as you control a Plains"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
            new PermanentsOnTheBattlefieldCondition(filterIsland), ", has flying as long as you control an Island"));
        ability.addEffect(new ConditionalContinuousEffect(new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
            new PermanentsOnTheBattlefieldCondition(filterSwamp), ", gets +2/+0 as long as you control a Swamp"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
            new PermanentsOnTheBattlefieldCondition(filterMountain), ", has first strike as long as you control a Mountain"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),
            new PermanentsOnTheBattlefieldCondition(filterForest), ", and has trample as long as you control a Forest."));
        this.addAbility(ability);
    }

    public Tek(final Tek card) {
        super(card);
    }

    @Override
    public Tek copy() {
        return new Tek(this);
    }
}
