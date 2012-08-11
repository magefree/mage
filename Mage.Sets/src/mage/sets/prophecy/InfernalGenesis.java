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
package mage.sets.prophecy;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class InfernalGenesis extends CardImpl<InfernalGenesis> {

    public InfernalGenesis(UUID ownerId) {
        super(ownerId, 68, "Infernal Genesis", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");
        this.expansionSetCode = "PCY";

        this.color.setBlack(true);

        // At the beginning of each player's upkeep, that player puts the top card of his or her library into his or her graveyard. Then he or she puts X 1/1 black Minion creature tokens onto the battlefield, where X is that card's converted mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new InfernalGenesisEffect(), Constants.TargetController.ANY, false));
    }

    public InfernalGenesis(final InfernalGenesis card) {
        super(card);
    }

    @Override
    public InfernalGenesis copy() {
        return new InfernalGenesis(this);
    }
}

class InfernalGenesisEffect extends OneShotEffect<InfernalGenesisEffect> {

    InfernalGenesisEffect() {
        super(Constants.Outcome.BoostCreature);
        staticText = "that player puts the top card of his or her library into his or her graveyard. Then he or she puts X 1/1 black Minion creature tokens onto the battlefield, where X is that card's converted mana cost";
    }

    InfernalGenesisEffect(final InfernalGenesisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                if (card.moveToZone(Constants.Zone.GRAVEYARD, source.getId(), game, false)) {
                    int cmc = card.getManaCost().convertedManaCost();
                    MinionToken token = new MinionToken();
                    token.putOntoBattlefield(cmc, game, id, player.getId());
                }
            }
        }
        return true;
    }

    @Override
    public InfernalGenesisEffect copy() {
        return new InfernalGenesisEffect(this);
    }
}

class MinionToken extends Token {

    public MinionToken() {
        super("Minion", "1/1 black Minion creature token");
        color = ObjectColor.BLACK;
        cardType.add(CardType.CREATURE);
        this.subtype.add("Minion");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
