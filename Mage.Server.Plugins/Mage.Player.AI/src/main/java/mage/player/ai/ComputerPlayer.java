/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.player.ai;


import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.RangeOfInfluence;
import mage.Constants.Zone;
import mage.MageObject;
import mage.Mana;
import mage.abilities.*;
import mage.abilities.costs.mana.*;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continious.BecomesCreatureSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.ManaAbility;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.filter.FilterPermanent;
import mage.filter.common.*;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.draft.Draft;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.match.Match;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.game.tournament.Tournament;
import mage.player.ai.simulators.CombatGroupSimulator;
import mage.player.ai.simulators.CombatSimulator;
import mage.player.ai.simulators.CreatureSimulator;
import mage.player.ai.utils.RateCard;
import mage.players.Player;
import mage.players.PlayerImpl;
import mage.players.net.UserData;
import mage.players.net.UserGroup;
import mage.target.*;
import mage.target.common.*;
import mage.util.Copier;
import mage.util.TreeNode;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;
import mage.cards.Sets;
import mage.cards.repository.CardCriteria;

/**
 *
 * suitable for two player games and some multiplayer games
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ComputerPlayer<T extends ComputerPlayer<T>> extends PlayerImpl<T> implements Player {

    private transient final static Logger log = Logger.getLogger(ComputerPlayer.class);
    private transient Map<Mana, Card> unplayable = new TreeMap<Mana, Card>();
    private transient List<Card> playableNonInstant = new ArrayList<Card>();
    private transient List<Card> playableInstant = new ArrayList<Card>();
    private transient List<ActivatedAbility> playableAbilities = new ArrayList<ActivatedAbility>();
    private transient List<PickedCard> pickedCards;
    private transient List<Constants.ColoredManaSymbol> chosenColors;

    public ComputerPlayer(String name, RangeOfInfluence range) {
        super(name, range);
        human = false;
        userData = new UserData(UserGroup.COMPUTER, 64);
    }

    protected ComputerPlayer(UUID id) {
        super(id);
    }

    public ComputerPlayer(final ComputerPlayer player) {
        super(player);
    }

    @Override
    public boolean chooseMulligan(Game game) {
        log.debug("chooseMulligan");
        if (hand.size() < 6 || isTestMode())
            return false;
        Set<Card> lands = hand.getCards(new FilterLandCard(), game);
        if (lands.size() < 2 || lands.size() > hand.size() - 2)
            return true;
        return false;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
        return choose(outcome, target, sourceId, game, null);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options) {
        if (log.isDebugEnabled())
            log.debug("chooseTarget: " + outcome.toString() + ":" + target.toString());
        UUID opponentId = game.getOpponents(playerId).iterator().next();
        if (target instanceof TargetPlayer) {
            if (outcome.isGood()) {
                if (target.canTarget(playerId, game)) {
                    target.add(playerId, game);
                    return true;
                }
                if (target.isRequired()) {
                    if (target.canTarget(opponentId, game)) {
                        target.add(opponentId, game);
                        return true;
                    }
                }
            } else {
                if (target.canTarget(opponentId, game)) {
                    target.add(opponentId, game);
                    return true;
                }
                if (target.isRequired()) {
                    if (target.canTarget(playerId, game)) {
                        target.add(playerId, game);
                        return true;
                    }
                }
            }
            return false;
        }
        if (target instanceof TargetDiscard) {
            findPlayables(game);
            if (unplayable.size() > 0) {
                for (int i = unplayable.size() - 1; i >= 0; i--) {
                    if (target.canTarget(unplayable.values().toArray(new Card[0])[i].getId(), game)) {
                        target.add(unplayable.values().toArray(new Card[0])[i].getId(), game);
                        return true;
                    }
                }
            }
            if (hand.size() > 0) {
                for (int i = 0; i < hand.size(); i++) {
                    if (target.canTarget(hand.toArray(new UUID[0])[i], game)) {
                        target.add(hand.toArray(new UUID[0])[i], game);
                        return true;
                    }
                }
            }
            return false;
        }
        if (target instanceof TargetControlledPermanent) {
            List<Permanent> targets;
            targets = threats(playerId, sourceId, ((TargetControlledPermanent) target).getFilter(), game, target.getTargets());
            if (!outcome.isGood())
                Collections.reverse(targets);
            for (Permanent permanent : targets) {
                if (((TargetControlledPermanent) target).canTarget(playerId, permanent.getId(), null, game) && !target.getTargets().contains(permanent.getId())) {
                    target.add(permanent.getId(), game);
                    return true;
                }
            }
        }
        if (target instanceof TargetPermanent) {
            List<Permanent> targets;
            if (outcome.isCanTargetAll()) {
                targets = threats(null, sourceId, ((TargetPermanent) target).getFilter(), game, target.getTargets());
            } else {
                if (outcome.isGood()) {
                    targets = threats(playerId, sourceId, ((TargetPermanent) target).getFilter(), game, target.getTargets());
                } else {
                    targets = threats(opponentId, sourceId, ((TargetPermanent) target).getFilter(), game, target.getTargets());
                }
            }
            for (Permanent permanent : targets) {
                if (((TargetPermanent) target).canTarget(playerId, permanent.getId(), null, game) && !target.getTargets().contains(permanent.getId())) {
                    target.add(permanent.getId(), game);
                    return true;
                }
            }
        }
        if (target instanceof TargetCardInHand) {
            List<Card> cards = new ArrayList<Card>();
            cards.addAll(this.hand.getCards(game));
            while(!target.isChosen() && !cards.isEmpty()) {
                Card pick = pickTarget(cards, outcome, target, null, game);
                if (pick != null) {
                    target.addTarget(pick.getId(), null, game);
                }
            }
            return target.isChosen();
        }
        if (target instanceof TargetCreatureOrPlayer) {
            List<Permanent> targets;
            TargetCreatureOrPlayer t = ((TargetCreatureOrPlayer) target);
            if (outcome.isGood()) {
                targets = threats(playerId, sourceId, ((FilterCreatureOrPlayer) t.getFilter()).getCreatureFilter(), game, target.getTargets());
            } else {
                targets = threats(opponentId, sourceId, ((FilterCreatureOrPlayer) t.getFilter()).getCreatureFilter(), game, target.getTargets());
            }
            for (Permanent permanent : targets) {
                List<UUID> alreadyTargetted = target.getTargets();
                if (t.canTarget(playerId, permanent.getId(), null, game)) {
                    if (alreadyTargetted != null && !alreadyTargetted.contains(permanent.getId())) {
                        target.add(permanent.getId(), game);
                        return true;
                    }
                }
            }
            if (outcome.isGood()) {
                if (target.canTarget(playerId, null, game)) {
                    target.add(playerId, game);
                    return true;
                }
            } else {
                if (target.canTarget(opponentId, null, game)) {
                    target.add(opponentId, game);
                    return true;
                }
            }
            if (!target.isRequired())
                return false;
        }
        if (target instanceof TargetPermanentOrPlayer) {
            List<Permanent> targets;
            TargetPermanentOrPlayer t = ((TargetPermanentOrPlayer) target);
            if (outcome.isGood()) {
                targets = threats(playerId, sourceId, ((FilterPermanentOrPlayer) t.getFilter()).getPermanentFilter(), game, target.getTargets());
            } else {
                targets = threats(opponentId, sourceId, ((FilterPermanentOrPlayer) t.getFilter()).getPermanentFilter(), game, target.getTargets());
            }
            for (Permanent permanent : targets) {
                List<UUID> alreadyTargetted = target.getTargets();
                if (t.canTarget(permanent.getId(), game)) {
                    if (alreadyTargetted != null && !alreadyTargetted.contains(permanent.getId())) {
                        target.add(permanent.getId(), game);
                        return true;
                    }
                }
            }
            if (outcome.isGood()) {
                if (target.canTarget(playerId, null, game)) {
                    target.add(playerId, game);
                    return true;
                }
            } else {
                if (target.canTarget(opponentId, null, game)) {
                    target.add(opponentId, game);
                    return true;
                }
            }
            if (!target.isRequired())
                return false;
        }
        if (target instanceof TargetCardInGraveyard) {
            List<Card> cards = new ArrayList<Card>();
            for (Player player: game.getPlayers().values()) {
                for (Card card: player.getGraveyard().getCards(game)) {
                    if (target.canTarget(card.getId(), game)) {
                        cards.add(card);
                    }
                }
            }
            for (Card card: cards) {
                target.add(card.getId(), game);
                if (target.isChosen()) {
                    return true;
                }
            }
            return target.isChosen();
        }

        if (target instanceof TargetCardInYourGraveyard) {
            List<Card> cards = new ArrayList<Card>(game.getPlayer(playerId).getGraveyard().getCards(game));
            while(!target.isChosen() && !cards.isEmpty()) {
                Card card = pickTarget(cards, outcome, target, null, game);
                if (card != null) {
                    target.add(card.getId(), game);
                }
            }
            return target.isChosen();
        }

        throw new IllegalStateException("Target wasn't handled. class:" + target.getClass().toString());
//        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        if (log.isDebugEnabled())
            log.debug("chooseTarget: " + outcome.toString() + ":" + target.toString());
        UUID opponentId = game.getOpponents(playerId).iterator().next();
        if (target instanceof TargetPlayer) {
            if (outcome.isGood()) {
                if (target.canTarget(playerId, source, game)) {
                    target.addTarget(playerId, source, game);
                    return true;
                }
                if (target.isRequired()) {
                    if (target.canTarget(opponentId, source, game)) {
                        target.addTarget(opponentId, source, game);
                        return true;
                    }
                }
            }
            else {
                if (target.canTarget(opponentId, source, game)) {
                    target.addTarget(opponentId, source, game);
                    return true;
                }
                if (target.isRequired()) {
                    if (target.canTarget(playerId, source, game)) {
                        target.addTarget(playerId, source, game);
                        return true;
                    }
                }
            }
            return false;
        }
        if (target instanceof TargetDiscard || target instanceof TargetCardInHand) {
            if (outcome.isGood()) {
                Card card = pickBestCard(new ArrayList<Card>(hand.getCards(game)), null, target, source, game);
                if (card != null) {
                    if (target.canTarget(card.getId(), source, game)) {
                        target.addTarget(card.getId(), source, game);
                        return true;
                    }
                }
            }
            else {
                findPlayables(game);
                if (unplayable.size() > 0) {
                    for (int i = unplayable.size() - 1; i >= 0; i--) {
                        if (target.canTarget(unplayable.values().toArray(new Card[0])[i].getId(), source, game)) {
                            target.addTarget(unplayable.values().toArray(new Card[0])[i].getId(), source, game);
                            return true;
                        }
                    }
                }
                if (hand.size() > 0) {
                    for (int i = 0; i < hand.size(); i++) {
                        if (target.canTarget(hand.toArray(new UUID[0])[i], source, game)) {
                            target.addTarget(hand.toArray(new UUID[0])[i], source, game);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        if (target instanceof TargetControlledPermanent) {
            List<Permanent> targets;
            targets = threats(playerId, source.getSourceId(), ((TargetControlledPermanent)target).getFilter(), game, target.getTargets());
            if (!outcome.isGood())
                Collections.reverse(targets);
            for (Permanent permanent: targets) {
                if (((TargetControlledPermanent)target).canTarget(playerId, permanent.getId(), source, game)) {
                    target.addTarget(permanent.getId(), source, game);
                    return true;
                }
            }
            return false;
        }
        if (target instanceof TargetPermanent) {
            List<Permanent> targets;
            if (outcome.isGood()) {
                targets = threats(playerId, source.getSourceId(), ((TargetPermanent)target).getFilter(), game, target.getTargets());
            }
            else {
                targets = threats(opponentId, source.getSourceId(), ((TargetPermanent)target).getFilter(), game, target.getTargets());
            }
            //targets = threats(null, source.getSourceId(), ((TargetPermanent)target).getFilter(), game, target.getTargets());
            if (targets.isEmpty() && target.isRequired()) {
                targets = game.getBattlefield().getActivePermanents(((TargetPermanent)target).getFilter(), playerId, game);
            }
            for (Permanent permanent: targets) {
                if (((TargetPermanent)target).canTarget(playerId, permanent.getId(), source, game)) {
                    target.addTarget(permanent.getId(), source, game);
                    return true;
                }
            }
            return false;
        }
        if (target instanceof TargetCreatureOrPlayer) {
            List<Permanent> targets;
            TargetCreatureOrPlayer t = ((TargetCreatureOrPlayer)target);
            if (outcome.isGood()) {
                targets = threats(playerId, source.getSourceId(), ((FilterCreatureOrPlayer)t.getFilter()).getCreatureFilter(), game, target.getTargets());
            }
            else {
                targets = threats(opponentId, source.getSourceId(), ((FilterCreatureOrPlayer)t.getFilter()).getCreatureFilter(), game, target.getTargets());
            }

            if (targets.isEmpty()) {
                if (outcome.isGood()) {
                    if (target.canTarget(playerId, source, game)) {
                        target.addTarget(playerId, source, game);
                        return true;
                    }
                }
                else {
                    if (target.canTarget(opponentId, source, game)) {
                        target.addTarget(opponentId, source, game);
                        return true;
                    }
                }
            }

            if (targets.isEmpty() && target.isRequired()) {
                targets = game.getBattlefield().getActivePermanents(((FilterCreatureOrPlayer)t.getFilter()).getCreatureFilter(), playerId, game);
            }
            for (Permanent permanent : targets) {
                List<UUID> alreadyTargetted = target.getTargets();
                if (t.canTarget(playerId, permanent.getId(), source, game)) {
                    if (alreadyTargetted != null && !alreadyTargetted.contains(permanent.getId())) {
                        target.addTarget(permanent.getId(), source, game);
                        return true;
                    }
                }
            }

            if (outcome.isGood()) {
                if (target.canTarget(playerId, source, game)) {
                    target.addTarget(playerId, source, game);
                    return true;
                }
            }
            else {
                if (target.canTarget(opponentId, source, game)) {
                    target.addTarget(opponentId, source, game);
                    return true;
                }
            }

            //if (!target.isRequired())
                return false;
        }
        if (target instanceof TargetCardInGraveyard) {
            List<Card> cards = new ArrayList<Card>();
            for (Player player: game.getPlayers().values()) {
                cards.addAll(player.getGraveyard().getCards(game));
            }
            Card card = pickTarget(cards, outcome, target, source, game);
            if (card != null) {
                target.addTarget(card.getId(), source, game);
                return true;
            }
            //if (!target.isRequired())
                return false;
        }
        if (target instanceof TargetCardInLibrary) {
            List<Card> cards = new ArrayList<Card>(game.getPlayer(playerId).getLibrary().getCards(game));
            Card card = pickTarget(cards, outcome, target, source, game);
            if (card != null) {
                target.addTarget(card.getId(), source, game);
                return true;
            }
            return false;
        }
        if (target instanceof TargetCardInYourGraveyard) {
            List<Card> cards = new ArrayList<Card>(game.getPlayer(playerId).getGraveyard().getCards(game));
            while(!target.isChosen() && !cards.isEmpty()) {
                Card card = pickTarget(cards, outcome, target, source, game);
                if (card != null) {
                    target.addTarget(card.getId(), source, game);
                }
            }
            return target.isChosen();
        }
        if (target instanceof TargetCardInHand) {
            List<Card> cards = new ArrayList<Card>();
            cards.addAll(this.hand.getCards(game));
            while(!target.isChosen() && !cards.isEmpty()) {
                Card pick = pickTarget(cards, outcome, target, source, game);
                if (pick != null) {
                    target.addTarget(pick.getId(), source, game);
                }
            }
            return target.isChosen();
        }
        if (target instanceof TargetSpell) {
            if (game.getStack().size() > 0) {
                Iterator<StackObject> it = game.getStack().iterator();
                while (it.hasNext()) {
                    StackObject o = it.next();
                    if (o instanceof Spell && !source.getId().equals(o.getStackAbility().getId())) {
                        target.addTarget(o.getId(), source, game);
                        return true;
                    }
                }
            }
            return false;
        }
        if (target instanceof TargetCardInOpponentsGraveyard) {
            List<Card> cards = new ArrayList<Card>();
            for (UUID uuid: game.getOpponents(playerId)) {
                Player player = game.getPlayer(uuid);
                if (player != null) {
                    cards.addAll(player.getGraveyard().getCards(game));
                }
            }
            Card card = pickTarget(cards, outcome, target, source, game);
            if (card != null) {
                target.addTarget(card.getId(), source, game);
                return true;
            }
            //if (!target.isRequired())
                return false;
        }
        throw new IllegalStateException("Target wasn't handled. class:" + target.getClass().toString());
    }

    protected Card pickTarget(List<Card> cards, Outcome outcome, Target target, Ability source, Game game) {
        Card card;
        while (!cards.isEmpty()) {
            if (outcome.isGood()) {
                card = pickBestCard(cards, null, target, source, game);
            }
            else {
                card = pickWorstCard(cards, null, target, source, game);
            }
            if (source != null) {
                if (target.canTarget(card.getId(), source, game)) {
                    return card;
                }
            }
            else {
                return card;
            }
            cards.remove(card);
        }
        return null;
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
        if (log.isDebugEnabled())
            log.debug("chooseTarget: " + outcome.toString() + ":" + target.toString());
        UUID opponentId = game.getOpponents(playerId).iterator().next();
        if (target instanceof TargetCreatureOrPlayerAmount) {
            if (game.getPlayer(opponentId).getLife() <= target.getAmountRemaining()) {
                target.addTarget(opponentId, target.getAmountRemaining(), source, game);
                return true;
            }
            List<Permanent> targets;
            if (outcome.isGood()) {
                targets = threats(playerId, source.getSourceId(), new FilterCreaturePermanent(), game, target.getTargets());
            }
            else {
                targets = threats(opponentId, source.getSourceId(), new FilterCreaturePermanent(), game, target.getTargets());
            }
            for (Permanent permanent: targets) {
                if (target.canTarget(permanent.getId(), source, game)) {
                    if (permanent.getToughness().getValue() <= target.getAmountRemaining()) {
                        target.addTarget(permanent.getId(), permanent.getToughness().getValue(), source, game);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean priority(Game game) {
        log.debug("priority");
        UUID opponentId = game.getOpponents(playerId).iterator().next();
        if (game.getActivePlayerId().equals(playerId)) {
            if (game.isMainPhase() && game.getStack().isEmpty()) {
                playLand(game);
            }
            switch (game.getTurn().getStepType()) {
                case UPKEEP:
                    findPlayables(game);
                    break;
                case DRAW:
                    logState(game);
                    break;
                case PRECOMBAT_MAIN:
                    findPlayables(game);
                    if (playableAbilities.size() > 0) {
                        for (ActivatedAbility ability: playableAbilities) {
                            if (ability.canActivate(playerId, game)) {
                                if (ability.getEffects().hasOutcome(Outcome.PutLandInPlay)) {
                                    if (this.activateAbility(ability, game))
                                        return true;
                                }
                                if (ability.getEffects().hasOutcome(Outcome.PutCreatureInPlay)) {
                                    if (getOpponentBlockers(opponentId, game).size() <= 1)
                                        if (this.activateAbility(ability, game))
                                            return true;
                                }
                            }
                        }
                    }
                    break;
                case DECLARE_BLOCKERS:
                    findPlayables(game);
                    playRemoval(game.getCombat().getBlockers(), game);
                    playDamage(game.getCombat().getBlockers(), game);
                    break;
                case END_COMBAT:
                    findPlayables(game);
                    playDamage(game.getCombat().getBlockers(), game);
                    break;
                case POSTCOMBAT_MAIN:
                    findPlayables(game);
                    if (game.getStack().isEmpty()) {
                        if (playableNonInstant.size() > 0) {
                            for (Card card: playableNonInstant) {
                                if (card.getSpellAbility().canActivate(playerId, game)) {
                                    if (this.activateAbility(card.getSpellAbility(), game))
                                        return true;
                                }
                            }
                        }
                        if (playableAbilities.size() > 0) {
                            for (ActivatedAbility ability: playableAbilities) {
                                if (ability.canActivate(playerId, game)) {
                                    if (!(ability.getEffects().get(0) instanceof BecomesCreatureSourceEffect)) {
                                        if (this.activateAbility(ability, game))
                                            return true;
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
        }
        else {
            //respond to opponent events
            switch (game.getTurn().getStepType()) {
                case UPKEEP:
                    findPlayables(game);
                    break;
                case DECLARE_ATTACKERS:
                    findPlayables(game);
                    playRemoval(game.getCombat().getAttackers(), game);
                    playDamage(game.getCombat().getAttackers(), game);
                    break;
                case END_COMBAT:
                    findPlayables(game);
                    playDamage(game.getCombat().getAttackers(), game);
                    break;
            }
        }
        pass();
        return true;
    }


       @Override
    public boolean activateAbility(ActivatedAbility ability, Game game) {
        for (Target target: ability.getModes().getMode().getTargets()) {
            for (UUID targetId: target.getTargets()) {
                game.fireEvent(GameEvent.getEvent(EventType.TARGETED, targetId, ability.getId(), ability.getControllerId()));
            }
        }
        return super.activateAbility(ability, game);
    }

    protected void playLand(Game game) {
        log.debug("playLand");
        Set<Card> lands = hand.getCards(new FilterLandCard(), game);
        while (lands.size() > 0 && this.landsPlayed < this.landsPerTurn) {
            if (lands.size() == 1)
                this.playLand(lands.iterator().next(), game);
            else {
                playALand(lands, game);
            }
        }
    }

    protected void playALand(Set<Card> lands, Game game) {
        log.debug("playALand");
        //play a land that will allow us to play an unplayable
        for (Mana mana: unplayable.keySet()) {
            for (Card card: lands) {
                for (ManaAbility ability: card.getAbilities().getManaAbilities(Zone.BATTLEFIELD)) {
                    if (ability.getNetMana(game).enough(mana)) {
                        this.playLand(card, game);
                        lands.remove(card);
                        return;
                    }
                }
            }
        }
        //play a land that will get us closer to playing an unplayable
        for (Mana mana: unplayable.keySet()) {
            for (Card card: lands) {
                for (ManaAbility ability: card.getAbilities().getManaAbilities(Zone.BATTLEFIELD)) {
                    if (mana.contains(ability.getNetMana(game))) {
                        this.playLand(card, game);
                        lands.remove(card);
                        return;
                    }
                }
            }
        }
        //play first available land
        this.playLand(lands.iterator().next(), game);
        lands.remove(lands.iterator().next());
    }

    protected void findPlayables(Game game) {
        playableInstant.clear();
        playableNonInstant.clear();
        unplayable.clear();
        playableAbilities.clear();
        Set<Card> nonLands = hand.getCards(new FilterNonlandCard(), game);
        ManaOptions available = getManaAvailable(game);
        available.addMana(manaPool.getMana());

        for (Card card: nonLands) {
            ManaOptions options = card.getManaCost().getOptions();
            if (card.getManaCost().getVariableCosts().size() > 0) {
                //don't use variable mana costs unless there is at least 3 extra mana for X
                for (Mana option: options) {
                    option.add(Mana.ColorlessMana(3));
                }
            }
            for (Mana mana: options) {
                for (Mana avail: available) {
                    if (mana.enough(avail)) {
                        SpellAbility ability = card.getSpellAbility();
                        if (ability != null && ability.canActivate(playerId, game)) {
                            if (card.getCardType().contains(CardType.INSTANT))
                                playableInstant.add(card);
                            else
                                playableNonInstant.add(card);
                        }
                    }
                    else {
                        if (!playableInstant.contains(card) && !playableNonInstant.contains(card))
                            unplayable.put(mana.needed(avail), card);
                    }
                }
            }
        }
        for (Permanent permanent: game.getBattlefield().getAllActivePermanents(playerId)) {
            for (ActivatedAbility ability: permanent.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD)) {
                if (!(ability instanceof ManaAbility) && ability.canActivate(playerId, game)) {
                    if (ability instanceof EquipAbility && permanent.getAttachedTo() != null)
                        continue;
                    ManaOptions abilityOptions = ability.getManaCosts().getOptions();
                    if (ability.getManaCosts().getVariableCosts().size() > 0) {
                        //don't use variable mana costs unless there is at least 3 extra mana for X
                        for (Mana option: abilityOptions) {
                            option.add(Mana.ColorlessMana(3));
                        }
                    }
                    if (abilityOptions.size() == 0) {
                        playableAbilities.add(ability);
                    }
                    else {
                        for (Mana mana: abilityOptions) {
                            for (Mana avail: available) {
                                if (mana.enough(avail)) {
                                    playableAbilities.add(ability);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (Card card: graveyard.getCards(game)) {
            for (ActivatedAbility ability: card.getAbilities().getActivatedAbilities(Zone.GRAVEYARD)) {
                if (ability.canActivate(playerId, game)) {
                    ManaOptions abilityOptions = ability.getManaCosts().getOptions();
                    if (abilityOptions.size() == 0) {
                        playableAbilities.add(ability);
                    }
                    else {
                        for (Mana mana: abilityOptions) {
                            for (Mana avail: available) {
                                if (mana.enough(avail)) {
                                    playableAbilities.add(ability);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (log.isDebugEnabled())
            log.debug("findPlayables: " + playableInstant.toString() + "---" + playableNonInstant.toString() + "---" + playableAbilities.toString() );
    }

//    @Override
//    protected ManaOptions getManaAvailable(Game game) {
//        return super.getManaAvailable(game);
//    }

    @Override
    public boolean playMana(ManaCost unpaid, Game game) {
//        log.info("paying for " + unpaid.getText());
        ManaCost cost;
        List<Permanent> producers;
        if (unpaid instanceof ManaCosts) {
            cost = ((ManaCosts<ManaCost>)unpaid).get(0);
            producers = getSortedProducers((ManaCosts)unpaid, game);
        }
        else {
            cost = unpaid;
            producers = this.getAvailableManaProducers(game);
            producers.addAll(this.getAvailableManaProducersWithCost(game));
        }
        for (Permanent perm: producers) {
            // pay all colored costs first
            for (ManaAbility ability: perm.getAbilities().getAvailableManaAbilities(Zone.BATTLEFIELD, game)) {
                if (cost instanceof ColoredManaCost) {
                    if (cost.testPay(ability.getNetMana(game))) {
                        if (activateAbility(ability, game))
                            return true;
                    }
                }
            }
            // then pay hybrid
            for (ManaAbility ability: perm.getAbilities().getAvailableManaAbilities(Zone.BATTLEFIELD, game)) {
                if (cost instanceof HybridManaCost) {
                    if (cost.testPay(ability.getNetMana(game))) {
                        if (activateAbility(ability, game))
                            return true;
                    }
                }
            }
            // then pay mono hybrid
            for (ManaAbility ability: perm.getAbilities().getAvailableManaAbilities(Zone.BATTLEFIELD, game)) {
                if (cost instanceof MonoHybridManaCost) {
                    if (cost.testPay(ability.getNetMana(game))) {
                        if (activateAbility(ability, game))
                            return true;
                    }
                }
            }
            // finally pay generic
            for (ManaAbility ability: perm.getAbilities().getAvailableManaAbilities(Zone.BATTLEFIELD, game)) {
                if (cost instanceof GenericManaCost) {
                    if (cost.testPay(ability.getNetMana(game))) {
                        if (activateAbility(ability, game))
                            return true;
                    }
                }
            }
        }
        // pay phyrexian life costs
        if (cost instanceof PhyrexianManaCost) {
            if (cost.pay(null, game, null, playerId, false))
                return true;
        }
        return false;
    }

    /**
     *
     * returns a list of Permanents that produce mana sorted by the number of mana the Permanent produces
     * that match the unpaid costs in ascending order
     *
     * the idea is that we should pay costs first from mana producers that produce only one type of mana
     * and save the multi-mana producers for those costs that can't be paid by any other producers
     *
     * @param unpaid - the amount of unpaid mana costs
     * @param game
     * @return List<Permanent>
     */
    private List<Permanent> getSortedProducers(ManaCosts<ManaCost> unpaid, Game game) {
        List<Permanent> unsorted = this.getAvailableManaProducers(game);
        unsorted.addAll(this.getAvailableManaProducersWithCost(game));
        Map<Permanent, Integer> scored = new HashMap<Permanent, Integer>();
        for (Permanent permanent: unsorted) {
            int score = 0;
            for (ManaCost cost: unpaid) {
                for (ManaAbility ability: permanent.getAbilities().getAvailableManaAbilities(Zone.BATTLEFIELD, game)) {
                    if (cost.testPay(ability.getNetMana(game))) {
                        score++;
                        break;
                    }
                }
            }
            if (score > 0) { // score mana producers that produce other mana types and have other uses higher
                score += permanent.getAbilities().getAvailableManaAbilities(Zone.BATTLEFIELD, game).size();
                score += permanent.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD).size();
                if (!permanent.getCardType().contains(CardType.LAND))
                    score+=2;
                else if(permanent.getCardType().contains(CardType.CREATURE))
                    score+=2;
            }
            scored.put(permanent, score);
        }
        return sortByValue(scored);
    }

    private List<Permanent> sortByValue(Map<Permanent, Integer> map) {
        List<Entry<Permanent, Integer>> list = new LinkedList<Entry<Permanent, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Entry<Permanent, Integer>>() {
            @Override
            public int compare(Entry<Permanent, Integer> o1, Entry<Permanent, Integer> o2) {
                return (o1.getValue().compareTo(o2.getValue()));
            }
        });
        List<Permanent> result = new ArrayList<Permanent>();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Entry<Permanent, Integer> entry = (Entry<Permanent, Integer>)it.next();
            result.add(entry.getKey());
        }
        return result;
    }

    @Override
    public boolean playXMana(VariableManaCost cost, ManaCosts<ManaCost> costs, Game game) {
        log.debug("playXMana");
        //put everything into X
        for (Permanent perm: this.getAvailableManaProducers(game)) {
            for (ManaAbility ability: perm.getAbilities().getAvailableManaAbilities(Zone.BATTLEFIELD, game)) {
                activateAbility(ability, game);
            }
        }

        // don't allow X=0
        if (getManaPool().count() == 0) {
            return false;
        }

        game.informPlayers(getName() + " payed " + cost.getPayment().count() + " for " + cost.getText());
        cost.setPaid();
        return true;
    }

    @Override
    public void abort() {
        abort = true;
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Game game) {
        log.debug("chooseUse: " + outcome.isGood());
        // Be proactive! Always use abilities, the evaluation function will decide if it's good or not
        // Otherwise some abilities won't be used by AI like LoseTargetEffect that has "bad" outcome
        // but still is good when targets opponent
        return true;
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        log.debug("choose 3");
        //TODO: improve this
        if (choice.getMessage() != null && choice.getMessage().equals("Choose creature type")) {
            chooseCreatureType(outcome, choice, game);
        }
        if (!choice.isChosen()) {
            int choiceIdx = (int) (Math.random()*choice.getChoices().size()+1);
            Iterator iterator = choice.getChoices().iterator();
            while (iterator.hasNext()) {
                String next = (String) iterator.next();
                if (--choiceIdx > 0) {
                    continue;
                }
                if (!next.isEmpty()) {
                    choice.setChoice(next);
                    break;
                }
            }
        }
        return true;
    }

    protected boolean chooseCreatureType(Outcome outcome, Choice choice, Game game) {
        if (outcome.equals(Outcome.Detriment)) {
            // choose a creature type of opponent on battlefield or graveyard
            for (Permanent permanent :game.getBattlefield().getActivePermanents(this.getId(), game)) {
                if(game.getOpponents(this.getId()).contains(permanent.getControllerId())
                        && permanent.getCardType().contains(CardType.CREATURE)
                        && permanent.getSubtype().size() > 0) {
                    if (choice.getChoices().contains(permanent.getSubtype().get(0))) {
                        choice.setChoice(permanent.getSubtype().get(0));
                        break;
                    }
                }
            }
            // or in opponent graveyard
            if (!choice.isChosen()) {
                for (UUID opponentId :game.getOpponents(this.getId())) {
                    Player opponent = game.getPlayer(opponentId);
                    for (Card card : opponent.getGraveyard().getCards(game)) {
                        if (card != null && card.getCardType().contains(CardType.CREATURE) && card.getSubtype().size() > 0) {
                            if (choice.getChoices().contains(card.getSubtype().get(0))) {
                                choice.setChoice(card.getSubtype().get(0));
                                break;
                            }
                        }
                    }
                    if (choice.isChosen()) {
                        break;
                    }
                }
            }
        } else {
            // choose a creature type of hand or library
            for (UUID cardId :this.getHand()) {
                Card card = game.getCard(cardId);
                if (card != null && card.getCardType().contains(CardType.CREATURE) && card.getSubtype().size() > 0) {
                    if (choice.getChoices().contains(card.getSubtype().get(0))) {
                        choice.setChoice(card.getSubtype().get(0));
                        break;
                    }
                }
            }
            if (!choice.isChosen()) {
                for (UUID cardId : this.getLibrary().getCardList()) {
                    Card card = game.getCard(cardId);
                    if (card != null && card.getCardType().contains(CardType.CREATURE) && card.getSubtype().size() > 0) {
                        if (choice.getChoices().contains(card.getSubtype().get(0))) {
                            choice.setChoice(card.getSubtype().get(0));
                            break;
                        }
                    }
                }
            }
        }
        return choice.isChosen();
    }
    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game)  {
        log.debug("chooseTarget");
        if (cards != null && cards.isEmpty()) {
            if (!target.isRequired())
                return false;
            return true;
        }

        ArrayList<Card> cardChoices = new ArrayList<Card>(cards.getCards(target.getFilter(), game));
        while (!target.doneChosing()) {
            Card card = pickTarget(cardChoices, outcome, target, source, game);
            if (card != null) {
                target.addTarget(card.getId(), source, game);
                cardChoices.remove(card);
            }
            if (outcome.equals(Outcome.Neutral) && target.getTargets().size() > target.getNumberOfTargets() + (target.getMaxNumberOfTargets() - target.getNumberOfTargets()) / 2) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game)  {
        log.debug("choose 2");
        if (cards != null && cards.isEmpty()) {
            if (!target.isRequired())
                return false;
            return true;
        }

        ArrayList<Card> cardChoices = new ArrayList<Card>(cards.getCards(target.getFilter(), game));
        while (!target.doneChosing()) {
            Card card = pickTarget(cardChoices, outcome, target, null, game);
            if (card != null) {
                target.add(card.getId(), game);
                cardChoices.remove(card);
            } else {
                // We don't have any valid target to choose so stop choosing
                break;
            }
            if (outcome.equals(Outcome.Neutral) && target.getTargets().size() > target.getNumberOfTargets() + (target.getMaxNumberOfTargets() - target.getNumberOfTargets()) / 2) {
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game) {
        //TODO: improve this
        return true;
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        log.debug("selectAttackers");
        UUID opponentId = game.getCombat().getDefenders().iterator().next();
        Attackers attackers = getPotentialAttackers(game);
        List<Permanent> blockers = getOpponentBlockers(opponentId, game);
        List<Permanent> actualAttackers = new ArrayList<Permanent>();
        if (blockers.isEmpty()) {
            actualAttackers = attackers.getAttackers();
        }
        else if (attackers.size() - blockers.size() >= game.getPlayer(opponentId).getLife()) {
            actualAttackers = attackers.getAttackers();
        }
        else {
            CombatSimulator combat = simulateAttack(attackers, blockers, opponentId, game);
            if (combat.rating > 2) {
                for (CombatGroupSimulator group: combat.groups) {
                    this.declareAttacker(group.attackers.get(0).id, group.defenderId, game);
                }
            }
        }
        for (Permanent attacker: actualAttackers) {
            this.declareAttacker(attacker.getId(), opponentId, game);
        }
        return;
    }

    @Override
    public void selectBlockers(Game game, UUID defendingPlayerId) {
        log.debug("selectBlockers");

        List<Permanent> blockers = getAvailableBlockers(game);

        CombatSimulator sim = simulateBlock(CombatSimulator.load(game), blockers, game);

        List<CombatGroup> groups = game.getCombat().getGroups();
        for (int i = 0; i< groups.size(); i++) {
            for (CreatureSimulator creature: sim.groups.get(i).blockers) {
                groups.get(i).addBlocker(creature.id, playerId, game);
            }
        }
    }

    @Override
    public int chooseEffect(List<ReplacementEffect> rEffects, Game game) {
        log.debug("chooseEffect");
        //TODO: implement this
        return 0;
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        log.debug("chooseMode");
        //TODO: improve this;
        return modes.values().iterator().next();
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        log.debug("chooseTriggeredAbility");
        //TODO: improve this
        if (abilities.size() > 0)
            return abilities.get(0);
        return null;
    }

    @Override
    public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID sourceId, Game game) {
        log.debug("assignDamage");
        //TODO: improve this
        game.getPermanent(targets.get(0)).damage(damage, sourceId, game, true, false);
    }

    @Override
    public int getAmount(int min, int max, String message, Game game) {
        log.debug("getAmount");
        //TODO: improve this
        return min;
    }

    @Override
    public UUID chooseAttackerOrder(List<Permanent> attackers, Game game) {
        //TODO: improve this
        return attackers.iterator().next().getId();
    }

    @Override
    public UUID chooseBlockerOrder(List<Permanent> blockers, Game game) {
        //TODO: improve this
        return blockers.iterator().next().getId();
    }

    @Override
    protected List<Permanent> getAvailableManaProducers(Game game) {
        return super.getAvailableManaProducers(game);
    }

    @Override
    public void sideboard(Match match, Deck deck) {
        //TODO: improve this
        match.submitDeck(playerId, deck);
    }

    private static void addBasicLands(Deck deck, String landName, int number) {
        Random random = new Random();
        Set<String> landSets =  Sets.getSetsWithBasicLandsAsCodes(deck.getExpansionSetCodes());

        CardCriteria criteria = new CardCriteria();
        if (!landSets.isEmpty()) {
            criteria.setCodes(landSets.toArray(new String[landSets.size()]));
        }
        criteria.rarities(Constants.Rarity.LAND).name(landName);
        List<CardInfo> cards = CardRepository.instance.findCards(criteria);

        if (cards.isEmpty()) {
            return;
        }

        for (int i = 0; i < number; i++) {
            Card land = cards.get(random.nextInt(cards.size())).getCard();
            deck.getCards().add(land);
        }
    }

    public static Deck buildDeck(List<Card> cardPool, final List<Constants.ColoredManaSymbol> colors) {
        Deck deck = new Deck();
        List<Card> sortedCards = new ArrayList<Card>(cardPool);
        Collections.sort(sortedCards, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                Integer score1 = RateCard.rateCard(o1, colors);
                Integer score2 = RateCard.rateCard(o2, colors);
                return score2.compareTo(score1);
            }
        });
        int cardNum = 0;
        while (deck.getCards().size() < 23 && sortedCards.size() > cardNum) {
            Card card = sortedCards.get(cardNum);
            if (!card.getSupertype().contains("Basic")) {
                deck.getCards().add(card);
                deck.getSideboard().remove(card);
            }
            cardNum++;
        }
        // add basic lands
        // TODO:  compensate for non basic lands
        Mana mana = new Mana();
        for (Card card: deck.getCards()) {
            mana.add(card.getManaCost().getMana());
        }
        double total = mana.getBlack() + mana.getBlue() + mana.getGreen() + mana.getRed() + mana.getWhite();
        int mostLand = 0;
        String mostLandName = "Forest";
        if (mana.getGreen() > 0) {
            int number = (int) Math.round(mana.getGreen() / total * 17);
            addBasicLands(deck, "Forest", number);
            mostLand = number;
        }
        if (mana.getBlack() > 0) {
            int number = (int) Math.round(mana.getBlack() / total * 17);
            addBasicLands(deck, "Swamp", number);
            if (number > mostLand) {
                mostLand = number;
                mostLandName = "Swamp";
            }
        }
        if (mana.getBlue() > 0) {
            int number = (int) Math.round(mana.getBlue() / total * 17);
            addBasicLands(deck, "Island", number);
            if (number > mostLand) {
                mostLand = number;
                mostLandName = "Island";
            }
        }
        if (mana.getWhite() > 0) {
            int number = (int) Math.round(mana.getWhite() / total * 17);
            addBasicLands(deck, "Plains", number);
            if (number > mostLand) {
                mostLand = number;
                mostLandName = "Plains";
            }
        }
        if (mana.getRed() > 0) {
            int number = (int) Math.round(mana.getRed() / total * 17);
            addBasicLands(deck, "Mountain", number);
            if (number > mostLand) {
                mostLandName = "Plains";
            }
        }

        addBasicLands(deck, mostLandName, 40 - deck.getCards().size());

        return deck;
    }

    @Override
    public void construct(Tournament tournament, Deck deck) {
        if (deck.getCards().size() < 40) {
            //pick the top 23 cards
            if (chosenColors == null) {
                for (Card card: deck.getSideboard()) {
                    rememberPick(card, RateCard.rateCard(card, null));
                }
                chosenColors = chooseDeckColorsIfPossible();
            }
            deck = buildDeck(new ArrayList<Card>(deck.getSideboard()), chosenColors);
        }
        tournament.submitDeck(playerId, deck);
    }

    public Card pickBestCard(List<Card> cards, List<Constants.ColoredManaSymbol> chosenColors) {
        if (cards.isEmpty()) {
            return null;
        }
        Card bestCard = null;
        int maxScore = 0;
        for (Card card : cards) {
            int score = RateCard.rateCard(card, chosenColors);
            if (bestCard == null || score > maxScore) {
                maxScore = score;
                bestCard = card;
            }
        }
        return bestCard;
    }

    public Card pickBestCard(List<Card> cards, List<Constants.ColoredManaSymbol> chosenColors, Target target, Ability source, Game game) {
        if (cards.isEmpty()) {
            return null;
        }
        Card bestCard = null;
        int maxScore = 0;
        for (Card card : cards) {
            int score = RateCard.rateCard(card, chosenColors);
            boolean betterCard = false;
            if (bestCard == null) { // we need any card to prevent NPE in callers
                betterCard = true;
            } else if (score > maxScore) { // we need better card
                if (target != null && source != null && game != null) {
                    // but also check it can be targeted
                    betterCard = target.canTarget(card.getId(), source, game);
                } else {
                    // target object wasn't provided, so acceptings it anyway
                    betterCard = true;
                }
            }
            // is it better than previous one?
            if (betterCard) {
                maxScore = score;
                bestCard = card;
            }
        }
        return bestCard;
    }

    public Card pickWorstCard(List<Card> cards, List<Constants.ColoredManaSymbol> chosenColors, Target target, Ability source, Game game) {
        if (cards.isEmpty()) {
            return null;
        }
        Card worstCard = null;
        int minScore = Integer.MAX_VALUE;
        for (Card card : cards) {
            int score = RateCard.rateCard(card, chosenColors);
            boolean worseCard = false;
            if (worstCard == null) { // we need any card to prevent NPE in callers
                worseCard = true;
            } else if (score < minScore) { // we need worse card
                if (target != null && source != null && game != null) {
                    // but also check it can be targeted
                    worseCard = target.canTarget(card.getId(), source, game);
                } else {
                    // target object wasn't provided, so accepting it anyway
                    worseCard = true;
                }
            }
            // is it worse than previous one?
            if (worseCard) {
                minScore = score;
                worstCard = card;
            }
        }
        return worstCard;
    }

    public Card pickWorstCard(List<Card> cards, List<Constants.ColoredManaSymbol> chosenColors) {
        if (cards.isEmpty()) {
            return null;
        }
        Card worstCard = null;
        int minScore = Integer.MAX_VALUE;
        for (Card card : cards) {
            int score = RateCard.rateCard(card, chosenColors);
            if (worstCard == null || score < minScore) {
                minScore = score;
                worstCard = card;
            }
        }
        return worstCard;
    }

    @Override
    public void pickCard(List<Card> cards, Deck deck, Draft draft) {
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("No cards to pick from.");
        }
        try {
            Card bestCard = pickBestCard(cards, chosenColors);
            int maxScore = RateCard.rateCard(bestCard, chosenColors);
            int pickedCardRate = RateCard.getCardRating(bestCard);

            if (pickedCardRate <= 3) {
                // if card is bad
                // try to counter pick without any color restriction
                Card counterPick = pickBestCard(cards, null);
                int counterPickScore = RateCard.getCardRating(counterPick);
                // card is really good
                // take it!
                if (counterPickScore >= 8) {
                    bestCard = counterPick;
                    maxScore =  RateCard.rateCard(bestCard, chosenColors);
                }
            }


            String colors = "not chosen yet";
            // remember card if colors are not chosen yet
            if (chosenColors == null) {
                rememberPick(bestCard, maxScore);
                chosenColors = chooseDeckColorsIfPossible();
            }
            if (chosenColors != null) {
                colors = "";
                for (Constants.ColoredManaSymbol symbol : chosenColors) {
                    colors += symbol.toString();
                }
            }
            log.debug("[DEBUG] AI picked: " + bestCard.getName() + ", score=" + maxScore + ", deck colors=" + colors);
            draft.addPick(playerId, bestCard.getId());
        } catch (Exception e) {
            e.printStackTrace();
            draft.addPick(playerId, cards.get(0).getId());
        }
    }

    /**
     * Remember picked card with its score.
     *
     * @param card
     * @param score
     */
    protected void rememberPick(Card card, int score) {
        if (pickedCards == null) {
            pickedCards = new ArrayList<PickedCard>();
        }
        pickedCards.add(new PickedCard(card, score));
    }

    /**
     * Choose 2 deck colors for draft:
     * 1. there should be at least 3 cards in card pool
     * 2. at least 2 cards should have different colors
     * 3. get card colors as chosen starting from most rated card
     */
    protected List<Constants.ColoredManaSymbol> chooseDeckColorsIfPossible() {
        if (pickedCards.size() > 2) {
            // sort by score and color mana symbol count in descending order
            Collections.sort(pickedCards, new Comparator<PickedCard>() {
                @Override
                public int compare(PickedCard o1, PickedCard o2) {
                    if (o1.score.equals(o2.score)) {
                        Integer i1 = RateCard.getColorManaCount(o1.card);
                        Integer i2 = RateCard.getColorManaCount(o2.card);
                        return -i1.compareTo(i2);
                    }
                    return -o1.score.compareTo(o2.score);
                }
            });
            Set<String> chosenSymbols = new HashSet<String>();
            for (PickedCard picked : pickedCards) {
                int differentColorsInCost = RateCard.getDifferentColorManaCount(picked.card);
                // choose only color card, but only if they are not too gold
                if (differentColorsInCost > 0 && differentColorsInCost < 3) {
                    // if some colors were already chosen, total amount shouldn't be more than 3
                    if (chosenSymbols.size() + differentColorsInCost < 4) {
                        for (String symbol : picked.card.getManaCost().getSymbols()) {
                            symbol = symbol.replace("{", "").replace("}", "");
                            if (RateCard.isColoredMana(symbol)) {
                                chosenSymbols.add(symbol);
                            }
                        }
                    }
                }
                // only two or three color decks are allowed
                if (chosenSymbols.size() >  1 && chosenSymbols.size() < 4) {
                    List<Constants.ColoredManaSymbol> colorsChosen = new ArrayList<Constants.ColoredManaSymbol>();
                    for (String symbol : chosenSymbols) {
                        Constants.ColoredManaSymbol manaSymbol = Constants.ColoredManaSymbol.lookup(symbol.charAt(0));
                        if (manaSymbol != null) {
                            colorsChosen.add(manaSymbol);
                        }
                    }
                    if (colorsChosen.size() > 1) {
                        // no need to remember picks anymore
                        pickedCards = null;
                        return colorsChosen;
                    }
                }
            }
        }
        return null;
    }

    private class PickedCard {
        public Card card;
        public Integer score;
        public PickedCard(Card card, int score) {
            this.card = card; this.score = score;
        }
    }

    protected Attackers getPotentialAttackers(Game game) {
        log.debug("getAvailableAttackers");
        Attackers attackers = new Attackers();
        List<Permanent> creatures = super.getAvailableAttackers(game);
        for (Permanent creature: creatures) {
            int potential = combatPotential(creature, game);
            if (potential > 0 && creature.getPower().getValue() > 0) {
                List<Permanent> l = attackers.get(potential);
                if (l == null)
                    attackers.put(potential, l = new ArrayList<Permanent>());
                l.add(creature);
            }
        }    
        return attackers;
    }

    protected int combatPotential(Permanent creature, Game game) {
        log.debug("combatPotential");
        if (!creature.canAttack(game))
            return 0;
        int potential = creature.getPower().getValue();
        potential += creature.getAbilities().getEvasionAbilities().size();
        potential += creature.getAbilities().getProtectionAbilities().size();
        potential += creature.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId())?1:0;
        potential += creature.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId())?2:0;
        potential += creature.getAbilities().containsKey(TrampleAbility.getInstance().getId())?1:0;
        return potential;
    }

    protected List<Permanent> getOpponentBlockers(UUID opponentId, Game game) {
        FilterCreatureForCombat blockFilter = new FilterCreatureForCombat();
        List<Permanent> blockers = game.getBattlefield().getAllActivePermanents(blockFilter, opponentId, game);
        return blockers;
    }

    protected CombatSimulator simulateAttack(Attackers attackers, List<Permanent> blockers, UUID opponentId, Game game) {
        log.debug("simulateAttack");
        List<Permanent> attackersList = attackers.getAttackers();
        CombatSimulator best = new CombatSimulator();
        int bestResult = 0;
        //use binary digits to calculate powerset of attackers
        int powerElements = (int) Math.pow(2, attackersList.size());
        for (int i = 1; i < powerElements; i++) {
            String binary = Integer.toBinaryString(i);
            while(binary.length() < attackersList.size()) {
                binary = "0" + binary;
            }
            List<Permanent> trialAttackers = new ArrayList<Permanent>();
            for (int j = 0; j < attackersList.size(); j++) {
                if (binary.charAt(j) == '1')
                    trialAttackers.add(attackersList.get(j));
            }
            CombatSimulator combat = new CombatSimulator();
            for (Permanent permanent: trialAttackers) {
                combat.groups.add(new CombatGroupSimulator(opponentId, Arrays.asList(permanent.getId()), new ArrayList<UUID>(), game));
            }
            CombatSimulator test = simulateBlock(combat, blockers, game);
            if (test.evaluate() > bestResult) {
                best = test;
                bestResult = test.evaluate();
            }
        }

        return best;
    }

    protected CombatSimulator simulateBlock(CombatSimulator combat, List<Permanent> blockers, Game game) {
        log.debug("simulateBlock");

        TreeNode<CombatSimulator> simulations;

        simulations = new TreeNode<CombatSimulator>(combat);
        addBlockSimulations(blockers, simulations, game);
        combat.simulate();

        return getWorstSimulation(simulations);

    }

    protected void addBlockSimulations(List<Permanent> blockers, TreeNode<CombatSimulator> node, Game game) {
        int numGroups = node.getData().groups.size();
        Copier<CombatSimulator> copier = new Copier<CombatSimulator>();
        for (Permanent blocker: blockers) {
            List<Permanent> subList = remove(blockers, blocker);
            for (int i = 0; i < numGroups; i++) {
                if (node.getData().groups.get(i).canBlock(blocker, game)) {
                    CombatSimulator combat = copier.copy(node.getData());
                    combat.groups.get(i).blockers.add(new CreatureSimulator(blocker));
                    TreeNode<CombatSimulator> child = new TreeNode<CombatSimulator>(combat);
                    node.addChild(child);
                    addBlockSimulations(subList, child, game);
                    combat.simulate();
                }
            }
        }
    }

    protected List<Permanent> remove(List<Permanent> source, Permanent element) {
        List<Permanent> newList = new ArrayList<Permanent>();
        for (Permanent permanent: source) {
            if (!permanent.equals(element)) {
                newList.add(permanent);
            }
        }
        return newList;
    }

    protected CombatSimulator getBestSimulation(TreeNode<CombatSimulator> simulations) {
        CombatSimulator best = simulations.getData();
        int bestResult = best.evaluate();
        for (TreeNode<CombatSimulator> node: simulations.getChildren()) {
            CombatSimulator bestSub = getBestSimulation(node);
            if (bestSub.evaluate() > bestResult) {
                best = node.getData();
                bestResult = best.evaluate();
            }
        }
        return best;
    }

    protected CombatSimulator getWorstSimulation(TreeNode<CombatSimulator> simulations) {
        CombatSimulator worst = simulations.getData();
        int worstResult = worst.evaluate();
        for (TreeNode<CombatSimulator> node: simulations.getChildren()) {
            CombatSimulator worstSub = getWorstSimulation(node);
            if (worstSub.evaluate() < worstResult) {
                worst = node.getData();
                worstResult = worst.evaluate();
            }
        }
        return worst;
    }

    protected List<Permanent> threats(UUID playerId, UUID sourceId, FilterPermanent filter, Game game, List<UUID> targets) {
        List<Permanent> threats = playerId == null ?
                game.getBattlefield().getAllActivePermanents(filter, game) :
                game.getBattlefield().getActivePermanents(filter, this.playerId, sourceId, game);

        Iterator<Permanent> it = threats.iterator();
        while (it.hasNext()) { // remove permanents already targeted
            Permanent test = it.next();
            if (targets.contains(test.getId()) || (playerId != null && !test.getControllerId().equals(playerId)))
                it.remove();
        }
        Collections.sort(threats, new PermanentComparator(game));
        Collections.reverse(threats);
        return threats;
    }

    protected void logState(Game game) {
        if (log.isDebugEnabled())
            logList("computer player " + name + " hand: ", new ArrayList(hand.getCards(game)));
    }

    protected void logList(String message, List<MageObject> list) {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append(": ");
        for (MageObject object: list) {
            sb.append(object.getName()).append(",");
        }
        log.info(sb.toString());
    }

    protected void logAbilityList(String message, List<Ability> list) {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append(": ");
        for (Ability ability: list) {
            sb.append(ability.getRule()).append(",");
        }
        log.debug(sb.toString());
    }

    private void playRemoval(List<UUID> creatures, Game game) {
        for (UUID creatureId: creatures) {
            for (Card card: this.playableInstant) {
                if (card.getSpellAbility().canActivate(playerId, game)) {
                    for (Effect effect: card.getSpellAbility().getEffects()) {
                        if (effect.getOutcome().equals(Outcome.DestroyPermanent) || effect.getOutcome().equals(Outcome.ReturnToHand)) {
                            if (card.getSpellAbility().getTargets().get(0).canTarget(creatureId, card.getSpellAbility(), game)) {
                                if (this.activateAbility(card.getSpellAbility(), game))
                                    return;
                            }
                        }
                    }
                }
            }
        }
    }

    private void playDamage(List<UUID> creatures, Game game) {
        for (UUID creatureId: creatures) {
            Permanent creature = game.getPermanent(creatureId);
            for (Card card: this.playableInstant) {
                if (card.getSpellAbility().canActivate(playerId, game)) {
                    for (Effect effect: card.getSpellAbility().getEffects()) {
                        if (effect instanceof DamageTargetEffect) {
                            if (card.getSpellAbility().getTargets().get(0).canTarget(creatureId, card.getSpellAbility(), game)) {
                                if (((DamageTargetEffect)effect).getAmount() > (creature.getPower().getValue() - creature.getDamage())) {
                                    if (this.activateAbility(card.getSpellAbility(), game))
                                        return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        unplayable = new TreeMap<Mana, Card>();
        playableNonInstant = new ArrayList<Card>();
        playableInstant = new ArrayList<Card>();
        playableAbilities = new ArrayList<ActivatedAbility>();
    }

    @Override
    public T copy() {
        return (T)new ComputerPlayer(this);
    }

}
