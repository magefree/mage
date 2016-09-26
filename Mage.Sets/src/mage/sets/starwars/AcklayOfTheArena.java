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
package mage.sets.starwars;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousTriggeredAbility;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class AcklayOfTheArena extends CardImpl {

    public AcklayOfTheArena(UUID ownerId) {
        super(ownerId, 180, "Acklay of the Arena", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");
        this.expansionSetCode = "SWS";
        this.subtype.add("Insect");
        this.subtype.add("Beast");
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // {2}{R}{G}{W}: Monstrosity 1.
        this.addAbility(new MonstrosityAbility("{2}{R}{G}{W}", 1));

        // Whenever a creature you control becomes monstrous, it fights target creature an opponent controls.
        Ability ability = new BecomesMonstrousTriggeredAbility(new FightTargetsEffect("it fights target creature an opponent controls"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

    }

    public AcklayOfTheArena(final AcklayOfTheArena card) {
        super(card);
    }

    @Override
    public AcklayOfTheArena copy() {
        return new AcklayOfTheArena(this);
    }
}
