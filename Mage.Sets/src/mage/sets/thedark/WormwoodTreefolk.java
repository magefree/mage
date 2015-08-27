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
package mage.sets.thedark;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public class WormwoodTreefolk extends CardImpl {

    public WormwoodTreefolk(UUID ownerId) {
        super(ownerId, 55, "Wormwood Treefolk", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "DRK";
        this.subtype.add("Treefolk");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {G}{G}: Wormwood Treefolk gains forestwalk until end of turn and deals 2 damage to you.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(new ForestwalkAbility(false), Duration.EndOfTurn), new ManaCostsImpl("{G}{G}"));
        ability.addEffect(new DamageControllerEffect(2));
        this.addAbility(ability);

        // {B}{B}: Wormwood Treefolk gains swampwalk until end of turn and deals 2 damage to you.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(new SwampwalkAbility(false), Duration.EndOfTurn), new ManaCostsImpl("{B}{B}"));
        ability.addEffect(new DamageControllerEffect(2));
        this.addAbility(ability);
    }

    public WormwoodTreefolk(final WormwoodTreefolk card) {
        super(card);
    }

    @Override
    public WormwoodTreefolk copy() {
        return new WormwoodTreefolk(this);
    }
}
