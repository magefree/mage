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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TimingRule;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.Token;

/**
 *
 * @author North
 */
public class SpiderSpawning extends CardImpl<SpiderSpawning> {

    public SpiderSpawning(UUID ownerId) {
        super(ownerId, 203, "Spider Spawning", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{4}{G}");
        this.expansionSetCode = "ISD";

        this.color.setGreen(true);

        // Put a 1/2 green Spider creature token with reach onto the battlefield for each creature card in your graveyard.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiderToken(), new CardsInControllerGraveyardCount(new FilterCreatureCard())));
        // Flashback {6}{B}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{6}{B}"), TimingRule.SORCERY));
    }

    public SpiderSpawning(final SpiderSpawning card) {
        super(card);
    }

    @Override
    public SpiderSpawning copy() {
        return new SpiderSpawning(this);
    }
}

class SpiderToken extends Token {

    public SpiderToken() {
        super("Spider", "1/2 green Spider creature token with reach");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Spider");
        power = new MageInt(1);
        toughness = new MageInt(2);
        addAbility(ReachAbility.getInstance());
    }
}
