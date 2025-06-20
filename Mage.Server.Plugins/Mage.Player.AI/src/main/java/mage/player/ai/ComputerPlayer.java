package mage.player.ai;

import mage.*;
import mage.abilities.*;
import mage.abilities.costs.mana.*;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.*;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.RateCard;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidator;
import mage.cards.decks.DeckValidatorFactory;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureForCombatBlock;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.draft.Draft;
import mage.game.events.GameEvent;
import mage.game.match.Match;
import mage.game.permanent.Permanent;
import mage.game.tournament.Tournament;
import mage.player.ai.simulators.CombatGroupSimulator;
import mage.player.ai.simulators.CombatSimulator;
import mage.player.ai.simulators.CreatureSimulator;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.players.PlayerImpl;
import mage.players.net.UserData;
import mage.players.net.UserGroup;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.util.*;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

/**
 * AI: basic server side bot with simple actions support (game, draft, construction/sideboarding)
 * <p>
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class ComputerPlayer extends PlayerImpl {

    private static final Logger log = Logger.getLogger(ComputerPlayer.class);

    protected static final int PASSIVITY_PENALTY = 5; // Penalty value for doing nothing if some actions are available

    // debug only: set TRUE to debug simulation's code/games (on false sim thread will be stopped after few secs by timeout)
    protected static final boolean COMPUTER_DISABLE_TIMEOUT_IN_GAME_SIMULATIONS = true; // DebugUtil.AI_ENABLE_DEBUG_MODE;

    // AI agents uses game simulation thread for all calcs and it's high CPU consumption
    // More AI threads - more parallel AI games can be calculate
    // If you catch errors like ConcurrentModificationException, then AI implementation works with wrong data
    // (e.g. with original game instead copy) or AI use wrong logic (one sim result depends on another sim result)
    // How-to use:
    // * 1 for debug or stable
    // * 5 for good performance on average computer
    // * use yours CPU cores for best performance
    // TODO: add server config to control max AI threads (with CPU cores by default)
    // TODO: rework AI implementation to use multiple sims calculation instead one by one
    final static int COMPUTER_MAX_THREADS_FOR_SIMULATIONS = 1;//DebugUtil.AI_ENABLE_DEBUG_MODE ? 1 : 5;


    // TODO: delete after target rework
    private final transient Map<Mana, Card> unplayable = new TreeMap<>();
    private final transient List<Card> playableNonInstant = new ArrayList<>();
    private final transient List<Card> playableInstant = new ArrayList<>();
    private final transient List<ActivatedAbility> playableAbilities = new ArrayList<>();
    private final transient List<PickedCard> pickedCards = new ArrayList<>();
    private final transient List<ColoredManaSymbol> chosenColors = new ArrayList<>();

    // keep current paying cost info for choose dialogs
    // mana abilities must ask payment too, so keep full chain
    // TODO: make sure it thread safe for AI simulations (all transient fields above and bottom)
    private final transient Map<UUID, ManaCost> lastUnpaidMana = new LinkedHashMap<>();

    // For stopping infinite loops when trying to pay Phyrexian mana when the player can't spend life and no other sources are available
    private transient boolean alreadyTryingToPayPhyrexian;

    private transient long lastThinkTime = 0; // time in ms for last AI actions calc

    public ComputerPlayer(String name, RangeOfInfluence range) {
        super(name, range);
        human = false;
        userData = UserData.getDefaultUserDataView();
        userData.setAvatarId(64);
        userData.setGroupId(UserGroup.COMPUTER.getGroupId());
        userData.setFlagName("computer.png");
    }

    protected ComputerPlayer(UUID id) {
        super(id);
        human = false;
        userData = UserData.getDefaultUserDataView();
        userData.setAvatarId(64);
        userData.setGroupId(UserGroup.COMPUTER.getGroupId());
        userData.setFlagName("computer.png");
    }

    public ComputerPlayer(final ComputerPlayer player) {
        super(player);
    }

    @Override
    public boolean chooseMulligan(Game game) {
        if (hand.size() < 6
                || isTestsMode() // ignore mulligan in tests
                || game.getClass().getName().contains("Momir") // ignore mulligan in Momir games
        ) {
            return false;
        }
        Set<Card> lands = hand.getCards(new FilterLandCard(), game);
        return lands.size() < 2
                || lands.size() > hand.size() - 2;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game) {
        return choose(outcome, target, source, game, null);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game, Map<String, Serializable> options) {
        return makeChoice(outcome, target, source, game, null);
    }

    /**
     * Default choice logic for any choose dialogs due effect's outcome and possible target priority
     */
    private boolean makeChoice(Outcome outcome, Target target, Ability source, Game game, Cards fromCards) {
        // choose itself for starting player all the time
        if (target.getMessage(game).equals("Select a starting player")) {
            target.add(this.getId(), game);
            return true;
        }

        // nothing to choose
        if (fromCards != null && fromCards.isEmpty()) {
            return false;
        }

        UUID abilityControllerId = target.getAffectedAbilityControllerId(getId());

        // nothing to choose, e.g. X=0
        if (target.isChoiceCompleted(abilityControllerId, source, game)) {
            return false;
        }

        // default logic for any targets
        PossibleTargetsSelector possibleTargetsSelector = new PossibleTargetsSelector(outcome, target, abilityControllerId, source, game);
        possibleTargetsSelector.findNewTargets(fromCards);
        // good targets -- choose as much as possible
        for (MageItem item : possibleTargetsSelector.getGoodTargets()) {
            target.add(item.getId(), game);
            if (target.isChoiceCompleted(abilityControllerId, source, game)) {
                return true;
            }
        }
        // bad targets -- choose as low as possible
        for (MageItem item : possibleTargetsSelector.getBadTargets()) {
            if (target.isChosen(game)) {
                break;
            }
            target.add(item.getId(), game);
        }

        return target.isChosen(game) && !target.getTargets().isEmpty();
    }

    /**
     * Default choice logic for X or amount values
     */
    private int makeChoiceAmount(int min, int max, Game game, Ability source, boolean isManaPay) {
        // fast calc on nothing to choose
        if (min >= max) {
            return min;
        }

        // TODO: add good/bad effects support
        // TODO: add simple game simulations like declare blocker (need to find only workable payment)?
        // TODO: remove random logic or make it more stable (e.g. use same value in same game cycle)

        // protection from too big values
        int realMin = min;
        int realMax = max;
        if (max == Integer.MAX_VALUE) {
            realMax = Math.max(realMin, 10); // AI don't need huge values for X, cause can't use infinite combos
        }

        int xValue;
        if (isManaPay) {
            // as X mana payment - due available mana
            xValue = Math.max(0, getAvailableManaProducers(game).size() - source.getManaCostsToPay().getUnpaid().manaValue());
        } else {
            // as X actions
            xValue = RandomUtil.nextInt(realMax + 1);
        }

        if (xValue > realMax) {
            xValue = realMax;
        }
        if (xValue < realMin) {
            xValue = realMin;
        }

        return xValue;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        return makeChoice(outcome, target, source, game, null);
    }

    @Deprecated // TODO: replace by source only version
    protected Card selectCard(UUID abilityControllerId, List<Card> cards, Outcome outcome, Target target, Game game) {
        return selectCardInner(abilityControllerId, cards, outcome, target, null, game);
    }

    /**
     * @param targetingSource null on non-target choice like choose and source on targeting choice like chooseTarget
     */
    protected Card selectCardInner(UUID abilityControllerId, List<Card> cards, Outcome outcome, Target target, Ability targetingSource, Game game) {
        Card card;
        while (!cards.isEmpty()) {
            if (outcome.isGood()) {
                card = selectBestCardInner(cards, Collections.emptyList(), target, targetingSource, game);
            } else {
                card = selectWorstCardInner(cards, Collections.emptyList(), target, targetingSource, game);
            }
            if (!target.getTargets().contains(card.getId())) {
                if (targetingSource != null) {
                    if (target.canTarget(abilityControllerId, card.getId(), targetingSource, game)) {
                        return card;
                    }
                } else {
                    return card;
                }
            }
            cards.remove(card);  // TODO: research parent code - is it depends on original list? Can be bugged
        }
        return null;
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {

        // nothing to choose, e.g. X=0
        target.prepareAmount(source, game);
        if (target.getAmountRemaining() <= 0) {
            return false;
        }
        if (target.getMaxNumberOfTargets() == 0 && target.getMinNumberOfTargets() == 0) {
            return false;
        }

        UUID abilityControllerId = target.getAffectedAbilityControllerId(getId());

        // nothing to choose, e.g. X=0
        if (target.isChoiceCompleted(abilityControllerId, source, game)) {
            return false;
        }

        PossibleTargetsSelector possibleTargetsSelector = new PossibleTargetsSelector(outcome, target, abilityControllerId, source, game);
        possibleTargetsSelector.findNewTargets(null);

        // nothing to choose, e.g. no valid targets
        if (!possibleTargetsSelector.hasAnyTargets()) {
            return false;
        }

        // KILL PRIORITY
        if (outcome == Outcome.Damage) {
            // opponent first
            for (MageItem item : possibleTargetsSelector.getGoodTargets()) {
                if (target.getAmountRemaining() <= 0) {
                    break;
                }
                if (target.contains(item.getId()) || !(item instanceof Player)) {
                    continue;
                }
                int leftLife = PossibleTargetsComparator.getLifeForDamage(item, game);
                if (leftLife > 0 && leftLife <= target.getAmountRemaining()) {
                    target.addTarget(item.getId(), leftLife, source, game);
                    if (target.isChoiceCompleted(abilityControllerId, source, game)) {
                        return true;
                    }
                }
            }

            // opponent's creatures second
            for (MageItem item : possibleTargetsSelector.getGoodTargets()) {
                if (target.getAmountRemaining() <= 0) {
                    break;
                }
                if (target.contains(item.getId()) || (item instanceof Player)) {
                    continue;
                }
                int leftLife = PossibleTargetsComparator.getLifeForDamage(item, game);
                if (leftLife > 0 && leftLife <= target.getAmountRemaining()) {
                    target.addTarget(item.getId(), leftLife, source, game);
                    if (target.isChoiceCompleted(abilityControllerId, source, game)) {
                        return true;
                    }
                }
            }

            // opponent's any
            for (MageItem item : possibleTargetsSelector.getGoodTargets()) {
                if (target.getAmountRemaining() <= 0) {
                    break;
                }
                if (target.contains(item.getId())) {
                    continue;
                }
                target.addTarget(item.getId(), target.getAmountRemaining(), source, game);
                if (target.isChoiceCompleted(abilityControllerId, source, game)) {
                    return true;
                }
            }

            // own - non-killable
            for (MageItem item : possibleTargetsSelector.getBadTargets()) {
                if (target.getAmountRemaining() <= 0) {
                    break;
                }
                if (target.contains(item.getId())) {
                    continue;
                }
                // stop as fast as possible on bad outcome
                if (target.isChosen(game)) {
                    return !target.getTargets().isEmpty();
                }
                int leftLife = PossibleTargetsComparator.getLifeForDamage(item, game);
                if (leftLife > 1) {
                    target.addTarget(item.getId(), Math.min(leftLife - 1, target.getAmountRemaining()), source, game);
                    if (target.isChoiceCompleted(abilityControllerId, source, game)) {
                        return true;
                    }
                }
            }

            // own - any
            for (MageItem item : possibleTargetsSelector.getBadTargets()) {
                if (target.getAmountRemaining() <= 0) {
                    break;
                }
                if (target.contains(item.getId())) {
                    continue;
                }
                // stop as fast as possible on bad outcome
                if (target.isChosen(game)) {
                    return !target.getTargets().isEmpty();
                }
                target.addTarget(item.getId(), target.getAmountRemaining(), source, game);
                if (target.isChoiceCompleted(abilityControllerId, source, game)) {
                    return true;
                }
            }

            return target.isChosen(game);
        }

        // non-damage effect like counters - give all to first valid item
        for (MageItem item : possibleTargetsSelector.getGoodTargets()) {
            if (target.getAmountRemaining() <= 0) {
                break;
            }
            if (target.contains(item.getId())) {
                continue;
            }
            target.addTarget(item.getId(), target.getAmountRemaining(), source, game);
            if (target.isChoiceCompleted(abilityControllerId, source, game)) {
                return true;
            }
        }
        for (MageItem item : possibleTargetsSelector.getBadTargets()) {
            if (target.getAmountRemaining() <= 0) {
                break;
            }
            if (target.contains(item.getId())) {
                continue;
            }
            // stop as fast as possible on bad outcome
            if (target.isChosen(game)) {
                return !target.getTargets().isEmpty();
            }
            target.addTarget(item.getId(), target.getAmountRemaining(), source, game);
            if (target.isChoiceCompleted(abilityControllerId, source, game)) {
                return true;
            }
        }

        return target.isChosen(game) && !target.getTargets().isEmpty();
    }

    @Override
    public boolean priority(Game game) {
        game.resumeTimer(getTurnControlledBy());
        boolean result = priorityPlay(game);
        game.pauseTimer(getTurnControlledBy());
        return result;
    }

    private boolean priorityPlay(Game game) {
        // TODO: simplify and delete, never called in real game due ComputerPlayer7's simulations usage
        UUID opponentId = getRandomOpponent(game);
        if (game.isActivePlayer(playerId)) {
            if (game.isMainPhase() && game.getStack().isEmpty()) {
                playLand(game);
            }
            switch (game.getTurnStepType()) {
                case UPKEEP:
                    // TODO: is it needs here? Need research (e.g. for better choose in upkeep triggers)?
                    findPlayables(game);
                    break;
                case DRAW:
                    break;
                case PRECOMBAT_MAIN:
                    findPlayables(game);
                    if (!playableAbilities.isEmpty()) {
                        for (ActivatedAbility ability : playableAbilities) {
                            if (ability.canActivate(playerId, game).canActivate()) {
                                if (ability.getEffects().hasOutcome(ability, Outcome.PutLandInPlay)) {
                                    if (this.activateAbility(ability, game)) {
                                        return true;
                                    }
                                }
                                if (ability.getEffects().hasOutcome(ability, Outcome.PutCreatureInPlay)) {
                                    if (getOpponentBlockers(opponentId, game).size() <= 1) {
                                        if (this.activateAbility(ability, game)) {
                                            return true;
                                        }
                                    }
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
                        if (!playableNonInstant.isEmpty()) {
                            for (Card card : playableNonInstant) {
                                if (card.getSpellAbility().canActivate(playerId, game).canActivate()) {
                                    if (this.activateAbility(card.getSpellAbility(), game)) {
                                        return true;
                                    }
                                }
                            }
                        }
                        if (!playableAbilities.isEmpty()) {
                            for (ActivatedAbility ability : playableAbilities) {
                                if (ability.canActivate(playerId, game).canActivate()) {
                                    if (!(ability.getEffects().get(0) instanceof BecomesCreatureSourceEffect)) {
                                        if (this.activateAbility(ability, game)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
        } else {
            //respond to opponent events
            switch (game.getTurnStepType()) {
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
        pass(game);
        return true;
    } // end priorityPlay method

    protected void playLand(Game game) {
        Set<Card> lands = new LinkedHashSet<>();
        for (Card landCard : hand.getCards(new FilterLandCard(), game)) {
            // remove lands that can not be played
            boolean canPlay = false;
            for (Ability ability : landCard.getAbilities(game)) {
                if (ability instanceof PlayLandAbility) {
                    if (!game.getContinuousEffects().preventedByRuleModification(GameEvent.getEvent(GameEvent.EventType.PLAY_LAND, landCard.getId(), ability, playerId), null, game, true)) {
                        canPlay = true;
                    }
                }
            }
            if (canPlay) {
                lands.add(landCard);
            }
        }
        while (!lands.isEmpty() && this.canPlayLand()) {
            if (lands.size() == 1) {
                this.playLand(lands.iterator().next(), game, false);
            } else {
                playALand(lands, game);
            }
        }
    }

    protected void playALand(Set<Card> lands, Game game) {
        //play a land that will allow us to play an unplayable
        for (Mana mana : unplayable.keySet()) {
            for (Card card : lands) {
                for (ActivatedManaAbilityImpl ability : card.getAbilities(game).getActivatedManaAbilities(Zone.BATTLEFIELD)) {
                    for (Mana netMana : ability.getNetMana(game)) {
                        if (netMana.enough(mana)) {
                            this.playLand(card, game, false);
                            lands.remove(card);
                            return;
                        }
                    }
                }
            }
        }
        //play a land that will get us closer to playing an unplayable
        for (Mana mana : unplayable.keySet()) {
            for (Card card : lands) {
                for (ActivatedManaAbilityImpl ability : card.getAbilities(game).getActivatedManaAbilities(Zone.BATTLEFIELD)) {
                    for (Mana netMana : ability.getNetMana(game)) {
                        if (mana.contains(netMana)) {
                            this.playLand(card, game, false);
                            lands.remove(card);
                            return;
                        }
                    }
                }
            }
        }
        //play first available land
        this.playLand(lands.iterator().next(), game, false);
        lands.remove(lands.iterator().next());
    }

    @Deprecated // TODO: delete after target rework
    protected void findPlayables(Game game) {
        playableInstant.clear();
        playableNonInstant.clear();
        unplayable.clear();
        playableAbilities.clear();
        Set<Card> nonLands = hand.getCards(new FilterNonlandCard(), game);
        ManaOptions available = getManaAvailable(game);
//        available.addMana(manaPool.getMana());

        for (Card card : nonLands) {
            ManaOptions options = card.getManaCost().getOptions();
            if (!card.getManaCost().getVariableCosts().isEmpty()) {
                //don't use variable mana costs unless there is at least 3 extra mana for X
                for (Mana option : options) {
                    option.add(Mana.GenericMana(3));
                }
            }
            for (Mana mana : options) {
                for (Mana avail : available) {
                    if (mana.enough(avail)) {
                        SpellAbility ability = card.getSpellAbility();
                        GameEvent castEvent = GameEvent.getEvent(GameEvent.EventType.CAST_SPELL, ability.getId(), ability, playerId);
                        castEvent.setZone(game.getState().getZone(card.getMainCard().getId()));
                        if (ability != null && ability.canActivate(playerId, game).canActivate()
                                && !game.getContinuousEffects().preventedByRuleModification(castEvent, ability, game, true)) {
                            if (card.isInstant(game)
                                    || card.hasAbility(FlashAbility.getInstance(), game)) {
                                playableInstant.add(card);
                            } else {
                                playableNonInstant.add(card);
                            }
                        }
                    } else if (!playableInstant.contains(card) && !playableNonInstant.contains(card)) {
                        unplayable.put(mana.needed(avail), card);
                    }
                }
            }
        }
        // TODO: wtf?! change to player.getPlayable
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            for (ActivatedAbility ability : permanent.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD)) {
                if (!ability.isManaActivatedAbility() && ability.canActivate(playerId, game).canActivate()) {
                    if (ability instanceof EquipAbility && permanent.getAttachedTo() != null) {
                        continue;
                    }
                    ManaOptions abilityOptions = ability.getManaCosts().getOptions();
                    if (!ability.getManaCosts().getVariableCosts().isEmpty()) {
                        //don't use variable mana costs unless there is at least 3 extra mana for X
                        for (Mana option : abilityOptions) {
                            option.add(Mana.GenericMana(3));
                        }
                    }
                    if (abilityOptions.isEmpty()) {
                        playableAbilities.add(ability);
                    } else {
                        for (Mana mana : abilityOptions) {
                            for (Mana avail : available) {
                                if (mana.enough(avail)) {
                                    playableAbilities.add(ability);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (Card card : graveyard.getCards(game)) {
            for (ActivatedAbility ability : card.getAbilities(game).getActivatedAbilities(Zone.GRAVEYARD)) {
                if (ability.canActivate(playerId, game).canActivate()) {
                    ManaOptions abilityOptions = ability.getManaCosts().getOptions();
                    if (abilityOptions.isEmpty()) {
                        playableAbilities.add(ability);
                    } else {
                        for (Mana mana : abilityOptions) {
                            for (Mana avail : available) {
                                if (mana.enough(avail)) {
                                    playableAbilities.add(ability);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean playMana(Ability ability, ManaCost unpaid, String promptText, Game game) {
        payManaMode = true;
        lastUnpaidMana.put(ability.getId(), unpaid.copy());
        try {
            return playManaHandling(ability, unpaid, game);
        } finally {

            lastUnpaidMana.remove(ability.getId());
            payManaMode = false;
        }
    }

    protected boolean playManaHandling(Ability ability, ManaCost unpaid, final Game game) {
//        log.info("paying for " + unpaid.getText());
        Set<ApprovingObject> approvingObjects = game.getContinuousEffects().asThough(ability.getSourceId(), AsThoughEffectType.SPEND_OTHER_MANA, ability, ability.getControllerId(), game);
        boolean hasApprovingObject = !approvingObjects.isEmpty();

        ManaCost cost;
        List<MageObject> producers;
        if (unpaid instanceof ManaCosts) {
            ManaCosts<ManaCost> manaCosts = (ManaCosts<ManaCost>) unpaid;
            cost = manaCosts.get(manaCosts.size() - 1);
            producers = getSortedProducers((ManaCosts) unpaid, game);
        } else {
            cost = unpaid;
            producers = this.getAvailableManaProducers(game);
            producers.addAll(this.getAvailableManaProducersWithCost(game));
        }

        // use fully compatible colored mana producers first
        for (MageObject mageObject : producers) {
            ManaAbility:
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                boolean canPayColoredMana = false;
                for (Mana mana : manaAbility.getNetMana(game)) {
                    // if mana ability can produce non-useful mana then ignore whole ability here (example: {R} or {G})
                    // (AI can't choose a good mana option, so make sure any selection option will be compatible with cost)
                    // AI support {Any} choice by lastUnpaidMana, so it can safety used in includesMana
                    if (!unpaid.getMana().includesMana(mana)) {
                        continue ManaAbility;
                    } else if (mana.getAny() > 0) {
                        throw new IllegalArgumentException("Wrong mana calculation: AI do not support color choosing from {Any}");
                    }
                    if (mana.countColored() > 0) {
                        canPayColoredMana = true;
                    }
                }
                // found compatible source - try to pay
                if (canPayColoredMana && (cost instanceof ColoredManaCost)) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana)) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (hasApprovingObject && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // use any other mana produces
        for (MageObject mageObject : producers) {
            // pay all colored costs first
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                if (cost instanceof ColoredManaCost) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana) || hasApprovingObject) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (hasApprovingObject && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
            // pay snow covered mana
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                if (cost instanceof SnowManaCost) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana) || hasApprovingObject) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (hasApprovingObject && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
            // pay colorless - more restrictive than hybrid (think of it like colored)
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                if (cost instanceof ColorlessManaCost) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana) || hasApprovingObject) {
                            if (netMana instanceof ConditionalMana
                                    && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (hasApprovingObject && !canUseAsThoughManaToPayManaCost(cost, ability, netMana,
                                    manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
            // then pay hybrid
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                if (cost instanceof HybridManaCost) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana) || hasApprovingObject) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (hasApprovingObject && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
            // then pay colorless hybrid - more restrictive than monohybrid
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                if (cost instanceof ColorlessHybridManaCost) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana) || hasApprovingObject) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (hasApprovingObject && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
            // then pay monohybrid
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                if (cost instanceof MonoHybridManaCost) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana) || hasApprovingObject) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (hasApprovingObject && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
            // finally pay generic
            for (ActivatedManaAbilityImpl manaAbility : getManaAbilitiesSortedByManaCount(mageObject, game)) {
                if (cost instanceof GenericManaCost) {
                    for (Mana netMana : manaAbility.getNetMana(game)) {
                        if (cost.testPay(netMana) || hasApprovingObject) {
                            if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                                continue;
                            }
                            if (hasApprovingObject && !canUseAsThoughManaToPayManaCost(cost, ability, netMana, manaAbility, mageObject, game)) {
                                continue;
                            }
                            if (activateAbility(manaAbility, game)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        if (alreadyTryingToPayPhyrexian) {
            return false;
        }

        // pay phyrexian life costs
        if (cost.isPhyrexian()) {
            alreadyTryingToPayPhyrexian = true;
            // TODO: make sure it's thread safe and protected from modifications (cost/unpaid can be shared between AI simulation threads?)
            boolean paidPhyrexian = cost.pay(ability, game, ability, playerId, false, null) || hasApprovingObject;
            alreadyTryingToPayPhyrexian = false;
            return paidPhyrexian;
        }

        // pay special mana like convoke cost (tap for pay)
        // GUI: user see "special" button while pay spell's cost
        // TODO: AI can't prioritize special mana types to pay, e.g. it will use first available
        SpecialAction specialAction = game.getState().getSpecialActions().getControlledBy(this.getId(), true).values()
                .stream()
                .findFirst()
                .orElse(null);
        ManaOptions specialMana = specialAction == null ? null : specialAction.getManaOptions(ability, game, unpaid);
        if (specialMana != null) {
            for (Mana netMana : specialMana) {
                if (cost.testPay(netMana) || hasApprovingObject) {
                    if (netMana instanceof ConditionalMana && !((ConditionalMana) netMana).apply(ability, game, getId(), cost)) {
                        continue;
                    }
                    if (activateAbility(specialAction, game)) {
                        return true;
                    }
                    // only one time try to pay to skip infinite AI loop
                    break;
                }
            }
        }

        return false;
    }

    boolean canUseAsThoughManaToPayManaCost(ManaCost checkCost, Ability abilityToPay, Mana manaOption, Ability manaAbility, MageObject manaProducer, Game game) {
        // asThoughMana can change producing mana type, so you must check it here
        // cause some effects adds additional checks in getAsThoughManaType (example: Draugr Necromancer with snow mana sources)

        // simulate real asThoughMana usage
        ManaPoolItem possiblePoolItem;
        if (manaOption instanceof ConditionalMana) {
            ConditionalMana conditionalNetMana = (ConditionalMana) manaOption;
            possiblePoolItem = new ManaPoolItem(
                    conditionalNetMana,
                    manaAbility.getSourceObject(game),
                    conditionalNetMana.getManaProducerOriginalId() != null ? conditionalNetMana.getManaProducerOriginalId() : manaAbility.getOriginalId()
            );
        } else {
            possiblePoolItem = new ManaPoolItem(
                    manaOption.getRed(),
                    manaOption.getGreen(),
                    manaOption.getBlue(),
                    manaOption.getWhite(),
                    manaOption.getBlack(),
                    manaOption.getGeneric() + manaOption.getColorless(),
                    manaProducer,
                    manaAbility.getOriginalId(),
                    manaOption.getFlag()
            );
        }

        // cost can contain multiple mana types, must check each type (is it possible to pay a cost)
        for (ManaType checkType : ManaUtil.getManaTypesInCost(checkCost)) {
            // affected asThoughMana effect must fit a checkType with pool mana
            ManaType possibleAsThoughPoolManaType = game.getContinuousEffects().asThoughMana(checkType, possiblePoolItem, abilityToPay.getSourceId(), abilityToPay, abilityToPay.getControllerId(), game);
            if (possibleAsThoughPoolManaType == null) {
                continue; // no affected asThough effects
            }
            boolean canPay;
            if (possibleAsThoughPoolManaType == ManaType.COLORLESS) {
                // colorless can be paid by any color from the pool
                canPay = possiblePoolItem.count() > 0;
            } else {
                // colored must be paid by specific color from the pool (AsThough already changed it to fit with mana pool)
                canPay = possiblePoolItem.get(possibleAsThoughPoolManaType) > 0;
            }
            if (canPay) {
                return true;
            }
        }

        return false;
    }

    private Abilities<ActivatedManaAbilityImpl> getManaAbilitiesSortedByManaCount(MageObject mageObject, final Game game) {
        Abilities<ActivatedManaAbilityImpl> manaAbilities = mageObject.getAbilities().getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, playerId, game);
        if (manaAbilities.size() > 1) {
            // Sort mana abilities by number of produced manas, to use ability first that produces most mana (maybe also conditional if possible)
            Collections.sort(manaAbilities, (a1, a2) -> {
                int a1Max = 0;
                for (Mana netMana : a1.getNetMana(game)) {
                    if (netMana.count() > a1Max) {
                        a1Max = netMana.count();
                    }
                }
                int a2Max = 0;
                for (Mana netMana : a2.getNetMana(game)) {
                    if (netMana.count() > a2Max) {
                        a2Max = netMana.count();
                    }
                }
                return CardUtil.overflowDec(a2Max, a1Max);
            });
        }
        return manaAbilities;
    }

    /**
     * returns a list of Permanents that produce mana sorted by the number of
     * mana the Permanent produces that match the unpaid costs in ascending
     * order
     * <p>
     * the idea is that we should pay costs first from mana producers that
     * produce only one type of mana and save the multi-mana producers for those
     * costs that can't be paid by any other producers
     *
     * @param unpaid - the amount of unpaid mana costs
     * @return List<Permanent>
     */
    private List<MageObject> getSortedProducers(ManaCosts<ManaCost> unpaid, Game game) {
        List<MageObject> unsorted = this.getAvailableManaProducers(game);
        unsorted.addAll(this.getAvailableManaProducersWithCost(game));
        Map<MageObject, Integer> scored = new HashMap<>();
        for (MageObject mageObject : unsorted) {
            int score = 0;
            for (ManaCost cost : unpaid) {
                Abilities:
                for (ActivatedManaAbilityImpl ability : mageObject.getAbilities().getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, playerId, game)) {
                    for (Mana netMana : ability.getNetMana(game)) {
                        if (cost.testPay(netMana)) {
                            score++;
                            break Abilities;
                        }
                    }
                }
            }
            if (score > 0) { // score mana producers that produce other mana types and have other uses higher
                score += mageObject.getAbilities().getAvailableActivatedManaAbilities(Zone.BATTLEFIELD, playerId, game).size();
                score += mageObject.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD).size();
                if (!mageObject.getCardType(game).contains(CardType.LAND)) {
                    score += 2;
                } else if (mageObject.getCardType(game).contains(CardType.CREATURE)) {
                    score += 2;
                }
            }
            scored.put(mageObject, score);
        }
        return sortByValue(scored);
    }

    private List<MageObject> sortByValue(Map<MageObject, Integer> map) {
        List<Entry<MageObject, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, Comparator.comparing(Entry::getValue));
        List<MageObject> result = new ArrayList<>();
        for (Entry<MageObject, Integer> entry : list) {
            result.add(entry.getKey());
        }
        return result;
    }

    @Override
    public int announceX(int min, int max, String message, Game game, Ability source, boolean isManaPay) {
        return makeChoiceAmount(min, max, game, source, isManaPay);
    }

    @Override
    public void abort() {
        abort = true;
    }

    @Override
    public void skip() {
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Ability source, Game game) {
        return chooseUse(outcome, message, null, null, null, source, game);
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, String secondMessage, String trueText, String falseText, Ability source, Game game) {
        // Be proactive! Always use abilities, the evaluation function will decide if it's good or not
        // Otherwise some abilities won't be used by AI like LoseTargetEffect that has "bad" outcome
        // but still is good when targets opponent
        return outcome != Outcome.AIDontUseIt; // Added for Desecration Demon sacrifice ability
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        //TODO: improve this

        // choose creature type
        // TODO: WTF?! Creature types dialog text can changes, need to replace that code
        if (choice.getMessage() != null && (choice.getMessage().equals("Choose creature type") || choice.getMessage().equals("Choose a creature type"))) {
            chooseCreatureType(outcome, choice, game);
        }

        // choose the correct color to pay a spell (use last unpaid ability for color hint)
        ManaCost unpaid = null;
        if (!lastUnpaidMana.isEmpty()) {
            unpaid = new ArrayList<>(lastUnpaidMana.values()).get(lastUnpaidMana.size() - 1);
        }
        if (outcome == Outcome.PutManaInPool && unpaid != null && choice.isManaColorChoice()) {
            if (unpaid.containsColor(ColoredManaSymbol.W) && choice.getChoices().contains("White")) {
                choice.setChoice("White");
                return true;
            }
            if (unpaid.containsColor(ColoredManaSymbol.R) && choice.getChoices().contains("Red")) {
                choice.setChoice("Red");
                return true;
            }
            if (unpaid.containsColor(ColoredManaSymbol.G) && choice.getChoices().contains("Green")) {
                choice.setChoice("Green");
                return true;
            }
            if (unpaid.containsColor(ColoredManaSymbol.U) && choice.getChoices().contains("Blue")) {
                choice.setChoice("Blue");
                return true;
            }
            if (unpaid.containsColor(ColoredManaSymbol.B) && choice.getChoices().contains("Black")) {
                choice.setChoice("Black");
                return true;
            }
            if (unpaid.getMana().getColorless() > 0 && choice.getChoices().contains("Colorless")) {
                choice.setChoice("Colorless");
                return true;
            }
        }

        // choose by random
        if (!choice.isChosen()) {
            choice.setRandomChoice();
        }

        return true;
    }

    protected boolean chooseCreatureType(Outcome outcome, Choice choice, Game game) {
        if (outcome == Outcome.Detriment) {
            // choose a creature type of opponent on battlefield or graveyard
            for (Permanent permanent : game.getBattlefield().getActivePermanents(this.getId(), game)) {
                if (game.getOpponents(getId(), true).contains(permanent.getControllerId())
                        && permanent.getCardType(game).contains(CardType.CREATURE)
                        && !permanent.getSubtype(game).isEmpty()) {
                    if (choice.getChoices().contains(permanent.getSubtype(game).get(0).toString())) {
                        choice.setChoice(permanent.getSubtype(game).get(0).toString());
                        break;
                    }
                }
            }
            // or in opponent graveyard
            if (!choice.isChosen()) {
                for (UUID opponentId : game.getOpponents(getId(), true)) {
                    Player opponent = game.getPlayer(opponentId);
                    for (Card card : opponent.getGraveyard().getCards(game)) {
                        if (card != null && card.getCardType(game).contains(CardType.CREATURE) && !card.getSubtype(game).isEmpty()) {
                            if (choice.getChoices().contains(card.getSubtype(game).get(0).toString())) {
                                choice.setChoice(card.getSubtype(game).get(0).toString());
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
            for (UUID cardId : this.getHand()) {
                Card card = game.getCard(cardId);
                if (card != null && card.getCardType(game).contains(CardType.CREATURE) && !card.getSubtype(game).isEmpty()) {
                    if (choice.getChoices().contains(card.getSubtype(game).get(0).toString())) {
                        choice.setChoice(card.getSubtype(game).get(0).toString());
                        break;
                    }
                }
            }
            if (!choice.isChosen()) {
                for (UUID cardId : this.getLibrary().getCardList()) {
                    Card card = game.getCard(cardId);
                    if (card != null && card.getCardType(game).contains(CardType.CREATURE) && !card.getSubtype(game).isEmpty()) {
                        if (choice.getChoices().contains(card.getSubtype(game).get(0).toString())) {
                            choice.setChoice(card.getSubtype(game).get(0).toString());
                            break;
                        }
                    }
                }
            }
        }
        return choice.isChosen();
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        return makeChoice(outcome, target, source, game, cards);
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        return makeChoice(outcome, target, source, game, cards);
    }

    @Override
    public boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game) {
        //TODO: improve this
        return true; // select left pile all the time
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        UUID opponentId = game.getCombat().getDefenders().iterator().next();
        Attackers attackers = getPotentialAttackers(game);
        List<Permanent> blockers = getOpponentBlockers(opponentId, game);
        List<Permanent> actualAttackers = new ArrayList<>();
        if (blockers.isEmpty()) {
            actualAttackers = attackers.getAttackers();
        } else if (attackers.size() - blockers.size() >= game.getPlayer(opponentId).getLife()) {
            actualAttackers = attackers.getAttackers();
        } else {
            CombatSimulator combat = simulateAttack(attackers, blockers, opponentId, game);
            if (combat.rating > 2) {
                for (CombatGroupSimulator group : combat.groups) {
                    this.declareAttacker(group.attackers.get(0).id, group.defenderId, game, false);
                }
            }
        }
        for (Permanent attacker : actualAttackers) {
            this.declareAttacker(attacker.getId(), opponentId, game, false);
        }
    }

    @Override
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {
        List<Permanent> blockers = getAvailableBlockers(game);

        CombatSimulator sim = simulateBlock(CombatSimulator.load(game), blockers, game);

        List<CombatGroup> groups = game.getCombat().getGroups();
        for (int i = 0; i < groups.size(); i++) {
            for (CreatureSimulator creature : sim.groups.get(i).blockers) {
                groups.get(i).addBlocker(creature.id, playerId, game);
            }
        }
    }

    @Override
    public int chooseReplacementEffect(Map<String, String> effectsMap, Map<String, MageObject> objectsMap, Game game) {
        //TODO: implement this
        return 0; // select first effect all the time
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        if (modes.getMode() != null && modes.getMaxModes(game, source) == modes.getSelectedModes().size()) {
            // mode was already set by the AI
            return modes.getMode();
        }

        // spell modes simulated by AI, see addModeOptions
        // trigger modes chooses here
        // TODO: add AI support to select best modes, current code uses first valid mode
        return modes.getAvailableModes(source, game).stream()
                .filter(mode -> !modes.getSelectedModes().contains(mode.getId()))
                .filter(mode -> mode.getTargets().canChoose(source.getControllerId(), source, game))
                .findFirst()
                .orElse(null);
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        //TODO: improve this
        if (!abilities.isEmpty()) {
            return abilities.get(0); // select first trigger all the time
        }
        return null;
    }

    @Override
    public int getAmount(int min, int max, String message, Ability source, Game game) {
        return makeChoiceAmount(min, max, game, source, false);
    }

    @Override
    public List<Integer> getMultiAmountWithIndividualConstraints(Outcome outcome, List<MultiAmountMessage> messages,
                                                                 int totalMin, int totalMax, MultiAmountType type, Game game) {
        int needCount = messages.size();
        List<Integer> defaultList = MultiAmountType.prepareDefaultValues(messages, totalMin, totalMax);
        if (needCount == 0) {
            return defaultList;
        }

        // BAD effect
        // default list uses minimum possible values, so return it on bad effect
        // TODO: need something for damage target and mana logic here, current version is useless but better than random
        if (!outcome.isGood()) {
            return defaultList;
        }

        // GOOD effect
        // values must be stable, so AI must be able to simulate it and choose correct actions
        // fill max values as much as possible
        return MultiAmountType.prepareMaxValues(messages, totalMin, totalMax);
    }

    @Override
    public List<MageObject> getAvailableManaProducers(Game game) {
        return super.getAvailableManaProducers(game);
    }

    @Override
    public void sideboard(Match match, Deck deck) {
        // TODO: improve this
        match.submitDeck(playerId, deck); // do not change a deck
    }

    private static void addBasicLands(Deck deck, String landName, int number) {
        Set<String> landSets = TournamentUtil.getLandSetCodeForDeckSets(deck.getExpansionSetCodes());

        CardCriteria criteria = new CardCriteria();
        if (!landSets.isEmpty()) {
            criteria.setCodes(landSets.toArray(new String[0]));
        }
        criteria.rarities(Rarity.LAND).name(landName);
        List<CardInfo> cards = CardRepository.instance.findCards(criteria);

        if (cards.isEmpty()) {
            criteria = new CardCriteria();
            criteria.rarities(Rarity.LAND).name(landName);
            criteria.setCodes("M15");
            cards = CardRepository.instance.findCards(criteria);
        }

        for (int i = 0; i < number; i++) {
            Card land = cards.get(RandomUtil.nextInt(cards.size())).createCard();
            deck.getCards().add(land);
        }
    }

    public static Deck buildDeck(int deckMinSize, List<Card> cardPool, final List<ColoredManaSymbol> colors) {
        return buildDeck(deckMinSize, cardPool, colors, false);
    }

    public static Deck buildDeck(int deckMinSize, List<Card> cardPool, final List<ColoredManaSymbol> colors, boolean onlyBasicLands) {
        if (onlyBasicLands) {
            return buildDeckWithOnlyBasicLands(deckMinSize, cardPool);
        } else {
            return buildDeckWithNormalCards(deckMinSize, cardPool, colors);
        }
    }

    public static Deck buildDeckWithOnlyBasicLands(int deckMinSize, List<Card> cardPool) {
        // random cards from card pool
        Deck deck = new Deck();
        final int DECK_SIZE = deckMinSize != 0 ? deckMinSize : 40;

        List<Card> sortedCards = new ArrayList<>(cardPool);
        if (!sortedCards.isEmpty()) {
            while (deck.getMaindeckCards().size() < DECK_SIZE) {
                deck.getCards().add(sortedCards.get(RandomUtil.nextInt(sortedCards.size())));
            }
            return deck;
        } else {
            addBasicLands(deck, "Forest", DECK_SIZE);
            return deck;
        }
    }

    public static Deck buildDeckWithNormalCards(int deckMinSize, List<Card> cardPool, final List<ColoredManaSymbol> colors) {
        // top 23 cards plus basic lands until 40 deck size
        Deck deck = new Deck();
        final int DECK_SIZE = deckMinSize != 0 ? deckMinSize : 40;
        final int DECK_CARDS_COUNT = Math.floorDiv(deckMinSize * 23, 40); // 23 from 40
        final int DECK_LANDS_COUNT = DECK_SIZE - DECK_CARDS_COUNT;

        // sort card pool by top score
        List<Card> sortedCards = new ArrayList<>(cardPool);
        Collections.sort(sortedCards, (o1, o2) -> {
            Integer score1 = RateCard.rateCard(o1, colors);
            Integer score2 = RateCard.rateCard(o2, colors);
            return score2.compareTo(score1);
        });

        // get top cards
        int cardNum = 0;
        while (deck.getMaindeckCards().size() < DECK_CARDS_COUNT && sortedCards.size() > cardNum) {
            Card card = sortedCards.get(cardNum);
            if (!card.isBasic()) {
                deck.getCards().add(card);
                deck.getSideboard().remove(card);
            }
            cardNum++;
        }

        // add basic lands by color percent
        // TODO:  compensate for non basic lands
        Mana mana = new Mana();
        for (Card card : deck.getCards()) {
            mana.add(card.getManaCost().getMana());
        }
        double total = mana.getBlack() + mana.getBlue() + mana.getGreen() + mana.getRed() + mana.getWhite();

        // most frequent land is forest by default
        int mostLand = 0;
        String mostLandName = "Forest";
        if (mana.getGreen() > 0) {
            int number = (int) Math.round(mana.getGreen() / total * DECK_LANDS_COUNT);
            addBasicLands(deck, "Forest", number);
            mostLand = number;
        }

        if (mana.getBlack() > 0) {
            int number = (int) Math.round(mana.getBlack() / total * DECK_LANDS_COUNT);
            addBasicLands(deck, "Swamp", number);
            if (number > mostLand) {
                mostLand = number;
                mostLandName = "Swamp";
            }
        }

        if (mana.getBlue() > 0) {
            int number = (int) Math.round(mana.getBlue() / total * DECK_LANDS_COUNT);
            addBasicLands(deck, "Island", number);
            if (number > mostLand) {
                mostLand = number;
                mostLandName = "Island";
            }
        }

        if (mana.getWhite() > 0) {
            int number = (int) Math.round(mana.getWhite() / total * DECK_LANDS_COUNT);
            addBasicLands(deck, "Plains", number);
            if (number > mostLand) {
                mostLand = number;
                mostLandName = "Plains";
            }
        }

        if (mana.getRed() > 0) {
            int number = (int) Math.round(mana.getRed() / total * DECK_LANDS_COUNT);
            addBasicLands(deck, "Mountain", number);
            if (number > mostLand) {
                mostLandName = "Plains";
            }
        }

        // adds remaining lands (most popular name)
        addBasicLands(deck, mostLandName, DECK_SIZE - deck.getMaindeckCards().size());

        return deck;
    }

    @Override
    public void construct(Tournament tournament, Deck deck) {
        DeckValidator deckValidator = DeckValidatorFactory.instance.createDeckValidator(tournament.getOptions().getMatchOptions().getDeckType());
        int deckMinSize = deckValidator != null ? deckValidator.getDeckMinSize() : 0;

        if (deck != null && deck.getMaindeckCards().size() < deckMinSize && !deck.getSideboard().isEmpty()) {
            if (chosenColors.isEmpty()) {
                for (Card card : deck.getSideboard()) {
                    rememberPick(card, RateCard.rateCard(card, Collections.emptyList()));
                }
                List<ColoredManaSymbol> deckColors = chooseDeckColorsIfPossible();
                if (deckColors != null) {
                    chosenColors.addAll(deckColors);
                }
            }
            deck = buildDeck(deckMinSize, new ArrayList<>(deck.getSideboard()), chosenColors);
        }
        tournament.submitDeck(playerId, deck);
    }

    @Deprecated // TODO: replace by source only version
    public Card selectBestCard(List<Card> cards, List<ColoredManaSymbol> chosenColors) {
        return selectBestCardInner(cards, chosenColors, null, null, null);
    }

    /**
     * @param targetingSource null on non-target choice like choose and source on targeting choice like chooseTarget
     */
    public Card selectBestCardInner(List<Card> cards, List<ColoredManaSymbol> chosenColors, Target target, Ability targetingSource, Game game) {
        if (cards.isEmpty()) {
            return null;
        }

        // sometimes a target selection can be made from a player that does not control the ability
        UUID abilityControllerId = this.getId();
        if (target != null) {
            abilityControllerId = target.getAffectedAbilityControllerId(this.getId());
        }

        Card bestCard = null;
        int maxScore = 0;
        for (Card card : cards) {
            int score = RateCard.rateCard(card, chosenColors);
            boolean betterCard = false;
            if (bestCard == null) { // we need any card to prevent NPE in callers
                betterCard = true;
            } else if (score > maxScore) { // we need better card
                if (target != null && targetingSource != null && game != null) {
                    // but also check it can be targeted
                    betterCard = target.canTarget(abilityControllerId, card.getId(), targetingSource, game);
                } else {
                    // target object wasn't provided, so accepting it anyway
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

    /**
     * @param targetingSource null on non-target choice like choose and source on targeting choice like chooseTarget
     */
    public Card selectWorstCardInner(List<Card> cards, List<ColoredManaSymbol> chosenColors, Target target, Ability targetingSource, Game game) {
        if (cards.isEmpty()) {
            return null;
        }

        // sometimes a target selection can be made from a player that does not control the ability
        UUID abilityControllerId = this.getId();
        if (target != null) {
            abilityControllerId = target.getAffectedAbilityControllerId(this.getId());
        }

        Card worstCard = null;
        int minScore = Integer.MAX_VALUE;
        for (Card card : cards) {
            int score = RateCard.rateCard(card, chosenColors);
            boolean worseCard = false;
            if (worstCard == null) { // we need any card to prevent NPE in callers
                worseCard = true;
            } else if (score < minScore) { // we need worse card
                if (target != null && targetingSource != null && game != null) {
                    // but also check it can be targeted
                    worseCard = target.canTarget(abilityControllerId, card.getId(), targetingSource, game);
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

    @Override
    public void pickCard(List<Card> cards, Deck deck, Draft draft) {
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("No cards to pick from.");
        }
        try {
            Card bestCard = selectBestCard(cards, chosenColors);
            int maxScore = RateCard.rateCard(bestCard, chosenColors);
            int pickedCardRate = RateCard.getBaseCardScore(bestCard);

            if (pickedCardRate <= 30) {
                // if card is bad
                // try to counter pick without any color restriction
                Card counterPick = selectBestCard(cards, Collections.emptyList());
                int counterPickScore = RateCard.getBaseCardScore(counterPick);
                // card is perfect
                // take it!
                if (counterPickScore >= 80) {
                    bestCard = counterPick;
                    maxScore = RateCard.rateCard(bestCard, chosenColors);
                }
            }

            String colors = "not chosen yet";
            // remember card if colors are not chosen yet
            if (chosenColors.isEmpty()) {
                rememberPick(bestCard, maxScore);
                List<ColoredManaSymbol> chosen = chooseDeckColorsIfPossible();
                if (chosen != null) {
                    chosenColors.addAll(chosen);
                }
            }
            if (!chosenColors.isEmpty()) {
                colors = "";
                for (ColoredManaSymbol symbol : chosenColors) {
                    colors += symbol.toString();
                }
            }
            log.debug("[DEBUG] AI picked: " + bestCard.getName() + ", score=" + maxScore + ", deck colors=" + colors);
            draft.addPick(playerId, bestCard.getId(), null);
        } catch (Exception e) {
            log.debug("Exception during AI pick card for draft playerId= " + getId());
            draft.addPick(playerId, cards.get(0).getId(), null);
        }
    }

    /**
     * Remember picked card with its score.
     */
    protected void rememberPick(Card card, int score) {
        pickedCards.add(new PickedCard(card, score));
    }

    /**
     * Choose 2 deck colors for draft: 1. there should be at least 3 cards in
     * card pool 2. at least 2 cards should have different colors 3. get card
     * colors as chosen starting from most rated card
     */
    protected List<ColoredManaSymbol> chooseDeckColorsIfPossible() {
        if (pickedCards.size() > 2) {
            // sort by score and color mana symbol count in descending order
            pickedCards.sort((o1, o2) -> {
                if (o1.score.equals(o2.score)) {
                    Integer i1 = RateCard.getColorManaCount(o1.card);
                    Integer i2 = RateCard.getColorManaCount(o2.card);
                    return i2.compareTo(i1);
                }
                return o2.score.compareTo(o1.score);
            });
            Set<String> chosenSymbols = new HashSet<>();
            for (PickedCard picked : pickedCards) {
                int differentColorsInCost = RateCard.getDifferentColorManaCount(picked.card);
                // choose only color card, but only if they are not too gold
                if (differentColorsInCost > 0 && differentColorsInCost < 3) {
                    // if some colors were already chosen, total amount shouldn't be more than 3
                    if (chosenSymbols.size() + differentColorsInCost < 4) {
                        for (String symbol : picked.card.getManaCostSymbols()) {
                            symbol = symbol.replace("{", "").replace("}", "");
                            if (RateCard.isColoredMana(symbol)) {
                                chosenSymbols.add(symbol);
                            }
                        }
                    }
                }
                // only two or three color decks are allowed
                if (chosenSymbols.size() > 1 && chosenSymbols.size() < 4) {
                    List<ColoredManaSymbol> colorsChosen = new ArrayList<>();
                    for (String symbol : chosenSymbols) {
                        ColoredManaSymbol manaSymbol = ColoredManaSymbol.lookup(symbol.charAt(0));
                        if (manaSymbol != null) {
                            colorsChosen.add(manaSymbol);
                        }
                    }
                    if (colorsChosen.size() > 1) {
                        // no need to remember picks anymore
                        pickedCards.clear();
                        return colorsChosen;
                    }
                }
            }
        }
        return null;
    }

    private static class PickedCard {

        public Card card;
        public Integer score;

        public PickedCard(Card card, int score) {
            this.card = card;
            this.score = score;
        }
    }

    protected Attackers getPotentialAttackers(Game game) {
        Attackers attackers = new Attackers();
        List<Permanent> creatures = super.getAvailableAttackers(game);
        for (Permanent creature : creatures) {
            int potential = combatPotential(creature, game);
            if (potential > 0 && creature.getPower().getValue() > 0) {
                List<Permanent> l = attackers.get(potential);
                if (l == null) {
                    attackers.put(potential, l = new ArrayList<>());
                }
                l.add(creature);
            }
        }
        return attackers;
    }

    protected int combatPotential(Permanent creature, Game game) {
        if (!creature.canAttack(null, game)) {
            return 0;
        }
        int potential = creature.getPower().getValue();
        potential += creature.getAbilities().getEvasionAbilities().size();
        potential += creature.getAbilities().getProtectionAbilities().size();
        potential += creature.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId()) ? 1 : 0;
        potential += creature.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId()) ? 2 : 0;
        potential += creature.getAbilities().containsKey(TrampleAbility.getInstance().getId()) ? 1 : 0;
        return potential;
    }

    protected List<Permanent> getOpponentBlockers(UUID opponentId, Game game) {
        FilterCreatureForCombatBlock blockFilter = new FilterCreatureForCombatBlock();
        return game.getBattlefield().getAllActivePermanents(blockFilter, opponentId, game);
    }

    protected CombatSimulator simulateAttack(Attackers attackers, List<Permanent> blockers, UUID opponentId, Game game) {
        List<Permanent> attackersList = attackers.getAttackers();
        CombatSimulator best = new CombatSimulator();
        int bestResult = 0;
        //use binary digits to calculate powerset of attackers
        int powerElements = (int) Math.pow(2, attackersList.size());
        for (int i = 1; i < powerElements; i++) {
            String binary = Integer.toBinaryString(i);
            while (binary.length() < attackersList.size()) {
                binary = '0' + binary;
            }
            List<Permanent> trialAttackers = new ArrayList<>();
            for (int j = 0; j < attackersList.size(); j++) {
                if (binary.charAt(j) == '1') {
                    trialAttackers.add(attackersList.get(j));
                }
            }
            CombatSimulator combat = new CombatSimulator();
            for (Permanent permanent : trialAttackers) {
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
        TreeNode<CombatSimulator> simulations;

        simulations = new TreeNode<>(combat);
        addBlockSimulations(blockers, simulations, game);
        combat.simulate(game);

        return getWorstSimulation(simulations);

    }

    protected void addBlockSimulations(List<Permanent> blockers, TreeNode<CombatSimulator> node, Game game) {
        int numGroups = node.getData().groups.size();
        Copier<CombatSimulator> copier = new Copier<>();
        for (Permanent blocker : blockers) {
            List<Permanent> subList = remove(blockers, blocker);
            for (int i = 0; i < numGroups; i++) {
                if (node.getData().groups.get(i).canBlock(blocker, game)) {
                    CombatSimulator combat = copier.copy(node.getData());
                    combat.groups.get(i).blockers.add(new CreatureSimulator(blocker));
                    TreeNode<CombatSimulator> child = new TreeNode<>(combat);
                    node.addChild(child);
                    addBlockSimulations(subList, child, game);
                    combat.simulate(game);
                }
            }
        }
    }

    protected List<Permanent> remove(List<Permanent> source, Permanent element) {
        List<Permanent> newList = new ArrayList<>();
        for (Permanent permanent : source) {
            if (!permanent.equals(element)) {
                newList.add(permanent);
            }
        }
        return newList;
    }

    protected CombatSimulator getBestSimulation(TreeNode<CombatSimulator> simulations) {
        CombatSimulator best = simulations.getData();
        int bestResult = best.evaluate();
        for (TreeNode<CombatSimulator> node : simulations.getChildren()) {
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
        for (TreeNode<CombatSimulator> node : simulations.getChildren()) {
            CombatSimulator worstSub = getWorstSimulation(node);
            if (worstSub.evaluate() < worstResult) {
                worst = node.getData();
                worstResult = worst.evaluate();
            }
        }
        return worst;
    }

    protected List<Permanent> threats(UUID playerId, Ability source, FilterPermanent filter, Game game, List<UUID> targets) {
        return threats(playerId, source, filter, game, targets, true);
    }

    protected List<Permanent> threats(UUID playerId, Ability source, FilterPermanent filter, Game game, List<UUID> targets, boolean mostValuableGoFirst) {
        // most valuable/powerfully permanents goes at first
        List<Permanent> threats;
        if (playerId == null) {
            threats = game.getBattlefield().getActivePermanents(filter, this.getId(), source, game); // all permanents within the range of the player
        } else {
            FilterPermanent filterCopy = filter.copy();
            filterCopy.add(new ControllerIdPredicate(playerId));
            threats = game.getBattlefield().getActivePermanents(filter, this.getId(), source, game);
        }
        Iterator<Permanent> it = threats.iterator();
        while (it.hasNext()) { // remove permanents already targeted
            Permanent test = it.next();
            if (targets.contains(test.getId()) || (playerId != null && !test.getControllerId().equals(playerId))) {
                it.remove();
            }
        }
        Collections.sort(threats, new PermanentComparator(game));
        if (mostValuableGoFirst) {
            Collections.reverse(threats);
        }
        return threats;
    }

    protected void logList(String message, List<MageObject> list) {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append(": ");
        for (MageObject object : list) {
            sb.append(object.getName()).append(',');
        }
        log.info(sb.toString());
    }

    private void playRemoval(Set<UUID> creatures, Game game) {
        for (UUID creatureId : creatures) {
            for (Card card : this.playableInstant) {
                if (card.getSpellAbility().canActivate(playerId, game).canActivate()) {
                    for (Effect effect : card.getSpellAbility().getEffects()) {
                        if (effect.getOutcome() == Outcome.DestroyPermanent || effect.getOutcome() == Outcome.ReturnToHand) {
                            if (card.getSpellAbility().getTargets().get(0).canTarget(creatureId, card.getSpellAbility(), game)) {
                                if (this.activateAbility(card.getSpellAbility(), game)) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void playDamage(Set<UUID> creatures, Game game) {
        for (UUID creatureId : creatures) {
            Permanent creature = game.getPermanent(creatureId);
            for (Card card : this.playableInstant) {
                if (card.getSpellAbility().canActivate(playerId, game).canActivate()) {
                    for (Effect effect : card.getSpellAbility().getEffects()) {
                        if (effect instanceof DamageTargetEffect) {
                            if (card.getSpellAbility().getTargets().get(0).canTarget(creatureId, card.getSpellAbility(), game)) {
                                if (((DamageTargetEffect) effect).getAmount() > (creature.getPower().getValue() - creature.getDamage())) {
                                    if (this.activateAbility(card.getSpellAbility(), game)) {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void cleanUpOnMatchEnd() {
        super.cleanUpOnMatchEnd();
    }

    @Override
    public ComputerPlayer copy() {
        return new ComputerPlayer(this);
    }

    private boolean tryAddTarget(Target target, UUID id, int amount, Ability source, Game game) {
        // workaround to check successfully targets add
        int before = target.getTargets().size();
        target.addTarget(id, amount, source, game);
        int after = target.getTargets().size();
        return before != after;
    }

    /**
     * Returns an opponent by random
     */
    @Deprecated // TODO: rework all usages and replace to all possible opponents instead single
    private UUID getRandomOpponent(Game game) {
        return RandomUtil.randomFromCollection(game.getOpponents(getId(), true));
    }

    @Override
    public SpellAbility chooseAbilityForCast(Card card, Game game, boolean noMana) {
        Map<UUID, SpellAbility> usable = PlayerImpl.getCastableSpellAbilities(game, this.getId(), card, game.getState().getZone(card.getId()), noMana);
        return usable.values().stream()
                .filter(a -> a.getTargets().canChoose(getId(), a, game))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player obj = (Player) o;
        if (this.getId() == null || obj.getId() == null) {
            return false;
        }

        return this.getId().equals(obj.getId());
    }

    @Override
    public boolean isHuman() {
        if (human) {
            throw new IllegalStateException("Computer player can't be Human");
        } else {
            return false;
        }
    }

    @Override
    public void restore(Player player) {
        super.restore(player);

        // restore used in AI simulations
        // all human players converted to computer and analyse
        this.human = false;
    }

    public long getLastThinkTime() {
        return lastThinkTime;
    }

    public void setLastThinkTime(long lastThinkTime) {
        this.lastThinkTime = lastThinkTime;
    }
}