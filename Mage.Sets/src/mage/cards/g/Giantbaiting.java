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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.Token;

/**
 *
 * @author jeffwadsworth
 */
public class Giantbaiting extends CardImpl {

    public Giantbaiting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R/G}");

        // Create a 4/4 red and green Giant Warrior creature token with haste. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new GiantbaitingEffect());

        // Conspire
        this.addAbility(new ConspireAbility(getId(), ConspireAbility.ConspireTargets.NONE));

    }

    public Giantbaiting(final Giantbaiting card) {
        super(card);
    }

    @Override
    public Giantbaiting copy() {
        return new Giantbaiting(this);
    }
}

class GiantbaitingEffect extends OneShotEffect {

    public GiantbaitingEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 4/4 red and green Giant Warrior creature token with haste. Exile it at the beginning of the next end step";
    }

    public GiantbaitingEffect(final GiantbaitingEffect effect) {
        super(effect);
    }

    @Override
    public GiantbaitingEffect copy() {
        return new GiantbaitingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new GiantWarriorToken());
        if(effect.apply(game, source))
        {
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }
        return false;
    }
}

class GiantWarriorToken extends Token {

    GiantWarriorToken() {
        super("Giant Warrior", "4/4 red and green Giant Warrior creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setGreen(true);
        subtype.add("Giant");
        subtype.add("Warrior");
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(HasteAbility.getInstance());
    }
}
