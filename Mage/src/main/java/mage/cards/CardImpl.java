package mage.cards;

import com.google.common.collect.ImmutableList;
import mage.MageObject;
import mage.MageObjectImpl;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.*;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.repository.PluginClassloaderRegistery;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.filter.FilterMana;
import mage.game.*;
import mage.game.command.CommandObject;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.util.GameLog;
import mage.util.SubTypeList;
import mage.watchers.Watcher;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class CardImpl extends MageObjectImpl implements Card {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(CardImpl.class);

    private static final String regexBlack = ".*\\x7b.{0,2}B.{0,2}\\x7d.*";
    private static final String regexBlue = ".*\\x7b.{0,2}U.{0,2}\\x7d.*";
    private static final String regexRed = ".*\\x7b.{0,2}R.{0,2}\\x7d.*";
    private static final String regexGreen = ".*\\x7b.{0,2}G.{0,2}\\x7d.*";
    private static final String regexWhite = ".*\\x7b.{0,2}W.{0,2}\\x7d.*";

    protected UUID ownerId;
    protected String cardNumber;
    protected String expansionSetCode;
    protected String tokenSetCode;
    protected String tokenDescriptor;
    protected Rarity rarity;
    protected boolean transformable;
    protected Class<?> secondSideCardClazz;
    protected Card secondSideCard;
    protected boolean nightCard;
    protected SpellAbility spellAbility;
    protected boolean flipCard;
    protected String flipCardName;
    protected boolean usesVariousArt = false;
    protected boolean splitCard;
    protected boolean morphCard;

    protected List<UUID> attachments = new ArrayList<>();

    public CardImpl(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, String costs) {
        this(ownerId, setInfo, cardTypes, costs, SpellAbilityType.BASE);
    }

    public CardImpl(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, String costs, SpellAbilityType spellAbilityType) {
        this(ownerId, setInfo.getName());
        this.rarity = setInfo.getRarity();
        this.cardNumber = setInfo.getCardNumber();
        this.expansionSetCode = setInfo.getExpansionSetCode();
        this.cardType.addAll(Arrays.asList(cardTypes));
        this.manaCost.load(costs);
        setDefaultColor();
        if (this.isLand()) {
            Ability ability = new PlayLandAbility(name);
            ability.setSourceId(this.getId());
            abilities.add(ability);
        } else {
            SpellAbility ability = new SpellAbility(manaCost, name, Zone.HAND, spellAbilityType);
            if (!this.isInstant()) {
                ability.setTiming(TimingRule.SORCERY);
            }
            ability.setSourceId(this.getId());
            abilities.add(ability);
        }

        CardGraphicInfo graphicInfo = setInfo.getGraphicInfo();
        if (graphicInfo != null) {
            this.usesVariousArt = graphicInfo.getUsesVariousArt();
            if (graphicInfo.getFrameColor() != null) {
                this.frameColor = graphicInfo.getFrameColor().copy();
            }
            if (graphicInfo.getFrameStyle() != null) {
                this.frameStyle = graphicInfo.getFrameStyle();
            }
        }

        this.morphCard = false;
    }

    private void setDefaultColor() {
        this.color.setWhite(this.manaCost.containsColor(ColoredManaSymbol.W));
        this.color.setBlue(this.manaCost.containsColor(ColoredManaSymbol.U));
        this.color.setBlack(this.manaCost.containsColor(ColoredManaSymbol.B));
        this.color.setRed(this.manaCost.containsColor(ColoredManaSymbol.R));
        this.color.setGreen(this.manaCost.containsColor(ColoredManaSymbol.G));
    }

    protected CardImpl(UUID ownerId, String name) {
        this.ownerId = ownerId;
        this.name = name;
    }

    protected CardImpl(UUID id, UUID ownerId, String name) {
        super(id);
        this.ownerId = ownerId;
        this.name = name;
    }

    public CardImpl(final CardImpl card) {
        super(card);
        ownerId = card.ownerId;
        cardNumber = card.cardNumber;
        expansionSetCode = card.expansionSetCode;
        tokenDescriptor = card.tokenDescriptor;
        rarity = card.rarity;

        transformable = card.transformable;
        if (transformable) {
            secondSideCardClazz = card.secondSideCardClazz;
            nightCard = card.nightCard;
        }
        if (card.spellAbility != null) {
            spellAbility = card.getSpellAbility().copy();
        } else {
            spellAbility = null;
        }

        flipCard = card.flipCard;
        flipCardName = card.flipCardName;
        splitCard = card.splitCard;
        usesVariousArt = card.usesVariousArt;
        this.attachments.addAll(card.attachments);
    }

    @Override
    public void assignNewId() {
        this.objectId = UUID.randomUUID();
        this.abilities.newOriginalId();
        this.abilities.setSourceId(objectId);
        if (this.spellAbility != null) {
            this.spellAbility.setSourceId(objectId);
        }
    }

    public static Card createCard(String name, CardSetInfo setInfo) {
        try {
            return createCard(Class.forName(name), setInfo);
        } catch (ClassNotFoundException ex) {
            try {
                return createCard(PluginClassloaderRegistery.forName(name), setInfo);
            } catch (ClassNotFoundException ex2) {
                // ignored
            }
            logger.fatal("Error loading card: " + name, ex);
            return null;
        }
    }

    public static Card createCard(Class<?> clazz, CardSetInfo setInfo) {
        return createCard(clazz, setInfo, null);
    }

    public static Card createCard(Class<?> clazz, CardSetInfo setInfo, List<String> errorList) {
        String setCode = null;
        try {
            Card card;
            if (setInfo == null) {
                Constructor<?> con = clazz.getConstructor(UUID.class);
                card = (Card) con.newInstance(new Object[]{null});
            } else {
                setCode = setInfo.getExpansionSetCode();
                Constructor<?> con = clazz.getConstructor(UUID.class, CardSetInfo.class);
                card = (Card) con.newInstance(null, setInfo);
            }
            return card;
        } catch (Exception e) {
            String err = "Error loading card: " + clazz.getCanonicalName() + " (" + setCode + ")";
            if (errorList != null) {
                errorList.add(err);
            }

            if (e instanceof InvocationTargetException) {
                logger.fatal(err, ((InvocationTargetException) e).getTargetException());
            } else {
                logger.fatal(err, e);
            }

            return null;
        }
    }

    @Override
    public UUID getOwnerId() {
        return ownerId;
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public void addInfo(String key, String value, Game game) {
        game.getState().getCardState(objectId).addInfo(key, value);
    }

    protected static final List<String> rulesError = ImmutableList.of("Exception occurred in rules generation");

    @Override
    public List<String> getRules() {
        try {
            return abilities.getRules(this.getName());
        } catch (Exception e) {
            logger.info("Exception in rules generation for card: " + this.getName(), e);
        }
        return rulesError;
    }

    @Override
    public List<String> getRules(Game game) {
        try {
            List<String> rules = getRules();
            if (game != null) {
                // debug state
                CardState cardState = game.getState().getCardState(objectId);
                if (cardState != null) {
                    for (String data : cardState.getInfo().values()) {
                        rules.add(data);
                    }
                    for (Ability ability : cardState.getAbilities()) {
                        rules.add(ability.getRule());
                    }
                }

                // ability hints
                List<String> abilityHints = new ArrayList<>();
                if (HintUtils.ABILITY_HINTS_ENABLE) {
                    for (Ability ability : abilities) {
                        for (Hint hint : ability.getHints()) {
                            String s = hint.getText(game, ability);
                            if (s != null && !s.isEmpty()) {
                                abilityHints.add(s);
                            }
                        }
                    }
                }

                // restrict hints only for permanents, not cards

                // total hints
                if (!abilityHints.isEmpty()) {
                    rules.add(HintUtils.HINT_START_MARK);
                    HintUtils.appendHints(rules, abilityHints);
                }
            }
            return rules;
        } catch (Exception e) {
            logger.error("Exception in rules generation for card: " + this.getName(), e);
        }
        return rulesError;
    }

    /**
     * Gets all base abilities - does not include additional abilities added by
     * other cards or effects
     *
     * @return A list of {@link Ability} - this collection is modifiable
     */
    @Override
    public Abilities<Ability> getAbilities() {
        return super.getAbilities();
    }

    /**
     * Gets all current abilities - includes additional abilities added by other
     * cards or effects
     *
     * @param game
     * @return A list of {@link Ability} - this collection is not modifiable
     */
    @Override
    public Abilities<Ability> getAbilities(Game game) {
        Abilities<Ability> otherAbilities = game.getState().getAllOtherAbilities(objectId);
        if (otherAbilities == null || otherAbilities.isEmpty()) {
            return abilities;
        }
        Abilities<Ability> all = new AbilitiesImpl<>();
        all.addAll(abilities);
        all.addAll(otherAbilities);
        return all;
    }

    /**
     * Public in order to support adding abilities to SplitCardHalf's
     *
     * @param ability
     */
    public void addAbility(Ability ability) {
        ability.setSourceId(this.getId());
        abilities.add(ability);
        for (Ability subAbility : ability.getSubAbilities()) {
            abilities.add(subAbility);
        }
    }

    protected void addAbilities(List<Ability> abilities) {
        for (Ability ability : abilities) {
            addAbility(ability);
        }
    }

    protected void addAbility(Ability ability, Watcher watcher) {
        addAbility(ability);
        ability.addWatcher(watcher);
    }

    public void replaceSpellAbility(SpellAbility newAbility) {
        SpellAbility oldAbility = this.getSpellAbility();
        while (oldAbility != null) {
            abilities.remove(oldAbility);
            spellAbility = null;
            oldAbility = this.getSpellAbility();
        }

        if (newAbility != null) {
            addAbility(newAbility);
        }
    }

    @Override
    public SpellAbility getSpellAbility() {
        if (spellAbility == null) {
            for (Ability ability : abilities.getActivatedAbilities(Zone.HAND)) {
                if (ability instanceof SpellAbility
                        && ((SpellAbility) ability).getSpellAbilityType() != SpellAbilityType.BASE_ALTERNATE) {
                    return spellAbility = (SpellAbility) ability;
                }
            }
        }
        return spellAbility;
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        ability.adjustCosts(game);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.adjustTargets(game);
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
        abilities.setControllerId(ownerId);
    }

    @Override
    public String getExpansionSetCode() {
        return expansionSetCode;
    }

    @Override
    public String getTokenSetCode() {
        return tokenSetCode;
    }

    @Override
    public String getTokenDescriptor() {
        return tokenDescriptor;
    }

    @Override
    public List<Mana> getMana() {
        List<Mana> mana = new ArrayList<>();
        for (ActivatedManaAbilityImpl ability : this.abilities.getActivatedManaAbilities(Zone.BATTLEFIELD)) {
            mana.addAll(ability.getNetMana(null));
        }
        return mana;
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag) {
        return this.moveToZone(toZone, sourceId, game, flag, null);
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, List<UUID> appliedEffects) {
        Zone fromZone = game.getState().getZone(objectId);
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, ownerId, fromZone, toZone, appliedEffects);
        ZoneChangeInfo zoneChangeInfo;
        if (null != toZone) {
            switch (toZone) {
                case LIBRARY:
                    zoneChangeInfo = new ZoneChangeInfo.Library(event, flag /* put on top */);
                    break;
                case BATTLEFIELD:
                    zoneChangeInfo = new ZoneChangeInfo.Battlefield(event, flag /* comes into play tapped */);
                    break;
                default:
                    zoneChangeInfo = new ZoneChangeInfo(event);
                    break;
            }
            return ZonesHandler.moveCard(zoneChangeInfo, game);
        }
        return false;
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        Card mainCard = getMainCard();
        ZoneChangeEvent event = new ZoneChangeEvent(mainCard.getId(), ability.getId(), controllerId, fromZone, Zone.STACK);
        ZoneChangeInfo.Stack info
                = new ZoneChangeInfo.Stack(event, new Spell(this, ability.getSpellAbilityToResolve(game), controllerId, event.getFromZone()));
        return ZonesHandler.cast(info, game);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game) {
        return moveToExile(exileId, name, sourceId, game, null);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, List<UUID> appliedEffects) {
        Zone fromZone = game.getState().getZone(objectId);
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, ownerId, fromZone, Zone.EXILED, appliedEffects);
        ZoneChangeInfo.Exile info = new ZoneChangeInfo.Exile(event, exileId, name);
        return ZonesHandler.moveCard(info, game);
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId) {
        return this.putOntoBattlefield(game, fromZone, sourceId, controllerId, false, false, null);
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped) {
        return this.putOntoBattlefield(game, fromZone, sourceId, controllerId, tapped, false, null);
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, boolean faceDown) {
        return this.putOntoBattlefield(game, fromZone, sourceId, controllerId, tapped, faceDown, null);
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, boolean faceDown, List<UUID> appliedEffects) {
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, controllerId, fromZone, Zone.BATTLEFIELD, appliedEffects);
        ZoneChangeInfo.Battlefield info = new ZoneChangeInfo.Battlefield(event, faceDown, tapped);
        return ZonesHandler.moveCard(info, game);
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, UUID sourceId) {
        boolean removed = false;
        MageObject lkiObject = null;
        switch (fromZone) {
            case GRAVEYARD:
                removed = game.getPlayer(ownerId).removeFromGraveyard(this, game);
                break;
            case HAND:
                removed = game.getPlayer(ownerId).removeFromHand(this, game);
                break;
            case LIBRARY:
                removed = game.getPlayer(ownerId).removeFromLibrary(this, game);
                break;
            case EXILED:
                if (game.getExile().getCard(getId(), game) != null) {
                    removed = game.getExile().removeCard(this, game);

                }
                break;
            case STACK:
                StackObject stackObject;
                if (getSpellAbility() != null) {
                    stackObject = game.getStack().getSpell(getSpellAbility().getId());
                } else {
                    stackObject = game.getStack().getSpell(this.getId());
                }
                if (stackObject == null && (this instanceof SplitCard)) { // handle if half of Split cast is on the stack
                    stackObject = game.getStack().getSpell(((SplitCard) this).getLeftHalfCard().getId());
                    if (stackObject == null) {
                        stackObject = game.getStack().getSpell(((SplitCard) this).getRightHalfCard().getId());
                    }
                }
                if (stackObject == null) {
                    stackObject = game.getStack().getSpell(getId());
                }
                if (stackObject != null) {
                    removed = game.getStack().remove(stackObject, game);
                    lkiObject = stackObject;
                }
                break;
            case COMMAND:
                for (CommandObject commandObject : game.getState().getCommand()) {
                    if (commandObject.getId().equals(objectId)) {
                        lkiObject = commandObject;
                    }
                }
                if (lkiObject != null) {
                    removed = game.getState().getCommand().remove(lkiObject);
                }
                break;
            case OUTSIDE:
                if (isCopy()) { // copied cards have no need to be removed from a previous zone
                    removed = true;
                } else if (game.getPlayer(ownerId).getSideboard().contains(this.getId())) {
                    game.getPlayer(ownerId).getSideboard().remove(this.getId());
                    removed = true;
                } else if (game.getPhase() == null) {
                    // E.g. Commander of commander game
                    removed = true;
                } else {
                    // Unstable - Summon the Pack
                    removed = true;
                }
                break;
            case BATTLEFIELD: // for sacrificing permanents or putting to library
                removed = true;
                break;
            default:
                MageObject sourceObject = game.getObject(sourceId);
                logger.fatal("Invalid from zone [" + fromZone + "] for card [" + this.getIdName()
                        + "] source [" + (sourceObject != null ? sourceObject.getName() : "null") + ']');
                break;
        }
        if (removed) {
            if (fromZone != Zone.OUTSIDE) {
                game.rememberLKI(lkiObject != null ? lkiObject.getId() : objectId, fromZone, lkiObject != null ? lkiObject : this);
            }
        } else {
            logger.warn("Couldn't find card in fromZone, card=" + getIdName() + ", fromZone=" + fromZone);
        }
        return removed;
    }

    @Override
    public void checkForCountersToAdd(Permanent permanent, Game game) {
        Counters countersToAdd = game.getEnterWithCounters(permanent.getId());
        if (countersToAdd != null) {
            for (Counter counter : countersToAdd.values()) {
                permanent.addCounters(counter, null, game);
            }
            game.setEnterWithCounters(permanent.getId(), null);
        }
    }

    @Override
    public void setFaceDown(boolean value, Game game) {
        game.getState().getCardState(objectId).setFaceDown(value);
    }

    @Override
    public boolean isFaceDown(Game game) {
        return game.getState().getCardState(objectId).isFaceDown();
    }

    @Override
    public boolean turnFaceUp(Game game, UUID playerId) {
        GameEvent event = GameEvent.getEvent(GameEvent.EventType.TURNFACEUP, getId(), playerId);
        if (!game.replaceEvent(event)) {
            setFaceDown(false, game);
            for (Ability ability : abilities) { // abilities that were set to not visible face down must be set to visible again
                if (ability.getWorksFaceDown() && !ability.getRuleVisible()) {
                    ability.setRuleVisible(true);
                }
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.TURNEDFACEUP, getId(), playerId));
            return true;
        }
        return false;
    }

    @Override
    public boolean turnFaceDown(Game game, UUID playerId) {
        GameEvent event = GameEvent.getEvent(GameEvent.EventType.TURNFACEDOWN, getId(), playerId);
        if (!game.replaceEvent(event)) {
            setFaceDown(true, game);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.TURNEDFACEDOWN, getId(), playerId));
            return true;
        }
        return false;
    }

    @Override
    public boolean isTransformable() {
        return this.transformable;
    }

    @Override
    public void setTransformable(boolean transformable) {
        this.transformable = transformable;
    }

    @Override
    public final Card getSecondCardFace() {
        if (secondSideCardClazz == null && secondSideCard == null) {
            return null;
        }

        if (secondSideCard != null) {
            return secondSideCard;
        }

        List<ExpansionSet.SetCardInfo> cardInfo = Sets.findSet(expansionSetCode).findCardInfoByClass(secondSideCardClazz);
        if (cardInfo.isEmpty()) {
            return null;
        }

        ExpansionSet.SetCardInfo info = cardInfo.get(0);
        return secondSideCard = createCard(secondSideCardClazz,
                new CardSetInfo(info.getName(), expansionSetCode, info.getCardNumber(), info.getRarity(), info.getGraphicInfo()));
    }

    @Override
    public boolean isNightCard() {
        return this.nightCard;
    }

    @Override
    public boolean isFlipCard() {
        return flipCard;
    }

    @Override
    public String getFlipCardName() {
        return flipCardName;
    }

    @Override
    public boolean isSplitCard() {
        return splitCard;
    }

    @Override
    public boolean getUsesVariousArt() {
        return usesVariousArt;
    }

    @Override
    public Counters getCounters(Game game) {
        return getCounters(game.getState());
    }

    @Override
    public Counters getCounters(GameState state) {
        return state.getCardState(this.objectId).getCounters();
    }

    /**
     * @return The controller if available otherwise the owner.
     */
    protected UUID getControllerOrOwner() {
        return ownerId;
    }

    @Override
    public boolean addCounters(Counter counter, Ability source, Game game) {
        return addCounters(counter, source, game, null, true);
    }

    @Override
    public boolean addCounters(Counter counter, Ability source, Game game, boolean isEffect) {
        return addCounters(counter, source, game, null, isEffect);
    }

    @Override
    public boolean addCounters(Counter counter, Ability source, Game game, List<UUID> appliedEffects) {
        return addCounters(counter, source, game, appliedEffects, true);
    }

    @Override
    public boolean addCounters(Counter counter, Ability source, Game game, List<UUID> appliedEffects, boolean isEffect) {
        boolean returnCode = true;
        UUID sourceId = getId();
        if (source != null) {
            MageObject object = game.getObject(source.getId());
            if (object instanceof StackObject) {
                sourceId = source.getId();
            } else {
                sourceId = source.getSourceId();
            }
        }
        GameEvent addingAllEvent = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTERS, objectId, sourceId, getControllerOrOwner(), counter.getName(), counter.getCount());
        addingAllEvent.setAppliedEffects(appliedEffects);
        addingAllEvent.setFlag(isEffect);
        if (!game.replaceEvent(addingAllEvent)) {
            int amount = addingAllEvent.getAmount();
            boolean isEffectFlag = addingAllEvent.getFlag();
            int finalAmount = amount;
            for (int i = 0; i < amount; i++) {
                Counter eventCounter = counter.copy();
                eventCounter.remove(eventCounter.getCount() - 1);
                GameEvent addingOneEvent = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTER, objectId, sourceId, getControllerOrOwner(), counter.getName(), 1);
                addingOneEvent.setAppliedEffects(appliedEffects);
                addingOneEvent.setFlag(isEffectFlag);
                if (!game.replaceEvent(addingOneEvent)) {
                    getCounters(game).addCounter(eventCounter);
                    GameEvent addedOneEvent = GameEvent.getEvent(GameEvent.EventType.COUNTER_ADDED, objectId, sourceId, getControllerOrOwner(), counter.getName(), 1);
                    addedOneEvent.setFlag(addingOneEvent.getFlag());
                    game.fireEvent(addedOneEvent);
                } else {
                    finalAmount--;
                    returnCode = false;
                }
            }
            if (finalAmount > 0) {
                GameEvent addedAllEvent = GameEvent.getEvent(GameEvent.EventType.COUNTERS_ADDED, objectId, sourceId, getControllerOrOwner(), counter.getName(), amount);
                addedAllEvent.setFlag(isEffectFlag);
                game.fireEvent(addedAllEvent);
            }
        } else {
            returnCode = false;
        }
        return returnCode;
    }

    @Override
    public void removeCounters(String name, int amount, Game game) {
        int finalAmount = 0;
        for (int i = 0; i < amount; i++) {
            if (!getCounters(game).removeCounter(name, 1)) {
                break;
            }
            GameEvent event = GameEvent.getEvent(GameEvent.EventType.COUNTER_REMOVED, objectId, getControllerOrOwner());
            event.setData(name);
            game.fireEvent(event);
            finalAmount++;
        }
        GameEvent event = GameEvent.getEvent(GameEvent.EventType.COUNTERS_REMOVED, objectId, getControllerOrOwner());
        event.setData(name);
        event.setAmount(finalAmount);
        game.fireEvent(event);
    }

    @Override
    public void removeCounters(Counter counter, Game game) {
        if (counter != null) {
            removeCounters(counter.getName(), counter.getCount(), game);
        }
    }

    @Override
    public String getLogName() {
        if (name.isEmpty()) {
            return GameLog.getNeutralColoredText(EmptyNames.FACE_DOWN_CREATURE.toString());
        } else {
            return GameLog.getColoredObjectIdName(this);
        }
    }

    @Override
    public Card getMainCard() {
        return this;
    }

    @Override
    public FilterMana getColorIdentity() {
        FilterMana mana = new FilterMana();
        mana.setBlack(getManaCost().getText().matches(regexBlack));
        mana.setBlue(getManaCost().getText().matches(regexBlue));
        mana.setGreen(getManaCost().getText().matches(regexGreen));
        mana.setRed(getManaCost().getText().matches(regexRed));
        mana.setWhite(getManaCost().getText().matches(regexWhite));

        for (String rule : getRules()) {
            rule = rule.replaceAll("(?i)<i.*?</i>", ""); // Ignoring reminder text in italic
            if (!mana.isBlack() && (rule.matches(regexBlack) || this.color.isBlack())) {
                mana.setBlack(true);
            }
            if (!mana.isBlue() && (rule.matches(regexBlue) || this.color.isBlue())) {
                mana.setBlue(true);
            }
            if (!mana.isGreen() && (rule.matches(regexGreen) || this.color.isGreen())) {
                mana.setGreen(true);
            }
            if (!mana.isRed() && (rule.matches(regexRed) || this.color.isRed())) {
                mana.setRed(true);
            }
            if (!mana.isWhite() && (rule.matches(regexWhite) || this.color.isWhite())) {
                mana.setWhite(true);
            }
        }
        if (isTransformable()) {
            Card secondCard = getSecondCardFace();
            ObjectColor color = secondCard.getColor(null);
            mana.setBlack(mana.isBlack() || color.isBlack());
            mana.setGreen(mana.isGreen() || color.isGreen());
            mana.setRed(mana.isRed() || color.isRed());
            mana.setBlue(mana.isBlue() || color.isBlue());
            mana.setWhite(mana.isWhite() || color.isWhite());
            for (String rule : secondCard.getRules()) {
                rule = rule.replaceAll("(?i)<i.*?</i>", ""); // Ignoring reminder text in italic
                if (!mana.isBlack() && rule.matches(regexBlack)) {
                    mana.setBlack(true);
                }
                if (!mana.isBlue() && rule.matches(regexBlue)) {
                    mana.setBlue(true);
                }
                if (!mana.isGreen() && rule.matches(regexGreen)) {
                    mana.setGreen(true);
                }
                if (!mana.isRed() && rule.matches(regexRed)) {
                    mana.setRed(true);
                }
                if (!mana.isWhite() && rule.matches(regexWhite)) {
                    mana.setWhite(true);
                }
            }
        }

        return mana;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        game.setZone(getId(), zone);
    }

    @Override
    public void setSpellAbility(SpellAbility ability) {
        spellAbility = ability;
    }

    @Override
    public ObjectColor getColor(Game game) {
        if (game != null) {
            CardAttribute cardAttribute = game.getState().getCardAttribute(getId());
            if (cardAttribute != null) {
                return cardAttribute.getColor();
            }
        }
        return super.getColor(game);
    }

    @Override
    public SubTypeList getSubtype(Game game) {
        if (game != null) {
            CardAttribute cardAttribute = game.getState().getCardAttribute(getId());
            if (cardAttribute != null) {
                return cardAttribute.getSubtype();
            }
        }
        return super.getSubtype(game);
    }

    @Override
    public List<UUID> getAttachments() {
        return attachments;
    }

    @Override
    public boolean addAttachment(UUID permanentId, Game game) {
        if (!this.attachments.contains(permanentId)) {
            Permanent attachment = game.getPermanent(permanentId);
            if (attachment == null) {
                attachment = game.getPermanentEntering(permanentId);
            }
            if (attachment != null) {
                if (!game.replaceEvent(new GameEvent(GameEvent.EventType.ATTACH, objectId, permanentId, attachment.getControllerId()))) {
                    this.attachments.add(permanentId);
                    attachment.attachTo(objectId, game);
                    game.fireEvent(new GameEvent(GameEvent.EventType.ATTACHED, objectId, permanentId, attachment.getControllerId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean removeAttachment(UUID permanentId, Game game) {
        if (this.attachments.contains(permanentId)) {
            Permanent attachment = game.getPermanent(permanentId);
            if (attachment != null) {
                attachment.unattach(game);
            }
            if (!game.replaceEvent(new GameEvent(GameEvent.EventType.UNATTACH, objectId, permanentId, attachment != null ? attachment.getControllerId() : null))) {
                this.attachments.remove(permanentId);
                game.fireEvent(new GameEvent(GameEvent.EventType.UNATTACHED, objectId, permanentId, attachment != null ? attachment.getControllerId() : null));
                return true;
            }
        }
        return false;
    }
}
