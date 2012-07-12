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
package mage.sets.guildpact;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.permanent.token.Token;

/**
 * @author Loki
 */
public class DuneBroodNephilim extends CardImpl<DuneBroodNephilim> {

    final static FilterControlledPermanent filterLands = new FilterControlledPermanent("land you control");

    static {
        filterLands.add(new CardTypePredicate(CardType.LAND));
    }

    public DuneBroodNephilim(UUID ownerId) {
        super(ownerId, 110, "Dune-Brood Nephilim", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}{R}{G}{W}");
        this.expansionSetCode = "GPT";
        this.subtype.add("Nephilim");
        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(new DuneBroodNephilimToken(), new PermanentsOnBattlefieldCount(filterLands)), false));
    }

    public DuneBroodNephilim(final DuneBroodNephilim card) {
        super(card);
    }

    @Override
    public DuneBroodNephilim copy() {
        return new DuneBroodNephilim(this);
    }
}

class DuneBroodNephilimToken extends Token {
    DuneBroodNephilimToken() {
        super("Sand", "1/1 colorless Sand creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Sand");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}