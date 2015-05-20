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
package mage.sets.thedark;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RevealHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Quercitron
 */
public class RagMan extends CardImpl {

    public RagMan(UUID ownerId) {
        super(ownerId, 13, "Rag Man", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "DRK";
        this.subtype.add("Human");
        this.subtype.add("Minion");

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {B}{B}{B}, {tap}: Target opponent reveals his or her hand and discards a creature card at random. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new RevealHandTargetEffect(), new ManaCostsImpl("{B}{B}{B}"), MyTurnCondition.getInstance());
        ability.addCost(new TapSourceCost());
        ability.addEffect(new RagManDiscardEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public RagMan(final RagMan card) {
        super(card);
    }

    @Override
    public RagMan copy() {
        return new RagMan(this);
    }
}

class RagManDiscardEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard();
    
    public RagManDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "and discards a creature card at random";
    }
    
    public RagManDiscardEffect(final RagManDiscardEffect effect) {
        super(effect);
    }

    @Override
    public RagManDiscardEffect copy() {
        return new RagManDiscardEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            Cards creatureCardsInHand = new CardsImpl(Zone.PICK);
            for (UUID cardId : player.getHand()) {
                Card card = player.getHand().get(cardId, game);
                if (filter.match(card, game)) {
                    creatureCardsInHand.add(card);
                }
            }
            
            if (!creatureCardsInHand.isEmpty()) {
                Card card = creatureCardsInHand.getRandom(game);
                if (card != null) {
                    player.discard(card, source, game);
                }
            }
            
            return true;
        }
        return false;
    }
    
}
