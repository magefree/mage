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
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author MarcoMarin
 */
public class JandorsRing extends CardImpl {

    public JandorsRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        Watcher watcher = new JandorsRingWatcher();
        // {2}, {tap}, Discard the last card you drew this turn: Draw a card.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new DiscardAndDrawEffect(), new ManaCostsImpl("{2}"), new WatchedCardInHandCondition(), "Last drawn card still in hand?");
        ability.addCost(new TapSourceCost());                
        this.addAbility(ability, watcher);
    }

    public JandorsRing(final JandorsRing card) {
        super(card);
    }

    @Override
    public JandorsRing copy() {
        return new JandorsRing(this);
    }
}

class DiscardAndDrawEffect extends OneShotEffect{
    
    
    public DiscardAndDrawEffect() {
        super(Outcome.Discard);
    }
    
    public DiscardAndDrawEffect(final DiscardAndDrawEffect effect) {
        super(effect);
    }
    
    @Override
    public DiscardAndDrawEffect copy() {
        return new DiscardAndDrawEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        JandorsRingWatcher watcher = (JandorsRingWatcher) game.getState().getWatchers().get("JandorsRingWatcher");        
        
        FilterCard filter = new FilterCard(game.getCard(watcher.lastDrawnCard).getName());
        filter.add(new CardIdPredicate(watcher.lastDrawnCard));
        DiscardCardYouChooseTargetEffect effect = new DiscardCardYouChooseTargetEffect(filter, TargetController.YOU);
        
        if (effect.apply(game, source)){//Conditional was already checked, card should be in hand, but if for some weird reason it fails, the card won't be drawn, although the cost will already be paid            
            Player controller = game.getPlayer(source.getControllerId());
            controller.drawCards(1, game);
            return true;
        }
        return false;
    }
}


class JandorsRingWatcher extends Watcher {

    UUID lastDrawnCard;

    public JandorsRingWatcher() {
        super("JandorsRingWatcher", WatcherScope.PLAYER);
        this.lastDrawnCard = null;
    }

    public JandorsRingWatcher(final JandorsRingWatcher watcher) {
        super(watcher);        
        this.lastDrawnCard = watcher.lastDrawnCard;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {            
            lastDrawnCard = event.getTargetId();
        }
        if (event.getType() == GameEvent.EventType.CLEANUP_STEP_POST) {            
            lastDrawnCard = null;
        }
    }

    @Override
    public JandorsRingWatcher copy() {
        return new JandorsRingWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        lastDrawnCard = null;
    }
}

class WatchedCardInHandCondition implements Condition {

    private static final WatchedCardInHandCondition fInstance = new WatchedCardInHandCondition();

    public static WatchedCardInHandCondition getInstance() {
        return fInstance;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        JandorsRingWatcher watcher = (JandorsRingWatcher) game.getState().getWatchers().get("JandorsRingWatcher");        
        
        return watcher.lastDrawnCard!=null && game.getPlayer(source.getControllerId()).getHand().contains(watcher.lastDrawnCard);
    }

    @Override
    public String toString() {
        return "if last drawn card is still in hand";
    }

    
}