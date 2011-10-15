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
package mage.sets.ravnika;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.game.permanent.token.Token;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public class HuntedHorror extends CardImpl<HuntedHorror> {

    public HuntedHorror(UUID ownerId) {
        super(ownerId, 90, "Hunted Horror", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Horror");

        this.color.setBlack(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        this.addAbility(TrampleAbility.getInstance());
        // When Hunted Horror enters the battlefield, put two 3/3 green Centaur creature tokens with protection from black onto the battlefield under target opponent's control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new CentaurToken(), 2), false);
        Target target = new TargetOpponent();
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public HuntedHorror(final HuntedHorror card) {
        super(card);
    }

    @Override
    public HuntedHorror copy() {
        return new HuntedHorror(this);
    }
}

class CentaurToken extends Token {

    private final static FilterCard filter = new FilterCard("black");

    static {
        filter.setUseColor(true);
        filter.setColor(ObjectColor.BLACK);
    }

    CentaurToken() {
        super("Centaur", "3/3 green Centaur creature tokens with protection from black");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.GREEN;
        subtype.add("Centaur");
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(new ProtectionAbility(filter));
    }
}

