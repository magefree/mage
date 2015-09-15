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
package mage.sets.futuresight;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.permanent.token.Token;

/**
 *
 * @author LoneFox
 */
public class Sparkspitter extends CardImpl {

    public Sparkspitter(UUID ownerId) {
        super(ownerId, 109, "Sparkspitter", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "FUT";
        this.subtype.add("Elemental");
        this.subtype.add("Spellshaper");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {R}, {tap}, Discard a card: Put a 3/1 red Elemental creature token named Spark Elemental onto the battlefield. It has trample, haste, and "At the beginning of the end step, sacrifice Spark Elemental."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SparkElementalToken()), new ManaCostsImpl("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    public Sparkspitter(final Sparkspitter card) {
        super(card);
    }

    @Override
    public Sparkspitter copy() {
        return new Sparkspitter(this);
    }
}

class SparkElementalToken extends Token {

    public SparkElementalToken() {
        super("Spark Elemental", "3/1 red Elemental creature token named Spark Elemental with trample, haste, and \"At the beginning of the end step, sacrifice Spark Elemental.\"");
        this.setOriginalExpansionSetCode("FUT");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add("Elemental");
        power = new MageInt(3);
        toughness = new MageInt(1);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect(), TargetController.ANY, false));
    }
}
