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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public class HuntedTroll extends CardImpl<HuntedTroll> {

    public HuntedTroll(UUID ownerId) {
        super(ownerId, 170, "Hunted Troll", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Troll");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.power = new MageInt(8);
        this.toughness = new MageInt(4);

        // When Hunted Troll enters the battlefield, put four 1/1 blue Faerie creature tokens with flying onto the battlefield under target opponent's control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new FaerieToken(), 4), false);
        Target target = new TargetOpponent();
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
        // {G}: Regenerate Hunted Troll.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ColoredManaCost(Constants.ColoredManaSymbol.G)));
    }

    public HuntedTroll(final HuntedTroll card) {
        super(card);
    }

    @Override
    public HuntedTroll copy() {
        return new HuntedTroll(this);
    }
}

class FaerieToken extends Token {
    FaerieToken() {
        super("Faerie", "1/1 blue Faerie creature tokens with flying");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.BLUE;
        subtype.add("Faerie");
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }
}