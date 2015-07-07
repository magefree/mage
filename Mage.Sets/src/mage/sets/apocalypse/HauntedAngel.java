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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.token.Token;

/**
 *
 * @author LoneFox

 */
public class HauntedAngel extends CardImpl {

    public HauntedAngel(UUID ownerId) {
        super(ownerId, 12, "Haunted Angel", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "APC";
        this.subtype.add("Angel");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Haunted Angel dies, exile Haunted Angel and each other player puts a 3/3 black Angel creature token with flying onto the battlefield.
        Ability ability = new DiesTriggeredAbility(new ExileSourceEffect());
        ability.addEffect(new HauntedAngelEffect());
        this.addAbility(ability);
    }

    public HauntedAngel(final HauntedAngel card) {
        super(card);
    }

    @Override
    public HauntedAngel copy() {
        return new HauntedAngel(this);
    }
}

class HauntedAngelEffect extends OneShotEffect {

    public HauntedAngelEffect() {
        super(Outcome.Detriment);
        staticText = "and each other player puts a 3/3 black Angel creature token with flying onto the battlefield.";
    }

    public HauntedAngelEffect(HauntedAngelEffect copy) {
        super(copy);
    }

    @Override
    public HauntedAngelEffect copy() {
        return new HauntedAngelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        HauntedAngelToken token = new HauntedAngelToken();
        for(UUID playerId: game.getState().getPlayersInRange(controllerId, game)) {
            if(!playerId.equals(controllerId)) {
                token.putOntoBattlefield(1, game, source.getSourceId(), playerId);
            }
        }
        return true;
    }
}

class HauntedAngelToken extends Token {
    public HauntedAngelToken() {
        super("Angel", "3/3 black Angel creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Angel");
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
    }
}
