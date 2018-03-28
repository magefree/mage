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
package mage.cards.s;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class SongOfBlood extends CardImpl {

    public SongOfBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Put the top four cards of your library into your graveyard.
        // Whenever a creature attacks this turn, it gets +1/+0 until end of turn for each creature card put into your graveyard this way.
        this.getSpellAbility().addEffect(new SongOfBloodEffect());
    }

    public SongOfBlood(final SongOfBlood card) {
        super(card);
    }

    @Override
    public SongOfBlood copy() {
        return new SongOfBlood(this);
    }
}

class SongOfBloodEffect extends OneShotEffect {

    public SongOfBloodEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Put the top four cards of your library into your graveyard.";

    }

    public SongOfBloodEffect(final SongOfBloodEffect effect) {
        super(effect);
    }

    @Override
    public SongOfBloodEffect copy() {
        return new SongOfBloodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToGraveyard = new CardsImpl();
            cardsToGraveyard.addAll(controller.getLibrary().getTopCards(game, 4));
            if (!cardsToGraveyard.isEmpty()) {
                Set<Card> movedCards = controller.moveCardsToGraveyardWithInfo(cardsToGraveyard.getCards(game), source, game, Zone.LIBRARY);
                Cards cardsMoved = new CardsImpl();
                cardsMoved.addAll(movedCards);
                int creatures = cardsMoved.count(new FilterCreatureCard(), game);
                if (creatures > 0) {
                    // Setup a delayed trigger to give +X/+0 to any creature attacking this turn..
                    DelayedTriggeredAbility delayedAbility = new SongOfBloodTriggeredAbility(creatures);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
            }
            return true;
        }
        return false;
    }
}

class SongOfBloodTriggeredAbility extends DelayedTriggeredAbility {

    int booster;

    public SongOfBloodTriggeredAbility(int booster) {
        super(new BoostTargetEffect(booster, 0, Duration.EndOfTurn), Duration.EndOfTurn, false);
        this.booster = booster;
    }

    public SongOfBloodTriggeredAbility(SongOfBloodTriggeredAbility ability) {
        super(ability);
        this.booster = ability.booster;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null) {
            for (Effect effect : getEffects()) {
                effect.setTargetPointer(new FixedTarget(permanent, game));
            }
            return true;
        }
        return false;
    }

    @Override
    public SongOfBloodTriggeredAbility copy() {
        return new SongOfBloodTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks this turn, it gets +1/+0 (+" + booster + "/0) until end of turn for each creature card put into your graveyard this way.";
    }
}
