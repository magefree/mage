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
package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class DrafnasRestoration extends CardImpl {

    public DrafnasRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");

        // Return any number of target artifact cards from target player's graveyard to the top of his or her library in any order.
        this.getSpellAbility().addEffect(new DrafnasRestorationEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new DrafnasRestorationTarget());
    }

    public DrafnasRestoration(final DrafnasRestoration card) {
        super(card);
    }

    @Override
    public DrafnasRestoration copy() {
        return new DrafnasRestoration(this);
    }
}

class DrafnasRestorationTarget extends TargetCardInGraveyard {

    DrafnasRestorationTarget() {
        super(0, Integer.MAX_VALUE, new FilterArtifactCard("any number of artifact cards from that player's graveyard"));
    }

    DrafnasRestorationTarget(final DrafnasRestorationTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        return targetPlayer != null && targetPlayer.getGraveyard().contains(id) && super.canTarget(id, source, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject object = game.getObject(sourceId);
        if (object != null && object instanceof StackObject) {
            Player targetPlayer = game.getPlayer(((StackObject) object).getStackAbility().getFirstTarget());
            if (targetPlayer != null) {
                for (Card card : targetPlayer.getGraveyard().getCards(filter, sourceId, sourceControllerId, game)) {
                    if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, card.getId(), sourceId, sourceControllerId))) {
                        possibleTargets.add(card.getId());
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public DrafnasRestorationTarget copy() {
        return new DrafnasRestorationTarget(this);
    }
}

class DrafnasRestorationEffect extends OneShotEffect {
    
    DrafnasRestorationEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return any number of target artifact cards from target player's graveyard to the top of his or her library in any order";
    }
    
    DrafnasRestorationEffect(final DrafnasRestorationEffect effect) {
        super(effect);
    }
    
    @Override
    public DrafnasRestorationEffect copy() {
        return new DrafnasRestorationEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(source.getTargets().get(1).getTargets());
            controller.putCardsOnTopOfLibrary(cards, game, source, true);
            return true;
        }
        return false;
    }
}
