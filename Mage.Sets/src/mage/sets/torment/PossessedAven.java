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
package mage.sets.torment;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public class PossessedAven extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public PossessedAven(UUID ownerId) {
        super(ownerId, 45, "Possessed Aven", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "TOR";
        this.subtype.add("Bird");
        this.subtype.add("Soldier");
        this.subtype.add("Horror");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Threshold - As long as seven or more cards are in your graveyard, Possessed Aven gets +1/+1, is black, and has "{2}{B}, {tap}: Destroy target blue creature."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), new CardsInControllerGraveCondition(7),
            "As long as seven or more cards are in your graveyard, {this} gets +1/+1"));

        Effect effect = new ConditionalContinuousEffect(new BecomesColorSourceEffect(ObjectColor.BLACK, Duration.WhileOnBattlefield),
            new CardsInControllerGraveCondition(7), ", is black");
        ability.addEffect(effect);

        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{2}{B}"));
        gainedAbility.addCost(new TapSourceCost());
        gainedAbility.addTarget(new TargetCreaturePermanent(filter));
        effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(gainedAbility),
            new CardsInControllerGraveCondition(7), ", and has \"{2}{B}, {T}: Destroy target blue creature.\"");
        ability.addEffect(effect);

        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public PossessedAven(final PossessedAven card) {
        super(card);
    }

    @Override
    public PossessedAven copy() {
        return new PossessedAven(this);
    }
}
