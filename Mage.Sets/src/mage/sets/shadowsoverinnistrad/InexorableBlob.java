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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.permanent.token.Token;

/**
 *
 * @author fireshoes
 */
public class InexorableBlob extends CardImpl {

    public InexorableBlob(UUID ownerId) {
        super(ownerId, 212, "Inexorable Blob", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "SOI";
        this.subtype.add("Ooze");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // <i>Delirium</i> &mdash; Whenever Inexorable Blob attacks and there are at least four card types among cards in your graveyard,
        // put a 3/3 green Ooze creature token onto the battlefield tapped and attacking.
        this.addAbility(new ConditionalTriggeredAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new OozeToken(), 1, true, true), false),
                DeliriumCondition.getInstance(),
                "<i>Delirium</i> &mdash; Whenever {this} attacks and there are at least four card types among cards in your graveyard, "
                        + "put a 3/3 green Ooze creature token onto the battlefield tapped and attacking."));
    }

    public InexorableBlob(final InexorableBlob card) {
        super(card);
    }

    @Override
    public InexorableBlob copy() {
        return new InexorableBlob(this);
    }
}

class OozeToken extends Token {

    public OozeToken() {
        super("Ooze", "3/3 green Ooze creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Ooze");
        color.setGreen(true);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }
}
