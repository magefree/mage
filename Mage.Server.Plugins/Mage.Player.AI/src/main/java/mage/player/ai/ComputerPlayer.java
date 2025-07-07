package mage.player.ai;

import mage.*;
import mage.abilities.*;
import mage.abilities.costs.mana.*;
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
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.draft.Draft;
import mage.game.match.Match;
import mage.game.permanent.Permanent;
import mage.game.tournament.Tournament;
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
 * AI: basic server side bot with simple actions support (game, draft, construction/sideboarding).
 * Full and minimum implementation of all choose dialogs to allow AI to start and finish a real game.
 * Used as parent class for any AI implementations.
 * <p>
 *
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class ComputerPlayer extends PlayerImpl {

    private static final Logger logger = Logger.getLogger(ComputerPlayer.class);

    protected static final int PASSIVITY_PENALTY = 5; // Penalty value for doing nothing if some actions are available

    // debug only: set TRUE to debug simulation's code/games (on false sim thread will be stopped after few secs by timeout)
    public static final boolean COMPUTER_DISABLE_TIMEOUT_IN_GAME_SIMULATIONS = false; // DebugUtil.AI_ENABLE_DEBUG_MODE;

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
    final static int COMPUTER_MAX_THREADS_FOR_SIMULATIONS = 5;//DebugUtil.AI_ENABLE_DEBUG_MODE ? 1 : 5;


    // remember picked cards for better draft choices
    private final transient List<PickedCard> pickedCards = new ArrayList<>();
    private final transient List<ColoredManaSymbol> chosenColors = new ArrayList<>();

    // keep current paying cost info for choose dialogs
    // mana abilities must ask payment too, so keep full chain
    // TODO: make sure it thread safe for AI simulations (all transient fields above and bottom)
    private final transient Map<UUID, ManaCost> lastUnpaidMana = new LinkedHashMap<>();

    // For stopping infinite loops when trying to pay Phyrexian mana when the player can't spend life and no other sources are available
    private transient boolean alreadyTryingToPayPhyrexian;

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
                || isTestMode() // ignore mulligan in tests
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
        // minimum implementation for do nothing
        pass(game);
        return false;
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
            if (chooseCreatureType(outcome, choice, game)) {
                return true;
            }
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
        // do nothing, parent class must implement it
    }

    @Override
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {
        // do nothing, parent class must implement it
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

    public Card makePickCard(List<Card> cards, List<ColoredManaSymbol> chosenColors) {
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
                betterCard = true;
            }
            // is it better than previous one?
            if (betterCard) {
                maxScore = score;
                bestCard = card;
            }
        }
        return bestCard;
    }

    @Override
    public void pickCard(List<Card> cards, Deck deck, Draft draft) {
        // method used by DRAFT bot too
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("No cards to pick from.");
        }
        try {
            Card bestCard = makePickCard(cards, chosenColors);
            int maxScore = RateCard.rateCard(bestCard, chosenColors);
            int pickedCardRate = RateCard.getBaseCardScore(bestCard);

            if (pickedCardRate <= 30) {
                // if card is bad
                // try to counter pick without any color restriction
                Card counterPick = makePickCard(cards, Collections.emptyList());
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
            draft.addPick(playerId, bestCard.getId(), null);
        } catch (Exception e) {
            logger.error("Error during AI pick card for draft playerId = " + getId(), e);
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

    protected List<Permanent> remove(List<Permanent> source, Permanent element) {
        List<Permanent> newList = new ArrayList<>();
        for (Permanent permanent : source) {
            if (!permanent.equals(element)) {
                newList.add(permanent);
            }
        }
        return newList;
    }

    protected void logList(String message, List<MageObject> list) {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append(": ");
        for (MageObject object : list) {
            sb.append(object.getName()).append(',');
        }
        logger.info(sb.toString());
    }

    @Override
    public void cleanUpOnMatchEnd() {
        super.cleanUpOnMatchEnd();
    }

    @Override
    public ComputerPlayer copy() {
        return new ComputerPlayer(this);
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
}