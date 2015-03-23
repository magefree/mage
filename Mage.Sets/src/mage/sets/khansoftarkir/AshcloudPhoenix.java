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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class AshcloudPhoenix extends CardImpl {

    public AshcloudPhoenix(UUID ownerId) {
        super(ownerId, 99, "Ashcloud Phoenix", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "KTK";
        this.subtype.add("Phoenix");

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Ashcloud Phoenix dies, return it to the battlefield face down under your control.
        this.addAbility(new DiesTriggeredAbility(new AshcloudPhoenixEffect()));
        
        // Morph {4}{R}{R}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{4}{R}{R}")));
        
        // When Ashcloud Phoenix is turned face up, it deals 2 damage to each player.
        Effect effect = new DamagePlayersEffect(2, TargetController.ANY);
        effect.setText("it deals 2 damage to each player");
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(effect));
    }

    public AshcloudPhoenix(final AshcloudPhoenix card) {
        super(card);
    }

    @Override
    public AshcloudPhoenix copy() {
        return new AshcloudPhoenix(this);
    }
}

class AshcloudPhoenixEffect extends OneShotEffect {
    
    AshcloudPhoenixEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return it to the battlefield face down under your control";
    }
    
    AshcloudPhoenixEffect(final AshcloudPhoenixEffect effect) {
        super(effect);
    }
    
    @Override
    public AshcloudPhoenixEffect copy() {
        return new AshcloudPhoenixEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                Player owner = game.getPlayer(card.getOwnerId());
                if (owner != null && owner.getGraveyard().contains(card.getId())) {
                    player.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId(), false, true);
                }
            }
            return true;
        }
        return false;
    }
}
