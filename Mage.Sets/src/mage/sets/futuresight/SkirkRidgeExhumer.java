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
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public class SkirkRidgeExhumer extends CardImpl {

    public SkirkRidgeExhumer(UUID ownerId) {
        super(ownerId, 77, "Skirk Ridge Exhumer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "FUT";
        this.subtype.add("Zombie");
        this.subtype.add("Spellshaper");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}, {tap}, Discard a card: Put a 1/1 black Zombie Goblin creature token named Festering Goblin onto the battlefield. It has "When Festering Goblin dies, target creature gets -1/-1 until end of turn."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new FesteringGoblinToken()), new ManaCostsImpl("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    public SkirkRidgeExhumer(final SkirkRidgeExhumer card) {
        super(card);
    }

    @Override
    public SkirkRidgeExhumer copy() {
        return new SkirkRidgeExhumer(this);
    }
}

class FesteringGoblinToken extends Token {

    public FesteringGoblinToken() {
        super("Festering Goblin", "1/1 black Zombie Goblin creature token named Festering Goblin with \"When Festering Goblin dies, target creature gets -1/-1 until end of turn.\"");
        this.setOriginalExpansionSetCode("FUT");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Zombie");
        subtype.add("Goblin");
        power = new MageInt(1);
        toughness = new MageInt(1);

        Ability ability = new DiesTriggeredAbility(new BoostTargetEffect(-1, -1, Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }
}
