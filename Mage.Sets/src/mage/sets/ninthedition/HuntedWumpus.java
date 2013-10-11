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
package mage.sets.ninthedition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class HuntedWumpus extends CardImpl<HuntedWumpus> {

    public HuntedWumpus(UUID ownerId) {
        super(ownerId, 248, "Hunted Wumpus", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "9ED";
        this.subtype.add("Beast");

        this.color.setGreen(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Hunted Wumpus enters the battlefield, each other player may put a creature card from his or her hand onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HuntedWumpusEffect(), false));

    }

    public HuntedWumpus(final HuntedWumpus card) {
        super(card);
    }

    @Override
    public HuntedWumpus copy() {
        return new HuntedWumpus(this);
    }
}

class HuntedWumpusEffect extends OneShotEffect<HuntedWumpusEffect> {

    public HuntedWumpusEffect() {
        super(Outcome.Detriment);
        this.staticText = "each other player may put a creature card from his or her hand onto the battlefield";
    }

    public HuntedWumpusEffect(final HuntedWumpusEffect effect) {
        super(effect);
    }

    @Override
    public HuntedWumpusEffect copy() {
        return new HuntedWumpusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for(UUID playerId: controller.getInRange()) {
                if (!playerId.equals(controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        TargetCardInHand target = new TargetCardInHand(new FilterCreatureCard());
                        if (target.canChoose(source.getSourceId(), playerId, game)
                                && player.chooseUse(Outcome.Neutral, "Put a creature card from your hand in play?", game)
                                && player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                card.putOntoBattlefield(game, Zone.HAND, source.getId(), player.getId());
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
