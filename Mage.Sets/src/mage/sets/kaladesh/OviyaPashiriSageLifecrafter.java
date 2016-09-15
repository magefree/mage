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
package mage.sets.kaladesh;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.ServoToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class OviyaPashiriSageLifecrafter extends CardImpl {

    public OviyaPashiriSageLifecrafter(UUID ownerId) {
        super(ownerId, 165, "Oviya Pashiri, Sage Lifecrafter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "KLD";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Artificer");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {2}{G}, {T}: Create a 1/1 colorless Servo artifact creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ServoToken(), 1), new ManaCostsImpl("{2}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {4}{G}, {T}: Create an X/X colorless Construct artifact creature token, where X is the number of creature you control.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new OviyaPashiriSageLifecrafterToken()), new ManaCostsImpl("{4}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public OviyaPashiriSageLifecrafter(final OviyaPashiriSageLifecrafter card) {
        super(card);
    }

    @Override
    public OviyaPashiriSageLifecrafter copy() {
        return new OviyaPashiriSageLifecrafter(this);
    }
}

class OviyaPashiriSageLifecrafterToken extends Token {

    final static FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("creature you control");

    OviyaPashiriSageLifecrafterToken() {
        super("Construct", "an X/X colorless Construct artifact creature token, where X is the number of creature you control");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add("Construct");
        power = new MageInt(0);
        toughness = new MageInt(0);
        DynamicValue controlledCreatures = new PermanentsOnBattlefieldCount(filterCreature);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(controlledCreatures, controlledCreatures, Duration.WhileOnBattlefield)));
    }
}
