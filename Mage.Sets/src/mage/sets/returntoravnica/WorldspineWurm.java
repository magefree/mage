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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class WorldspineWurm extends CardImpl<WorldspineWurm> {

    public WorldspineWurm(UUID ownerId) {
        super(ownerId, 140, "Worldspine Wurm", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{8}{G}{G}{G}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Wurm");

        this.color.setGreen(true);
        this.power = new MageInt(15);
        this.toughness = new MageInt(15);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Worldspine Wurm dies, put three 5/5 green Wurm creature tokens with trample onto the battlefield.
        this.addAbility(new DiesTriggeredAbility(new CreateTokenEffect(new WorldspineWurmToken(), 3)));
        // When Worldspine Wurm is put into a graveyard from anywhere, shuffle it into its owner's library.
        this.addAbility(new PutIntoGraveFromAnywhereTriggeredAbility(new WorldspineWurmEffect()));
    }

    public WorldspineWurm(final WorldspineWurm card) {
        super(card);
    }

    @Override
    public WorldspineWurm copy() {
        return new WorldspineWurm(this);
    }
}


class WorldspineWurmEffect extends OneShotEffect<WorldspineWurmEffect> {
    WorldspineWurmEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "shuffle it into its owner's library";
    }

    WorldspineWurmEffect(final WorldspineWurmEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card c = player.getGraveyard().get(source.getSourceId(), game);
            if (c != null) {
                player.getGraveyard().remove(c);
                player.getLibrary().putOnTop(c, game);
                player.getLibrary().shuffle();
                return true;
            }

        }
        return false;
    }

    @Override
    public WorldspineWurmEffect copy() {
        return new WorldspineWurmEffect(this);
    }
}


class WorldspineWurmToken extends Token {

    public WorldspineWurmToken() {
        super("Wurm", "5/5 green Wurm creature tokens with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Wurm");
        power = new MageInt(5);
        toughness = new MageInt(5);
        
        this.addAbility(TrampleAbility.getInstance());
    }
}