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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.PreventDamageBySourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.filter.FilterObject;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.players.Player;
import mage.target.TargetSource;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public class DesperateGambit extends CardImpl {

    public DesperateGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Choose a source you control and flip a coin. If you win the flip, the next time that source would deal damage this turn, it deals double that damage instead. If you lose the flip, the next time it would deal damage this turn, prevent that damage.
        this.getSpellAbility().addEffect(new DesperateGambitEffect());
    }

    public DesperateGambit(final DesperateGambit card) {
        super(card);
    }

    @Override
    public DesperateGambit copy() {
        return new DesperateGambit(this);
    }
}

class DesperateGambitEffect extends PreventionEffectImpl {
    
    private final TargetSource target;
    private boolean wonFlip;

    public DesperateGambitEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "Choose a source you control and flip a coin. If you win the flip, the next time that source would deal damage this turn, it deals double that damage instead. If you lose the flip, the next time it would deal damage this turn, prevent that damage";
        this.target = new TargetControlledSource();
    }

    public DesperateGambitEffect(final DesperateGambitEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.Benefit, source.getControllerId(), source.getSourceId(), game);
        this.wonFlip = game.getPlayer(source.getControllerId()).flipCoin(game);
        super.init(source, game);
    }

    @Override
    public DesperateGambitEffect copy() {
        return new DesperateGambitEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CREATURE ||
                event.getType() == GameEvent.EventType.DAMAGE_PLANESWALKER ||
                event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(target.getFirstTarget())) {
            // check source
            MageObject object = game.getObject(event.getSourceId());
            if (object == null) {
                game.informPlayers("Couldn't find source of damage");
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && object != null) {
            if (super.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0) {
                if (wonFlip) {
                    event.setAmount(CardUtil.addWithOverflowCheck(event.getAmount(), event.getAmount()));
                    this.discard();
                } else {
                    preventDamageAction(event, source, game);
                    this.discard();
                    return true;
                }
            }
        }
        return false;
    }
}

class TargetControlledSource extends TargetSource {

    public TargetControlledSource() {
        super(1, 1, new FilterObject("source you control"));
    }

    public TargetControlledSource(final TargetControlledSource target) {
        super(target);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        int count = 0;
        for (StackObject stackObject: game.getStack()) {
            if (game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getControllerId()) 
                && stackObject.getControllerId() == sourceControllerId) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Permanent permanent: game.getBattlefield().getActivePermanents(sourceControllerId, game)) {
            if (permanent.getControllerId() == sourceControllerId) {
                count++;
                if (count >= this.minNumberOfTargets) {
                    return true;
                }
            }
        }
        for (Player player : game.getPlayers().values()) {
            if (player == game.getPlayer(sourceControllerId)) {
                for (Card card : player.getGraveyard().getCards(game)) {
                    count++;
                    if (count >= this.minNumberOfTargets) {
                        return true;
                    }
                }
                // 108.4a If anything asks for the controller of a card that doesn’t have one (because it’s not a permanent or spell), use its owner instead.
                for (Card card : game.getExile().getAllCards(game)) {
                    if (card.getOwnerId() == sourceControllerId) {
                        count++;
                        if (count >= this.minNumberOfTargets) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (StackObject stackObject: game.getStack()) {
            if (game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getControllerId()) 
                && stackObject.getControllerId() == sourceControllerId) {
                possibleTargets.add(stackObject.getId());
            }
        }
        for (Permanent permanent: game.getBattlefield().getActivePermanents(sourceControllerId, game)) {
            if (permanent.getControllerId() == sourceControllerId) {
                possibleTargets.add(permanent.getId());
            }
        }
        for (Player player : game.getPlayers().values()) {
            if (player == game.getPlayer(sourceControllerId)) {
                for (Card card : player.getGraveyard().getCards(game)) {
                    possibleTargets.add(card.getId());
                }
                // 108.4a If anything asks for the controller of a card that doesn’t have one (because it’s not a permanent or spell), use its owner instead.
                for (Card card : game.getExile().getAllCards(game)) {
                    if (card.getOwnerId() == sourceControllerId) {
                        possibleTargets.add(card.getId());
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetControlledSource copy() {
        return new TargetControlledSource(this);
    }
}
