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
package mage.sets.scourge;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class RavenGuildMaster extends CardImpl {

    public RavenGuildMaster(UUID ownerId) {
        super(ownerId, 47, "Raven Guild Master", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "SCG";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.subtype.add("Mutant");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Raven Guild Master deals combat damage to a player, that player exiles the top ten cards of his or her library.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(new RavenGuildMasterEffect(), false, true));
        
        // Morph {2}{U}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{2}{U}{U}")));
    }

    public RavenGuildMaster(final RavenGuildMaster card) {
        super(card);
    }

    @Override
    public RavenGuildMaster copy() {
        return new RavenGuildMaster(this);
    }
}

class RavenGuildMasterEffect extends OneShotEffect {

    public RavenGuildMasterEffect() {
        super(Outcome.Exile);
        this.staticText = "that player exiles the top ten cards of his or her library";
    }

    public RavenGuildMasterEffect(final RavenGuildMasterEffect effect) {
        super(effect);
    }

    @Override
    public RavenGuildMasterEffect copy() {
        return new RavenGuildMasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int count = Math.min(player.getLibrary().size(), 10);
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    card.moveToExile(id, "", source.getSourceId(), game);
                }
            }
        return true;
        }
    return false;
    }
}