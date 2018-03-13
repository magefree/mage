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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.Token;

/**
 *
 * @author anonymous
 */
public class SvogthosTheRestlessTomb extends CardImpl {

    public SvogthosTheRestlessTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {3}{B}{G}: Until end of turn, Svogthos, the Restless Tomb becomes a black and green Plant Zombie creature with "This creature's power and toughness are each equal to the number of creature cards in your graveyard." It's still a land.
        // set to character defining to prevent setting P/T again to 0 becuase already set by CDA of the token
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new SvogthosToken(), "land", Duration.EndOfTurn, false, true), new ManaCostsImpl<>("{3}{B}{G}"));
        this.addAbility(ability);
    }

    public SvogthosTheRestlessTomb(final SvogthosTheRestlessTomb card) {
        super(card);
    }

    @Override
    public SvogthosTheRestlessTomb copy() {
        return new SvogthosTheRestlessTomb(this);
    }
}

class SvogthosToken extends Token {

    public SvogthosToken() {
        super("", "black and green Plant Zombie creature with \"This creature's power and toughness are each equal to the number of creature cards in your graveyard.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PLANT);
        subtype.add(SubType.ZOMBIE);
        color.setGreen(true);
        color.setBlack(true);
        power = new MageInt(0);
        toughness = new MageInt(0);
        CardsInControllerGraveyardCount count = new CardsInControllerGraveyardCount(new FilterCreatureCard("creature cards"));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(count, Duration.EndOfGame)));
    }
}
