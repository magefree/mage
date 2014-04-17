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
package mage.sets.journeyintonyx;

import java.util.LinkedList;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class BrainMaggot extends CardImpl<BrainMaggot> {

    public BrainMaggot(UUID ownerId) {
        super(ownerId, 62, "Brain Maggot", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Insect");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Brain Maggot enters the battlefield, target opponent reveals his or her hand and you choose a nonland card from it. Exile that card until Brain Maggot leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BrainMaggotExileEffect());
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(ability);    
        // Implemented as triggered effect that doesn't uses the stack (implementation with watcher does not work correctly because if the returned creature
        // has a DiesTriggeredAll ability it triggers for the dying / battlefield leaving source object, what shouldn't happen)
        this.addAbility(new BrainMaggotReturnExiledAbility());           
    }

    public BrainMaggot(final BrainMaggot card) {
        super(card);
    }

    @Override
    public BrainMaggot copy() {
        return new BrainMaggot(this);
    }
}

class BrainMaggotExileEffect extends OneShotEffect<BrainMaggotExileEffect> {

    public BrainMaggotExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "target opponent reveals his or her hand and you choose a nonland card from it. Exile that card until {this} leaves the battlefield";
    }

    public BrainMaggotExileEffect(final BrainMaggotExileEffect effect) {
        super(effect);
    }

    @Override
    public BrainMaggotExileEffect copy() {
        return new BrainMaggotExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && opponent != null && sourcePermanent != null) {
            opponent.revealCards(sourcePermanent.getName(), opponent.getHand(), game);

            TargetCard target = new TargetCard(Zone.HAND, new FilterNonlandCard("nonland card to exile"));
            target.setRequired(true);
            if (controller.choose(Outcome.Exile, opponent.getHand(), target, game)) {
                Card card = opponent.getHand().get(target.getFirstTarget(), game);
                // If source permanent leaves the battlefield before its triggered ability resolves, the target card won't be exiled.
                if (card != null && game.getState().getZone(source.getSourceId()) == Zone.BATTLEFIELD) {
                    controller.moveCardToExileWithInfo(card, CardUtil.getCardExileZoneId(game, source), sourcePermanent.getName(), source.getSourceId(), game, Zone.HAND);
                }
            }            
        }
        return false;

    }
}

/**
 * Returns the exiled card as source permanent leaves battlefield
 * Uses no stack
 * @author LevelX2
 */

class BrainMaggotReturnExiledAbility extends TriggeredAbilityImpl<BrainMaggotReturnExiledAbility> {

    public BrainMaggotReturnExiledAbility() {
        super(Zone.BATTLEFIELD, new BrainMaggotReturnExiledCreatureEffect());
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    public BrainMaggotReturnExiledAbility(final BrainMaggotReturnExiledAbility ability) {
        super(ability);
    }

    @Override
    public BrainMaggotReturnExiledAbility copy() {
        return new BrainMaggotReturnExiledAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                return true;
            }
        }
        return false;
    }
}

class BrainMaggotReturnExiledCreatureEffect extends OneShotEffect<BrainMaggotReturnExiledCreatureEffect> {

    public BrainMaggotReturnExiledCreatureEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return exiled nonland card to its owner's hand";
    }

    public BrainMaggotReturnExiledCreatureEffect(final BrainMaggotReturnExiledCreatureEffect effect) {
        super(effect);
    }

    @Override
    public BrainMaggotReturnExiledCreatureEffect copy() {
        return new BrainMaggotReturnExiledCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exile = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (exile != null && sourcePermanent != null) {
                LinkedList<UUID> cards = new LinkedList<>(exile);
                for (UUID cardId : cards) {
                    Card card = game.getCard(cardId);
                    controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.EXILED);
                }
                exile.clear();
                return true;
            }            
        }
        return false;
    }
}
