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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiscardsACardOpponentTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.game.permanent.token.ElfToken;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 *
 */
public class NathOfTheGiltLeaf extends CardImpl<NathOfTheGiltLeaf> {

    public NathOfTheGiltLeaf(UUID ownerId) {
        super(ownerId, 250, "Nath of the Gilt-Leaf", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");
        this.expansionSetCode = "LRW";
        this.supertype.add("Legendary");
        this.subtype.add("Elf");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you may have target opponent discard a card at random.
        Effect effect = new DiscardTargetEffect(1, true);
        effect.setText("you may have target opponent discard a card at random");
        Ability ability = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, true);
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(ability);

        // Whenever an opponent discards a card, you may put a 1/1 green Elf Warrior creature token onto the battlefield.
        Effect effect2 = new CreateTokenEffect(new ElfToken());
        effect2.setText("you may put a 1/1 green Elf Warrior creature token onto the battlefield");
        this.addAbility(new DiscardsACardOpponentTriggeredAbility(effect2, true));

    }

    public NathOfTheGiltLeaf(final NathOfTheGiltLeaf card) {
        super(card);
    }

    @Override
    public NathOfTheGiltLeaf copy() {
        return new NathOfTheGiltLeaf(this);
    }
}
