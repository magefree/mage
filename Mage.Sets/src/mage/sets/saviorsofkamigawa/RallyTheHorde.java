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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class RallyTheHorde extends CardImpl {

    public RallyTheHorde(UUID ownerId) {
        super(ownerId, 110, "Rally the Horde", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{R}");
        this.expansionSetCode = "SOK";


        // Exile the top card of your library. Exile the top card of your library. Exile the top card of your library. If the last card exiled isn't a land, repeat this process. Put a 1/1 red Warrior creature token onto the battlefield for each nonland card exiled this way.
        this.getSpellAbility().addEffect(new RallyTheHordeEffect());
    }

    public RallyTheHorde(final RallyTheHorde card) {
        super(card);
    }

    @Override
    public RallyTheHorde copy() {
        return new RallyTheHorde(this);
    }
}

class RallyTheHordeEffect extends OneShotEffect {

    public RallyTheHordeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Exile the top card of your library. Exile the top card of your library. Exile the top card of your library. If the last card exiled isn't a land, repeat this process. Put a 1/1 red Warrior creature token onto the battlefield for each nonland card exiled this way";
    }

    public RallyTheHordeEffect(final RallyTheHordeEffect effect) {
        super(effect);
    }

    @Override
    public RallyTheHordeEffect copy() {
        return new RallyTheHordeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int nonLandCardsExiled = 0;
            while(controller.getLibrary().size() > 0) {
                nonLandCardsExiled += checkIfNextLibCardIsNonLandAndExile(controller, source, game);
                if (controller.getLibrary().size() > 0) {
                    nonLandCardsExiled += checkIfNextLibCardIsNonLandAndExile(controller, source, game);
                }
                if (controller.getLibrary().size() > 0) {
                    int nonLands = checkIfNextLibCardIsNonLandAndExile(controller, source, game);
                    if (nonLands == 0) {
                        break;
                    }
                    nonLandCardsExiled += nonLands;
                }
            }
            return new CreateTokenEffect(new RallyTheHordeWarriorToken(), nonLandCardsExiled).apply(game, source);

        }
        return false;
    }

    private int checkIfNextLibCardIsNonLandAndExile(Player controller, Ability source, Game game) {
        Card card = controller.getLibrary().getFromTop(game);
        if (card != null) {
            controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
            return card.getCardType().contains(CardType.LAND) ? 0:1;
        }
        return 0;
    }
}

class RallyTheHordeWarriorToken extends Token {

    public RallyTheHordeWarriorToken() {
        super("Warrior", "1/1 red Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add("Warrior");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
