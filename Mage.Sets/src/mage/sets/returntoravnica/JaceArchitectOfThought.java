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
package mage.sets.returntoravnica;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.Step;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;


/**
 *
 *
 * @author LevelX2
 */
public class JaceArchitectOfThought extends CardImpl<JaceArchitectOfThought> {

    public JaceArchitectOfThought(UUID ownerId) {
        super(ownerId, 44, "Jace, Architect of Thought", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Jace");

        this.color.setBlue(true);

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), false));

        // +1: Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.
        this.addAbility(new LoyaltyAbility(new JaceArchitectOfThoughtGainAbilityEffect(new JaceArchitectOfThoughtTriggeredAbility()),1));

        // -2: Reveal the top three cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new JaceArchitectOfThoughtEffect2(), -2));

        // -8: For each player, search that player's library for a nonland card and exile it, then that player shuffles his or her library. You may cast those cards without paying their mana costs.
        this.addAbility(new LoyaltyAbility(new JaceArchitectOfThoughtEffect3(), -8));


    }

    public JaceArchitectOfThought(final JaceArchitectOfThought card) {
        super(card);
    }

    @Override
    public JaceArchitectOfThought copy() {
        return new JaceArchitectOfThought(this);
    }
}

class JaceArchitectOfThoughtGainAbilityEffect extends ContinuousEffectImpl<JaceArchitectOfThoughtGainAbilityEffect> {

    protected Ability ability;

    public JaceArchitectOfThoughtGainAbilityEffect(Ability ability) {
        super(Duration.Custom, Constants.Layer.AbilityAddingRemovingEffects_6, Constants.SubLayer.NA, Constants.Outcome.AddAbility);
        this.ability = ability;
        staticText = "Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn";
    }

    public JaceArchitectOfThoughtGainAbilityEffect(final JaceArchitectOfThoughtGainAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public JaceArchitectOfThoughtGainAbilityEffect copy() {
        return new JaceArchitectOfThoughtGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addAbility(ability, game);
            return true;
        }
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == Constants.PhaseStep.UNTAP && game.getStep().getStepPart() == Step.StepPart.PRE)
        {
            if (game.getActivePlayerId().equals(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }
}

class JaceArchitectOfThoughtTriggeredAbility extends TriggeredAbilityImpl<JaceArchitectOfThoughtTriggeredAbility> {

    public JaceArchitectOfThoughtTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new JaceArchitectOfThoughtEffectUnboostEffect(-1,0, Duration.EndOfTurn));
    }

    public JaceArchitectOfThoughtTriggeredAbility(final JaceArchitectOfThoughtTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JaceArchitectOfThoughtTriggeredAbility copy() {
        return new JaceArchitectOfThoughtTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            Permanent attacker = game.getPermanent(event.getSourceId());
            Player defender = game.getPlayer(event.getTargetId());
            Player you = game.getPlayer(controllerId);
            if (attacker.getControllerId() != you.getId()
                  && defender == you) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(attacker.getId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.";
    }
}

/*
 * effect only applies if creature continues to attack (if it's left the combat, it#s no longer attacking
 */
class JaceArchitectOfThoughtEffectUnboostEffect extends BoostTargetEffect {

    public JaceArchitectOfThoughtEffectUnboostEffect(int power, int toughness, Duration duration) {
        super(power, toughness, duration);
    }


    public JaceArchitectOfThoughtEffectUnboostEffect (final  JaceArchitectOfThoughtEffectUnboostEffect effect) {
        super(effect);
    }

    @Override
    public  JaceArchitectOfThoughtEffectUnboostEffect copy() {
        return new JaceArchitectOfThoughtEffectUnboostEffect(this);
    }


    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent target = (Permanent) game.getPermanent(permanentId);
            if (target != null && target.isAttacking()) {
                return super.apply(game, source);
            }
        }
        return false;
    }
}

class JaceArchitectOfThoughtEffect2 extends OneShotEffect<JaceArchitectOfThoughtEffect2> {

    public JaceArchitectOfThoughtEffect2() {
        super(Constants.Outcome.DrawCard);
        this.staticText = "Reveal the top three cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other on the bottom of your library in any order";
    }

    public JaceArchitectOfThoughtEffect2(final JaceArchitectOfThoughtEffect2 effect) {
        super(effect);
    }

    @Override
    public JaceArchitectOfThoughtEffect2 copy() {
        return new JaceArchitectOfThoughtEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(Zone.PICK);
        int count = Math.min(player.getLibrary().size(), 3);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
            }
        }
        player.revealCards("Jace, Architect of Thought", cards, game);

        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (!opponents.isEmpty()) {
            Player opponent = null;
            if (opponents.size() > 1) {
                TargetOpponent targetOpponent = new TargetOpponent();
                if (player.chooseTarget(Outcome.Neutral, targetOpponent, source, game)) {
                    opponent = game.getPlayer(targetOpponent.getFirstTarget());
                }
            }
            if (opponent == null)  {
                opponent = game.getPlayer(opponents.iterator().next());
            }
            
            TargetCard target = new TargetCard(0, cards.size(), Zone.PICK, new FilterCard("cards to put in the first pile"));

            Cards pile1 = new CardsImpl();
            if (opponent.choose(Constants.Outcome.Neutral, cards, target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = cards.get(targetId, game);
                    if (card != null) {
                        pile1.add(card);
                        cards.remove(card);
                    }
                }
            }
            player.revealCards("Pile 1 (Jace, Architect of Thought)", pile1, game);
            player.revealCards("Pile 2 (Jace, Architect of Thought)", cards, game);

            Cards cardsToHand = cards;
            Cards cardsToLibrary = pile1;
            List<Card> cardPile1 = new ArrayList<Card>();
            List<Card> cardPile2 = new ArrayList<Card>();
            for (UUID cardId: pile1) {
                cardPile1.add(game.getCard(cardId));
            }
            for (UUID cardId: cards) {
                cardPile2.add(game.getCard(cardId));
            }

            boolean pileChoice = player.choosePile(Outcome.Neutral, "Choose a pile to to put into your hand.", cardPile1, cardPile2, game);
            if (pileChoice){

                cardsToHand = pile1;
                cardsToLibrary = cards;
            }
            game.informPlayers(player.getName() +" chose pile" + (pileChoice?"1":"2"));

            for (UUID cardUuid : cardsToHand) {
                Card card = cardsToHand.get(cardUuid, game);
                if (card != null) {
                    card.moveToZone(Zone.HAND, source.getId(), game, false);
                }
            }

            TargetCard targetCard = new TargetCard(Zone.PICK, new FilterCard("card to put on the bottom of your library"));
            targetCard.setRequired(true);
            while (cardsToLibrary.size() > 1) {
                player.choose(Constants.Outcome.Neutral, cardsToLibrary, targetCard, game);
                Card card = cardsToLibrary.get(targetCard.getFirstTarget(), game);
                if (card != null) {
                    cardsToLibrary.remove(card);
                    card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
                }
                target.clearChosen();
            }
            if (cardsToLibrary.size() == 1) {
                Card card = cardsToLibrary.get(cardsToLibrary.iterator().next(), game);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
            }
            return true;
        }
        return false;
    }
}

class JaceArchitectOfThoughtEffect3 extends OneShotEffect<JaceArchitectOfThoughtEffect3> {

    public JaceArchitectOfThoughtEffect3() {
        super(Outcome.PlayForFree);
        this.staticText = "For each player, search that player's library for a nonland card and exile it, then that player shuffles his or her library. You may cast those cards without paying their mana costs";
    }

    public JaceArchitectOfThoughtEffect3(final JaceArchitectOfThoughtEffect3 effect) {
        super(effect);
    }

    @Override
    public JaceArchitectOfThoughtEffect3 copy() {
        return new JaceArchitectOfThoughtEffect3(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        for (UUID playerId : controller.getInRange()) {
            Cards playerLibrary = new CardsImpl();
            Player player = game.getPlayer(playerId);
            playerLibrary.addAll(player.getLibrary().getCardList());

            TargetCardInLibrary target = new TargetCardInLibrary(new FilterNonlandCard());
            if (controller.choose(Constants.Outcome.Benefit, playerLibrary, target, game)) {
                UUID targetId = target.getFirstTarget();
                Card card = player.getLibrary().remove(targetId, game);
                if (card != null) {
                    card.moveToExile(source.getSourceId(), "Jace, Architect of Thought", source.getSourceId(), game);
                }
            }
            player.shuffleLibrary(game);
        }

        ExileZone JaceExileZone = game.getExile().getExileZone(source.getSourceId());
        FilterCard filter = new FilterCard("card to cast without mana costs");
        TargetCardInExile target = new TargetCardInExile(filter, source.getSourceId());
        while (JaceExileZone.count(filter, game) > 0 && controller.choose(Outcome.PlayForFree, JaceExileZone, target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {

                    if (controller.cast(card.getSpellAbility(), game, true))
                    {
                        game.getExile().removeCard(card, game);
                    }
                }
                target.clearChosen();
        }

        return true;
    }
}

