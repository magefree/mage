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
package mage.sets.theros;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.command.Emblem;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author LevelX2
 */
public class ElspethSunsChampion extends CardImpl<ElspethSunsChampion> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 4 or greater");
    static {
        filter.add(new PowerPredicate(Filter.ComparisonType.GreaterThan, 3));
    }

    public ElspethSunsChampion(UUID ownerId) {
        super(ownerId, 9, "Elspeth, Sun's Champion", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{4}{W}{W}");
        this.expansionSetCode = "THS";
        this.subtype.add("Elsepth");

        this.color.setWhite(true);

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), false));

        // +1: Put three 1/1 white Soldier creature tokens onto the battlefield.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new SoldierToken(), 3), 1));
        // -3: Destroy all creatures with power 4 or greater.
        this.addAbility(new LoyaltyAbility(new DestroyAllEffect(filter), -3));
        // -7: You get an emblem with "Creatures you control get +2/+2 and have flying."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ElspethSunsChampionEmblem()), -7));
    }

    public ElspethSunsChampion(final ElspethSunsChampion card) {
        super(card);
    }

    @Override
    public ElspethSunsChampion copy() {
        return new ElspethSunsChampion(this);
    }
}

// -7: You get an emblem with "Creatures you control get +2/+2 and have flying."
class ElspethSunsChampionEmblem extends Emblem {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures");

    public ElspethSunsChampionEmblem() {
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new BoostControlledEffect(2,2, Duration.WhileOnBattlefield, filter, false));
        ability.addEffect(new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter));
        this.getAbilities().add(ability);

    }
}
