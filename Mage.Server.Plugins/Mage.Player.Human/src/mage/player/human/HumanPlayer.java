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

package mage.player.human;

import mage.Constants.Outcome;
import mage.Constants.RangeOfInfluence;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.*;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.PhyrexianManaCost;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.mana.ManaAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.common.FilterCreatureForCombat;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.draft.Draft;
import mage.game.match.Match;
import mage.game.permanent.Permanent;
import mage.game.tournament.Tournament;
import mage.players.Player;
import mage.players.PlayerImpl;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetDefender;
import mage.util.ManaUtil;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class HumanPlayer extends PlayerImpl<HumanPlayer> {

    private final transient PlayerResponse response = new PlayerResponse();

    protected static FilterCreatureForCombat filterCreatureForCombat = new FilterCreatureForCombat();
    protected static FilterAttackingCreature filterAttack = new FilterAttackingCreature();
    protected static FilterBlockingCreature filterBlock = new FilterBlockingCreature();
    protected static final Choice replacementEffectChoice = new ChoiceImpl(true);
    private static final Map<String, Serializable> staticOptions = new HashMap<String, Serializable>();

    private static final Logger log = Logger.getLogger(HumanPlayer.class);

    static {
        replacementEffectChoice.setMessage("Choose replacement effect");
        staticOptions.put("UI.right.btn.text", "Done");
    }

    public HumanPlayer(String name, RangeOfInfluence range, int skill) {
        super(name, range);
        human = true;
    }

    public HumanPlayer(final HumanPlayer player) {
        super(player);
    }

    protected void waitForResponse() {
        response.clear();
        log.debug("Waiting response from player: " + getId());
        synchronized(response) {
            try {
                response.wait();
                log.debug("Got response from player: " + getId());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void waitForBooleanResponse() {
        do {
            waitForResponse();
        } while (response.getBoolean() == null && !abort);
    }

    protected void waitForUUIDResponse() {
        do {
            waitForResponse();
        } while (response.getUUID() == null && !abort);
    }

    protected void waitForStringResponse() {
        do {
            waitForResponse();
        } while (response.getString() == null && !abort);
    }

    protected void waitForIntegerResponse() {
        do {
            waitForResponse();
        } while (response.getInteger() == null && !abort);
    }

    @Override
    public boolean chooseMulligan(Game game) {
        updateGameStatePriority("chooseMulligan", game);
        game.fireAskPlayerEvent(playerId, "Do you want to take a mulligan?");
        waitForBooleanResponse();
        if (!abort)
            return response.getBoolean();
        return false;
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Game game) {
        updateGameStatePriority("chooseUse", game);
        game.fireAskPlayerEvent(playerId, message);
        waitForBooleanResponse();
        if (!abort)
            return response.getBoolean();
        return false;
    }

    @Override
    public int chooseEffect(List<String> rEffects, Game game) {
        updateGameStatePriority("chooseEffect", game);
        replacementEffectChoice.getChoices().clear();
        int count = 1;
        for (String effectText: rEffects) {
            replacementEffectChoice.getChoices().add(count + ". " + effectText);
            count++;
        }
        if (replacementEffectChoice.getChoices().size() == 1)
            return 0;
        while (!abort) {
            game.fireChooseEvent(playerId, replacementEffectChoice);
            waitForResponse();
            log.debug("Choose effect: " + response.getString());
            if (response.getString() != null) {
                replacementEffectChoice.setChoice(response.getString());
                count = 1;
                for (int i = 0; i < rEffects.size(); i++) {
                    if (replacementEffectChoice.getChoice().equals(count + ". " + rEffects.get(i)))
                        return i;
                    count++;
                }
            }
        }
        return 0;
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        updateGameStatePriority("choose(3)", game);
        while (!abort) {
            game.fireChooseEvent(playerId, choice);
            waitForResponse();
            if (response.getString() != null) {
                choice.setChoice(response.getString());
                return true;
            } else if (!choice.isRequired()) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
         return choose(outcome, target, sourceId, game, null);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options) {
        updateGameStatePriority("choose(5)", game);
        while (!abort) {
            Set<UUID> cards = target.possibleTargets(null, playerId, game);
            game.fireSelectTargetEvent(playerId, target.getMessage(), cards, target.isRequired(), options);
            waitForResponse();
            if (response.getUUID() != null) {
                if (target instanceof TargetPermanent) {
                    if (((TargetPermanent)target).canTarget(playerId, response.getUUID(), sourceId, game, false)) {
                        target.add(response.getUUID(), game);
                        if(target.doneChosing()){
                            return true;
                        }
                    }
                } else {
                    MageObject object = game.getObject(sourceId);
                    if (object instanceof Ability) {
                        if (target.canTarget(response.getUUID(), (Ability) object, game)) {
                            target.add(response.getUUID(), game);
                            if (target.doneChosing()) {
                                return true;
                            }
                        }
                    } else {
                        if (target.canTarget(response.getUUID(), game)) {
                            target.add(response.getUUID(), game);
                            if (target.doneChosing()) {
                                return true;
                            }
                        }
                    }
                }
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!target.isRequired()) {
                    return false;
                }
                if (cards == null || cards.isEmpty()) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        updateGameStatePriority("chooseTarget", game);
        while (!abort) {
            Set<UUID> possibleTargets = target.possibleTargets(source==null?null:source.getSourceId(), playerId, game);
            boolean required = possibleTargets.isEmpty() ? false : target.isRequired();
            game.fireSelectTargetEvent(playerId, target.getMessage(), possibleTargets, required, getOptions(target));
            waitForResponse();
            if (response.getUUID() != null) {
                if (possibleTargets.contains(response.getUUID())) {
                    if (target instanceof TargetPermanent) {
                        if (((TargetPermanent)target).canTarget(playerId, response.getUUID(), source, game)) {
                            target.addTarget(response.getUUID(), source, game);
                            if(target.doneChosing()){
                                return true;
                            }
                        }
                    } else if (target.canTarget(response.getUUID(), source, game)) {
                        target.addTarget(response.getUUID(), source, game);
                        if(target.doneChosing()){
                            return true;
                        }
                    }
                } // else do nothing - allow to pick another target
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!required) {
                    return false;
                }
            }
        }
        return false;
    }

    private Map<String, Serializable> getOptions(Target target) {
        return target.getNumberOfTargets() != target.getMaxNumberOfTargets() ? staticOptions : null;
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game) {
        updateGameStatePriority("choose(4)", game);
        while (!abort) {
            boolean required = target.isRequired();
            // if there is no cards to select from, then add possibility to cancel choosing action
            if (cards == null) {
                required = false;
            } else {
                int count = cards.count(target.getFilter(), game);
                if (count == 0) required = false;
            }
            Map<String, Serializable> options = getOptions(target);
            if (target.getTargets().size() > 0) {
                if (options == null) {
                    options = new HashMap<String, Serializable>(1);
                }
                List<UUID> chosen = (List<UUID>)target.getTargets();
                options.put("chosen", (Serializable)chosen);
            }
            game.fireSelectTargetEvent(playerId, target.getMessage(), cards, required, options);
            waitForResponse();
            if (response.getUUID() != null) {
                if (target.canTarget(response.getUUID(), cards, game)) {
                    target.add(response.getUUID(), game);
                    if(target.doneChosing()){
                        return true;
                    }
                }
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!required) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        updateGameStatePriority("chooseTarget(5)", game);
        while (!abort) {
            boolean required = target.isRequired();
            // if there is no cards to select from, then add possibility to cancel choosing action
            if (cards == null) {
                required = false;
            } else {
                int count = cards.count(target.getFilter(), game);
                if (count == 0) required = false;
            }
            game.fireSelectTargetEvent(playerId, target.getMessage(), cards, target.isRequired(), null);
            waitForResponse();
            if (response.getUUID() != null) {
                if (target.canTarget(response.getUUID(), cards, game)) {
                    target.addTarget(response.getUUID(), source, game);
                    if(target.doneChosing()){
                        return true;
                    }
                }
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!required) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
        updateGameStatePriority("chooseTargetAmount", game);
        while (!abort) {
            game.fireSelectTargetEvent(playerId, target.getMessage() + "\n Amount remaining:" + target.getAmountRemaining(), target.possibleTargets(source==null?null:source.getId(), playerId, game), target.isRequired(), null);
            waitForResponse();
            if (response.getUUID() != null) {
                if (target.canTarget(response.getUUID(), source, game)) {
                    UUID targetId = response.getUUID();
                    int amountSelected = getAmount(1, target.getAmountRemaining(), "Select amount", game);
                    target.addTarget(targetId, amountSelected, source, game);
                    return true;
                }
            } else if (!target.isRequired()) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean priority(Game game) {
        passed = false;
        if (!abort) {
            if (passedAllTurns) {
                pass();
                return false;
            }
            if (passedTurn && game.getStack().isEmpty()) {
                pass();
                return false;
            }
            updateGameStatePriority("priority", game);
            game.firePriorityEvent(playerId);
            waitForResponse();
            if (response.getBoolean() != null) {
                pass();
                return false;
            } else if (response.getInteger() != null) {
                /*if (response.getInteger() == -9999) {
                    passedAllTurns = true;
                }*/
                pass();
                //passedTurn = true;
                return false;
            } else if (response.getString() != null && response.getString().equals("special")) {
                specialAction(game);
            } else if (response.getUUID() != null) {
                MageObject object = game.getObject(response.getUUID());
                if (object != null) {
                    Zone zone = game.getState().getZone(object.getId());
                    if (zone != null) {
                        LinkedHashMap<UUID, ActivatedAbility> useableAbilities = getUseableActivatedAbilities(object, zone, game);
                        if (useableAbilities != null && useableAbilities.size() > 0) {
                            activateAbility(useableAbilities, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        updateGameStatePriority("chooseTriggeredAbility", game);
        while (!abort) {
            game.fireSelectTargetEvent(playerId, "Pick triggered ability (goes to the stack first)", abilities);
            waitForResponse();
            if (response.getUUID() != null) {
                for (TriggeredAbility ability: abilities) {
                    if (ability.getId().equals(response.getUUID()))
                        return ability;
                }
            }
        }
        return null;
    }

    @Override
    public boolean playMana(ManaCost unpaid, Game game) {
        updateGameStatePriority("playMana", game);
        game.firePlayManaEvent(playerId, "Pay " + unpaid.getText());
        waitForResponse();
        if (response.getBoolean() != null) {
            return false;
        } else if (response.getUUID() != null) {
            playManaAbilities(unpaid, game);
        } else if (response.getString() != null && response.getString().equals("special")) {
            if (unpaid instanceof ManaCostsImpl) {
                ManaCostsImpl<ManaCost> costs = (ManaCostsImpl<ManaCost>) unpaid;
                for (ManaCost cost : costs.getUnpaid()) {
                    if (cost instanceof PhyrexianManaCost) {
                        PhyrexianManaCost ph = (PhyrexianManaCost)cost;
                        if (ph.canPay(null, playerId, game)) {
                            ((PhyrexianManaCost)cost).pay(null, game, null, playerId, false);
                        }
                        break;
                    }
                }
            }
        } 
        return true;
    }

    /**
     * Gets the amount of mana the player want to spent for a x spell
     * @param min
     * @param max
     * @param message
     * @param game
     * @return
     */
    @Override
    public int announceXMana(int min, int max, String message, Game game, Ability ability) {
        updateGameStatePriority("announceXMana", game);
        game.fireGetAmountEvent(playerId, message, min, max);
        waitForIntegerResponse();
        return response.getInteger();
    }

    protected void playManaAbilities(ManaCost unpaid, Game game) {
        updateGameStatePriority("playManaAbilities", game);
        MageObject object = game.getObject(response.getUUID());
        if (object == null) {
            return;
        }        
        Zone zone = game.getState().getZone(object.getId());
        if (zone != null) {
            LinkedHashMap<UUID, ManaAbility> useableAbilities = getUseableManaAbilities(object, zone, game);
            if (useableAbilities != null && useableAbilities.size() > 0) {
                useableAbilities = ManaUtil.tryToAutoPay(unpaid, useableAbilities);
                activateAbility(useableAbilities, game);
            }
        }
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        updateGameStatePriority("selectAttackers", game);
        FilterCreatureForCombat filter = filterCreatureForCombat.copy();
        filter.add(new ControllerIdPredicate(attackingPlayerId));
        while (!abort) {
            if (passedAllTurns /*|| passedTurn*/) {
                return;
            }
            game.fireSelectEvent(playerId, "Select attackers");
            waitForResponse();
            if (response.getBoolean() != null) {
                return;
            } else if (response.getInteger() != null) {
                //if (response.getInteger() == -9999) {
                //    passedAllTurns = true;
                //}
                //passedTurn = true;
                return;
            } else if (response.getUUID() != null) {
                Permanent attacker = game.getPermanent(response.getUUID());
                if (attacker != null) {
                    if (filterCreatureForCombat.match(attacker, null, playerId, game)) {
                        selectDefender(game.getCombat().getDefenders(), attacker.getId(), game);
                    }
                    else if (filterAttack.match(attacker, null, playerId, game) && game.getStack().isEmpty()) {
                        removeAttackerIfPossible(game, attacker);
                    }
                }
             }
        }
    }

    private void removeAttackerIfPossible(Game game, Permanent attacker) {
        for (RequirementEffect effect : game.getContinuousEffects().getApplicableRequirementEffects(attacker, game)) {
            if (effect.mustAttack(game)) {
                return; // we can't cancel attacking
            }
        }
        game.getCombat().removeAttacker(attacker.getId(), game);
    }

    /**
     * Selects a defender for an attacker and adds the attacker to combat
     *
     * @param defenders - list of possible defender
     * @param attackerId - UUID of attacker
     * @param game
     * @return
     */
    protected boolean selectDefender(Set<UUID> defenders, UUID attackerId, Game game) {
        if (defenders.size() == 1) {
            declareAttacker(attackerId, defenders.iterator().next(), game);
            return true;
        }
        else {
            TargetDefender target = new TargetDefender(defenders, attackerId);
            if (chooseTarget(Outcome.Damage, target, null, game)) {
                declareAttacker(attackerId, response.getUUID(), game);
                return true;
            }
        }
        return false;
    }

    @Override
    public void selectBlockers(Game game, UUID defendingPlayerId) {
        updateGameStatePriority("selectBlockers", game);
        FilterCreatureForCombat filter = filterCreatureForCombat.copy();
        filter.add(new ControllerIdPredicate(defendingPlayerId));
        while (!abort) {
            game.fireSelectEvent(playerId, "Select blockers");
            waitForResponse();
            if (response.getBoolean() != null) {
                return;
            } else if (response.getInteger() != null) {
                //if (response.getInteger() == -9999) {
                //    passedAllTurns = true;
                //}
                //passedTurn = true;
                return;
            } else if (response.getUUID() != null) {
                Permanent blocker = game.getPermanent(response.getUUID());
                if (blocker != null) {
                    if (filter.match(blocker, null, playerId, game)) {
                        selectCombatGroup(blocker.getId(), game);
                    }
                    else if (filterBlock.match(blocker, null, playerId, game) && game.getStack().isEmpty()) {
//                        if (game.getState().getTriggered().isEmpty() && game.getState().getDelayed().isEmpty())
                            game.getCombat().removeBlocker(blocker.getId(), game);
                    }
                }
            }
        }
    }

    @Override
    public UUID chooseAttackerOrder(List<Permanent> attackers, Game game) {
        updateGameStatePriority("chooseAttackerOrder", game);
        while (!abort) {
            game.fireSelectTargetEvent(playerId, "Pick attacker", attackers, true);
            waitForResponse();
            if (response.getUUID() != null) {
                for (Permanent perm: attackers) {
                    if (perm.getId().equals(response.getUUID()))
                        return perm.getId();
                }
            }
        }
        return null;
    }


    @Override
    public UUID chooseBlockerOrder(List<Permanent> blockers, Game game) {
        updateGameStatePriority("chooseBlockerOrder", game);
        while (!abort) {
            game.fireSelectTargetEvent(playerId, "Pick blocker", blockers, true);
            waitForResponse();
            if (response.getUUID() != null) {
                for (Permanent perm: blockers) {
                    if (perm.getId().equals(response.getUUID()))
                        return perm.getId();
                }
            }
        }
        return null;
    }

    protected void selectCombatGroup(UUID blockerId, Game game) {
        updateGameStatePriority("selectCombatGroup", game);
        TargetAttackingCreature target = new TargetAttackingCreature();
        game.fireSelectTargetEvent(playerId, "Select attacker to block", target.possibleTargets(null, playerId, game), target.isRequired(), null);
        waitForResponse();
        if (response.getBoolean() != null) {
            // do nothing
        } else if (response.getUUID() != null) {
            declareBlocker(blockerId, response.getUUID(), game);
        }
    }

    @Override
    public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID sourceId, Game game) {
        updateGameStatePriority("assignDamage", game);
        int remainingDamage = damage;
        while (remainingDamage > 0) {
            Target target = new TargetCreatureOrPlayer();
            if (singleTargetName != null) target.setTargetName(singleTargetName);
            choose(Outcome.Damage, target, sourceId, game);
            if (targets.isEmpty() || targets.contains(target.getFirstTarget())) {
                int damageAmount = getAmount(0, remainingDamage, "Select amount", game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.damage(damageAmount, sourceId, game, true, false);
                    remainingDamage -= damageAmount;
                }
                else {
                    Player player = game.getPlayer(target.getFirstTarget());
                    if (player != null) {
                        player.damage(damageAmount, sourceId, game, false, true);
                        remainingDamage -= damageAmount;
                    }
                }
            }
        }
    }

    @Override
    public int getAmount(int min, int max, String message, Game game) {
        updateGameStatePriority("getAmount", game);
        game.fireGetAmountEvent(playerId, message, min, max);
        waitForIntegerResponse();
        return response.getInteger();
    }

    @Override
    public void sideboard(Match match, Deck deck) {
        match.fireSideboardEvent(playerId, deck);
    }

    @Override
    public void construct(Tournament tournament, Deck deck) {
        tournament.fireConstructEvent(playerId);
    }

    @Override
    public void pickCard(List<Card> cards, Deck deck, Draft draft) {
        draft.firePickCardEvent(playerId);
    }

    protected void specialAction(Game game) {
        updateGameStatePriority("specialAction", game);
        LinkedHashMap<UUID, SpecialAction> specialActions = game.getState().getSpecialActions().getControlledBy(playerId);
        game.fireGetChoiceEvent(playerId, name, new ArrayList<SpecialAction>(specialActions.values()));
        waitForResponse();
        if (response.getUUID() != null) {
            if (specialActions.containsKey(response.getUUID()))
                activateAbility(specialActions.get(response.getUUID()), game);
        }
    }

    protected void activateAbility(LinkedHashMap<UUID, ? extends ActivatedAbility> abilities, Game game) {
        updateGameStatePriority("activateAbility", game);
        if (abilities.size() == 1) {
            ActivatedAbility ability = abilities.values().iterator().next();
            if (ability.getTargets().size() != 0 || !(ability.getCosts().size() == 1 && ability.getCosts().get(0) instanceof SacrificeSourceCost)) {
                activateAbility(ability, game);
                return;
            }
        }
        game.fireGetChoiceEvent(playerId, name, new ArrayList<ActivatedAbility>(abilities.values()));
        waitForResponse();
        if (response.getUUID() != null) {
            if (abilities.containsKey(response.getUUID()))
                activateAbility(abilities.get(response.getUUID()), game);
        }
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        updateGameStatePriority("chooseMode", game);
        if (modes.size() > 1) {
            MageObject obj = game.getObject(source.getSourceId());
            Map<UUID, String> modeMap = new LinkedHashMap<UUID, String>();
            for (Mode mode: modes.values()) {
                String modeText = mode.getEffects().getText(mode);
                if (obj != null)
                    modeText = modeText.replace("{source}", obj.getName());
                modeMap.put(mode.getId(), modeText);
            }
            game.fireGetModeEvent(playerId, "Choose Mode", modeMap);
            waitForResponse();
            if (response.getUUID() != null) {
                for (Mode mode: modes.values()) {
                    if (mode.getId().equals(response.getUUID()))
                        return mode;
                }
            }
            return null;
        }
        return modes.getMode();
    }

    @Override
    public boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game) {
        updateGameStatePriority("choosePile", game);
        game.fireChoosePileEvent(playerId, message, pile1, pile2);
        waitForBooleanResponse();
        if (!abort)
            return response.getBoolean();
        return false;
    }

    @Override
    public void setResponseString(String responseString) {
        synchronized(response) {
            response.setString(responseString);
            response.notify();
            log.debug("Got response string from player: " + getId());
        }
    }

    @Override
    public void setResponseUUID(UUID responseUUID) {
        synchronized(response) {
            response.setUUID(responseUUID);
            response.notify();
            log.debug("Got response UUID from player: " + getId());
        }
    }

    @Override
    public void setResponseBoolean(Boolean responseBoolean) {
        synchronized(response) {
            response.setBoolean(responseBoolean);
            response.notify();
            log.debug("Got response boolean from player: " + getId());
        }
    }

    @Override
    public void setResponseInteger(Integer responseInteger) {
        synchronized(response) {
            response.setInteger(responseInteger);
            response.notify();
            log.debug("Got response integer from player: " + getId());
        }
    }

    @Override
    public void abort() {
        abort = true;
        synchronized(response) {
            response.notify();
            log.debug("Got cancel action from player: " + getId());
        }
    }

    @Override
    public void skip() {
        synchronized(response) {
            response.setInteger(0);
            response.notify();
            log.debug("Got skip action from player: " + getId());
        }
    }

    @Override
    public HumanPlayer copy() {
        return new HumanPlayer(this);
    }

    protected void updateGameStatePriority(String methodName, Game game) {
        log.debug("Setting game priority to " + getId() + " [" + methodName + "]");
        game.getState().setPriorityPlayerId(getId());
    }
}
