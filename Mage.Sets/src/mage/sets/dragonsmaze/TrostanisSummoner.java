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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.CentaurToken;
import mage.game.permanent.token.KnightToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class TrostanisSummoner extends CardImpl<TrostanisSummoner> {

    public TrostanisSummoner(UUID ownerId) {
        super(ownerId, 110, "Trostani's Summoner", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{G}{W}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Elf");
        this.subtype.add("Shaman");

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Trostani's Summoner enters the battlefield, put a 2/2 white Knight creature token with vigilance, a 3/3 green Centaur creature token, and a 4/4 green Rhino creature token with trample onto the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KnightToken()));
        ability.addEffect(new CreateTokenEffect(new CentaurToken()));
        ability.addEffect(new CreateTokenEffect(new RhinoToken()));
        this.addAbility(ability);

    }

    public TrostanisSummoner(final TrostanisSummoner card) {
        super(card);
    }

    @Override
    public TrostanisSummoner copy() {
        return new TrostanisSummoner(this);
    }
}

class RhinoToken extends Token {

    public RhinoToken() {
        super("Rhino", "4/4 green Rhino creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Rhino");
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(TrampleAbility.getInstance());
        setOriginalExpansionSetCode("RTR");
    }
}