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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class SoulOfTheros extends CardImpl {

    public SoulOfTheros(UUID ownerId) {
        super(ownerId, 34, "Soul of Theros", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.expansionSetCode = "M15";
        this.subtype.add("Avatar");

        this.color.setWhite(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        
        // {4}{W}{W}: Creatures you control get +2/+2 and gain first strike and lifelink until end of turn.
        Effect effect1 = new BoostControlledEffect(2, 2, Duration.EndOfTurn);
        effect1.setText("Creatures you control get +2/+2");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect1, new ManaCostsImpl("{4}{W}{W}"));
        Effect effect2 = new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect2.setText("and gain first strike");
        ability.addEffect(effect2);
        Effect effect3 = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect3.setText("and lifelink until end of turn");
        ability.addEffect(effect3);
        this.addAbility(ability);

        // {4}{W}{W}, Exile Soul of Theros from your graveyard: Creatures you control get +2/+2 and gain first strike and lifelink until end of turn.
        ability = new SimpleActivatedAbility(Zone.GRAVEYARD, effect1, new ManaCostsImpl("{4}{W}{W}"));
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addEffect(effect2);
        ability.addEffect(effect3);
        this.addAbility(ability);
    }

    public SoulOfTheros(final SoulOfTheros card) {
        super(card);
    }

    @Override
    public SoulOfTheros copy() {
        return new SoulOfTheros(this);
    }
}
