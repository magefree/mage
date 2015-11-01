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
package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.game.permanent.token.CatToken;

/**
 *
 * @author Loki, North
 */
public class KembaKhaRegent extends CardImpl {

    public KembaKhaRegent(UUID ownerId) {
        super(ownerId, 12, "Kemba, Kha Regent", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "SOM";
        this.supertype.add("Legendary");
        this.subtype.add("Cat");
        this.subtype.add("Cleric");

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new CatToken(), new EquipmentAttachedCount()),
                TargetController.YOU, false));
    }

    public KembaKhaRegent(final KembaKhaRegent card) {
        super(card);
    }

    @Override
    public KembaKhaRegent copy() {
        return new KembaKhaRegent(this);
    }
}
