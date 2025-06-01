package mage.player.ai;

import mage.MageObject;
import mage.abilities.*;
import mage.abilities.costs.mana.ManaCost;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.constants.ManaType;
import mage.constants.MultiAmountType;
import mage.constants.Outcome;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.draft.Draft;
import mage.game.match.Match;
import mage.game.tournament.Tournament;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.util.MultiAmountMessage;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * AI player that can be taken under control by another player (AI or human).
 * <p>
 * Under control logic on choose dialog (under human):
 * - create fake human player and assign it to real human data transfer object (for income answers);
 * - call choose dialog from fake human (e.g. send choose data to real player);
 * - game will process all sending and answering logic as "human under human" logic;
 * - return choose dialog result without AI code processing;
 *
 * @author JayDi85
 */
public class ComputerPlayerControllableProxy extends ComputerPlayer7 {

    private static final Logger logger = Logger.getLogger(ComputerPlayerControllableProxy.class);

    Player lastControllingPlayer = null;

    public ComputerPlayerControllableProxy(String name, RangeOfInfluence range, int skill) {
        super(name, range, skill);
    }

    public ComputerPlayerControllableProxy(final ComputerPlayerControllableProxy player) {
        super(player);
        this.lastControllingPlayer = player;
    }

    @Override
    public ComputerPlayerControllableProxy copy() {
        return new ComputerPlayerControllableProxy(this);
    }

    private boolean isUnderMe(Game game) {
        return game.isSimulation() || this.isGameUnderControl();
    }

    private Player getControllingPlayer(Game game) {
        Player player = game.getPlayer(this.getTurnControlledBy());
        this.lastControllingPlayer = player.prepareControllableProxy(this);
        return this.lastControllingPlayer;
    }

    @Override
    public void setResponseString(String responseString) {
        if (this.lastControllingPlayer != null) {
            this.lastControllingPlayer.setResponseString(responseString);
        }
    }

    @Override
    public void setResponseManaType(UUID manaTypePlayerId, ManaType responseManaType) {
        if (this.lastControllingPlayer != null) {
            this.lastControllingPlayer.setResponseManaType(manaTypePlayerId, responseManaType);
        }
    }

    @Override
    public void setResponseUUID(UUID responseUUID) {
        if (this.lastControllingPlayer != null) {
            this.lastControllingPlayer.setResponseUUID(responseUUID);
        }
    }

    @Override
    public void setResponseBoolean(Boolean responseBoolean) {
        if (this.lastControllingPlayer != null) {
            this.lastControllingPlayer.setResponseBoolean(responseBoolean);
        }
    }

    @Override
    public void setResponseInteger(Integer responseInteger) {
        if (this.lastControllingPlayer != null) {
            this.lastControllingPlayer.setResponseInteger(responseInteger);
        }
    }

    @Override
    public void signalPlayerCheat() {
        if (this.lastControllingPlayer != null) {
            this.lastControllingPlayer.signalPlayerCheat();
        }
    }

    @Override
    public void signalPlayerConcede(boolean stopCurrentChooseDialog) {
        if (this.lastControllingPlayer != null) {
            this.lastControllingPlayer.signalPlayerConcede(stopCurrentChooseDialog);
        }
    }

    @Override
    public boolean priority(Game game) {
        if (isUnderMe(game)) {
            return super.priority(game);
        } else {
            Player player = getControllingPlayer(game);
            try {
                return player.priority(game);
            } finally {
                this.passed = player.isPassed(); // TODO: wtf, no needs?
            }
        }
    }

    @Override
    public boolean chooseMulligan(Game game) {
        if (isUnderMe(game)) {
            return super.chooseMulligan(game);
        } else {
            return getControllingPlayer(game).chooseMulligan(game);
        }
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Ability source, Game game) {
        if (isUnderMe(game)) {
            return super.chooseUse(outcome, message, source, game);
        } else {
            return getControllingPlayer(game).chooseUse(outcome, message, source, game);
        }
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, String secondMessage, String trueText, String falseText, Ability source, Game game) {
        if (isUnderMe(game)) {
            return super.chooseUse(outcome, message, secondMessage, trueText, falseText, source, game);
        } else {
            return getControllingPlayer(game).chooseUse(outcome, message, secondMessage, trueText, falseText, source, game);
        }
    }

    @Override
    public int chooseReplacementEffect(Map<String, String> effectsMap, Map<String, MageObject> objectsMap, Game game) {
        if (isUnderMe(game)) {
            return super.chooseReplacementEffect(effectsMap, objectsMap, game);
        } else {
            return getControllingPlayer(game).chooseReplacementEffect(effectsMap, objectsMap, game);
        }
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        if (isUnderMe(game)) {
            return super.choose(outcome, choice, game);
        } else {
            return getControllingPlayer(game).choose(outcome, choice, game);
        }
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game) {
        if (isUnderMe(game)) {
            return super.choose(outcome, target, source, game);
        } else {
            return getControllingPlayer(game).choose(outcome, target, source, game);
        }
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game, Map<String, Serializable> options) {
        if (isUnderMe(game)) {
            return super.choose(outcome, target, source, game, options);
        } else {
            return getControllingPlayer(game).choose(outcome, target, source, game, options);
        }
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        if (isUnderMe(game)) {
            return super.chooseTarget(outcome, target, source, game);
        } else {
            return getControllingPlayer(game).chooseTarget(outcome, target, source, game);
        }
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        if (isUnderMe(game)) {
            return super.choose(outcome, cards, target, source, game);
        } else {
            return getControllingPlayer(game).choose(outcome, cards, target, source, game);
        }
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        if (isUnderMe(game)) {
            return super.chooseTarget(outcome, cards, target, source, game);
        } else {
            return getControllingPlayer(game).chooseTarget(outcome, cards, target, source, game);
        }
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
        if (isUnderMe(game)) {
            return super.chooseTargetAmount(outcome, target, source, game);
        } else {
            return getControllingPlayer(game).chooseTargetAmount(outcome, target, source, game);
        }
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(java.util.List<TriggeredAbility> abilities, Game game) {
        if (isUnderMe(game)) {
            return super.chooseTriggeredAbility(abilities, game);
        } else {
            return getControllingPlayer(game).chooseTriggeredAbility(abilities, game);
        }
    }

    @Override
    public boolean playMana(Ability abilityToCast, ManaCost unpaid, String promptText, Game game) {
        if (isUnderMe(game)) {
            return super.playMana(abilityToCast, unpaid, promptText, game);
        } else {
            return getControllingPlayer(game).playMana(abilityToCast, unpaid, promptText, game);
        }
    }

    @Override
    public int announceX(int min, int max, String message, Game game, Ability source, boolean isManaPay) {
        if (isUnderMe(game)) {
            return super.announceX(min, max, message, game, source, isManaPay);
        } else {
            return getControllingPlayer(game).announceX(min, max, message, game, source, isManaPay);
        }
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        if (isUnderMe(game)) {
            super.selectAttackers(game, attackingPlayerId);
        } else {
            getControllingPlayer(game).selectAttackers(game, attackingPlayerId);
        }
    }

    @Override
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {
        if (isUnderMe(game)) {
            super.selectBlockers(source, game, defendingPlayerId);
        } else {
            getControllingPlayer(game).selectBlockers(source, game, defendingPlayerId);
        }
    }

    @Override
    public int getAmount(int min, int max, String message, Ability source, Game game) {
        if (isUnderMe(game)) {
            return super.getAmount(min, max, message, source, game);
        } else {
            return getControllingPlayer(game).getAmount(min, max, message, source, game);
        }
    }

    @Override
    public List<Integer> getMultiAmountWithIndividualConstraints(
            Outcome outcome,
            List<MultiAmountMessage> messages,
            int totalMin,
            int totalMax,
            MultiAmountType type,
            Game game
    ) {
        if (isUnderMe(game)) {
            return super.getMultiAmountWithIndividualConstraints(outcome, messages, totalMin, totalMax, type, game);
        } else {
            return getControllingPlayer(game).getMultiAmountWithIndividualConstraints(outcome, messages, totalMin, totalMax, type, game);
        }
    }

    @Override
    public void sideboard(Match match, Deck deck) {
        super.sideboard(match, deck);
    }

    @Override
    public void construct(Tournament tournament, Deck deck) {
        super.construct(tournament, deck);
    }

    @Override
    public void pickCard(java.util.List<Card> cards, Deck deck, Draft draft) {
        super.pickCard(cards, deck, draft);
    }

    @Override
    public boolean activateAbility(ActivatedAbility ability, Game game) {
        // TODO: need research, see HumanPlayer's code
        return super.activateAbility(ability, game);
    }

    @Override
    public SpellAbility chooseAbilityForCast(Card card, Game game, boolean noMana) {
        if (isUnderMe(game)) {
            return super.chooseAbilityForCast(card, game, noMana);
        } else {
            return getControllingPlayer(game).chooseAbilityForCast(card, game, noMana);
        }
    }

    @Override
    public ActivatedAbility chooseLandOrSpellAbility(Card card, Game game, boolean noMana) {
        if (isUnderMe(game)) {
            return super.chooseLandOrSpellAbility(card, game, noMana);
        } else {
            return getControllingPlayer(game).chooseLandOrSpellAbility(card, game, noMana);
        }
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        if (isUnderMe(game)) {
            return super.chooseMode(modes, source, game);
        } else {
            return getControllingPlayer(game).chooseMode(modes, source, game);
        }
    }

    @Override
    public boolean choosePile(Outcome outcome, String message, java.util.List<? extends Card> pile1, java.util.List<? extends Card> pile2, Game game) {
        if (isUnderMe(game)) {
            return super.choosePile(outcome, message, pile1, pile2, game);
        } else {
            return getControllingPlayer(game).choosePile(outcome, message, pile1, pile2, game);
        }
    }

    @Override
    public void abort() {
        // TODO: need research, is it require real player call? Concede/leave/timeout works by default
        super.abort();
    }

    @Override
    public void skip() {
        // TODO: see abort comments above
        super.skip();
    }
}
