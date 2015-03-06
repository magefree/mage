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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LevelX2
 */
public class BattleBrawler extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("As long as you control a red or white permanent");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.RED), new ColorPredicate(ObjectColor.WHITE)));
    }

    public BattleBrawler(UUID ownerId) {
        super(ownerId, 63, "Battle Brawler", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Orc");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as you control a red or white permanent, Battle Brawler gets +1/+0 and has first strike.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostSourceEffect(1,0, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter), "As long as you control a red or white permanent, {this} gets +1/+0"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter), "and has first strike"));
        this.addAbility(ability);
    }

    public BattleBrawler(final BattleBrawler card) {
        super(card);
    }

    @Override
    public BattleBrawler copy() {
        return new BattleBrawler(this);
    }
}
