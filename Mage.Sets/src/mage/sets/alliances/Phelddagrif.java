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
package mage.sets.alliances;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.permanent.token.SaprolingToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class Phelddagrif extends CardImpl<Phelddagrif> {

    public Phelddagrif(UUID ownerId) {
        super(ownerId, 196, "Phelddagrif", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");
        this.expansionSetCode = "ALL";
        this.supertype.add("Legendary");
        this.subtype.add("Phelddagrif");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {G}: Phelddagrif gains trample until end of turn. Target opponent puts a 1/1 green Hippo creature token onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn),new ManaCostsImpl("{G}"));
        ability.addEffect(new CreateTokenTargetEffect(new HippoToken()));
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(ability);
        // {W}: Phelddagrif gains flying until end of turn. Target opponent gains 2 life.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),new ManaCostsImpl("{W}"));
        ability.addEffect(new GainLifeTargetEffect(2));
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(ability);
        // {U}: Return Phelddagrif to its owner's hand. Target opponent may draw a card.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true),new ManaCostsImpl("{U}"));
        ability.addEffect(new DrawCardTargetEffect(1, true));
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(ability);
    }

    public Phelddagrif(final Phelddagrif card) {
        super(card);
    }

    @Override
    public Phelddagrif copy() {
        return new Phelddagrif(this);
    }
}

class HippoToken extends Token {

    public HippoToken() {
        super("Hippo", "1/1 green Hippo creature token");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.GREEN;
        subtype.add("Hippo");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
