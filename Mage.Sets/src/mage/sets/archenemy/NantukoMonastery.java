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
package mage.sets.archenemy;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.permanent.token.Token;

/**
 *
 * @author anonymous
 */
public class NantukoMonastery extends CardImpl {

    public NantukoMonastery(UUID ownerId) {
        super(ownerId, 131, "Nantuko Monastery", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ARC";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // Threshold - {G}{W}: Nantuko Monastery becomes a 4/4 green and white Insect Monk creature with first strike until end of turn. It's still a land. Activate this ability only if seven or more cards are in your graveyard.
        Effect effect = new BecomesCreatureSourceEffect(new NantukoMonasteryToken(), "land", Duration.Custom);
        effect.setText("{this} becomes a 4/4 green and white Insect Monk creature");
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{G}{W}"),
                new CardsInControllerGraveCondition(7),
                "<i>Threshold</i> - {G}{W}: Nantuko Monastery becomes a 4/4 green and white Insect Monk creature with first strike until end of turn. It's still a land. Activate this ability only if seven or more cards are in your graveyard.");
        ability.addEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn));
        this.addAbility(ability);
    }

    public NantukoMonastery(final NantukoMonastery card) {
        super(card);
    }

    @Override
    public NantukoMonastery copy() {
        return new NantukoMonastery(this);
    }
}

class NantukoMonasteryToken extends Token {

    public NantukoMonasteryToken() {
        super("", "4/4 green and white Insect Monk creature");
        cardType.add(CardType.CREATURE);
        subtype.add("Insect");
        subtype.add("Monk");
        color.setGreen(true);
        color.setWhite(true);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }
}
