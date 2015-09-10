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
package mage.cards;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.MageObjectImpl;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.PlayLandAbility;
import mage.abilities.SpellAbility;
import mage.abilities.mana.ManaAbility;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Rarity;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import static mage.constants.Zone.BATTLEFIELD;
import static mage.constants.Zone.COMMAND;
import static mage.constants.Zone.EXILED;
import static mage.constants.Zone.GRAVEYARD;
import static mage.constants.Zone.HAND;
import static mage.constants.Zone.LIBRARY;
import static mage.constants.Zone.OUTSIDE;
import static mage.constants.Zone.PICK;
import static mage.constants.Zone.STACK;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.CardAttribute;
import mage.game.CardState;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.util.GameLog;
import mage.watchers.Watcher;
import org.apache.log4j.Logger;

public abstract class CardImpl extends MageObjectImpl implements Card {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(CardImpl.class);

    protected UUID ownerId;
    protected int cardNumber;
    public String expansionSetCode;
    protected String tokenSetCode;
    protected Rarity rarity;
    protected boolean canTransform;
    protected Card secondSideCard;
    protected boolean nightCard;
    protected SpellAbility spellAbility;
    protected boolean flipCard;
    protected String flipCardName;
    protected boolean usesVariousArt = false;
    protected boolean splitCard;
    protected boolean morphCard;

    public CardImpl(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs) {
        this(ownerId, cardNumber, name, rarity, cardTypes, costs, SpellAbilityType.BASE);
    }

    public CardImpl(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs, SpellAbilityType spellAbilityType) {
        this(ownerId, name);
        this.rarity = rarity;
        this.cardNumber = cardNumber;
        this.cardType.addAll(Arrays.asList(cardTypes));
        this.manaCost.load(costs);
        setDefaultColor();
        if (cardType.contains(CardType.LAND)) {
            Ability ability = new PlayLandAbility(name);
            ability.setSourceId(this.getId());
            abilities.add(ability);
        } else {
            SpellAbility ability = new SpellAbility(manaCost, name, Zone.HAND, spellAbilityType);
            if (!cardType.contains(CardType.INSTANT)) {
                ability.setTiming(TimingRule.SORCERY);
            }
            ability.setSourceId(this.getId());
            abilities.add(ability);
        }
        this.usesVariousArt = Character.isDigit(this.getClass().getName().charAt(this.getClass().getName().length() - 1));
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
        rarity = card.rarity;

        canTransform = card.canTransform;
        if (canTransform) {
            secondSideCard = card.secondSideCard;
            nightCard = card.nightCard;
        }
        flipCard = card.flipCard;
        flipCardName = card.flipCardName;
        splitCard = card.splitCard;
        usesVariousArt = card.usesVariousArt;
    }

    @Override
    public void assignNewId() {
        this.objectId = UUID.randomUUID();
        this.abilities.newOriginalId();
        this.abilities.setSourceId(objectId);
    }

    public static Card createCard(String name) {
        try {
            return createCard(Class.forName(name));
        } catch (ClassNotFoundException ex) {
            logger.fatal("Error loading card: " + name, ex);
            return null;
        }
    }

    public static Card createCard(Class<?> clazz) {
        try {
            Constructor<?> con = clazz.getConstructor(new Class[]{UUID.class});
            Card card = (Card) con.newInstance(new Object[]{null});
            card.build();
            return card;
        } catch (Exception e) {
            logger.fatal("Error loading card: " + clazz.getCanonicalName(), e);
            return null;
        }
    }

    @Override
    public UUID getOwnerId() {
        return ownerId;
    }

    @Override
    public int getCardNumber() {
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

    protected static final ArrayList<String> rulesError = new ArrayList<String>() {
        {
            add("Exception occured in rules generation");
        }
    };

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
                CardState cardState = game.getState().getCardState(objectId);
                if (cardState != null) {
                    for (String data : cardState.getInfo().values()) {
                        rules.add(data);
                    }
                    for (Ability ability : cardState.getAbilities()) {
                        rules.add(ability.getRule());
                    }
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
        if (otherAbilities == null) {
            return abilities;
        }
        Abilities<Ability> all = new AbilitiesImpl<>();
        all.addAll(abilities);
        all.addAll(otherAbilities);
        return all;
    }

    protected void addAbility(Ability ability) {
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

    @Override
    public SpellAbility getSpellAbility() {
        if (spellAbility == null) {
            for (Ability ability : abilities.getActivatedAbilities(Zone.HAND)) {
                if (ability instanceof SpellAbility
                        && !((SpellAbility) ability).getSpellAbilityType().equals(SpellAbilityType.BASE_ALTERNATE)) {
                    return spellAbility = (SpellAbility) ability;
                }
            }
        }
        return spellAbility;
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
    public List<Mana> getMana() {
        List<Mana> mana = new ArrayList<>();
        for (ManaAbility ability : this.abilities.getManaAbilities(Zone.BATTLEFIELD)) {
            for (Mana netMana : ability.getNetMana(null)) {
                mana.add(netMana);
            }
        }
        return mana;
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag) {
        return this.moveToZone(toZone, sourceId, game, flag, null);
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects) {
        Zone fromZone = game.getState().getZone(objectId);
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, ownerId, fromZone, toZone, appliedEffects);
        if (!game.replaceEvent(event)) {
            if (event.getFromZone() != null) {
                switch (event.getFromZone()) {
                    case GRAVEYARD:
                        game.getPlayer(ownerId).removeFromGraveyard(this, game);
                        break;
                    case HAND:
                        game.getPlayer(ownerId).removeFromHand(this, game);
                        break;
                    case LIBRARY:
                        game.getPlayer(ownerId).removeFromLibrary(this, game);
                        break;
                    case EXILED:
                        game.getExile().removeCard(this, game);
                        break;
                    case OUTSIDE:
                        game.getPlayer(ownerId).getSideboard().remove(this);
                        break;
                    case COMMAND:
                        game.getState().getCommand().remove((Commander) game.getObject(objectId));
                        break;
                    case STACK:
                        StackObject stackObject = game.getStack().getSpell(getSpellAbility().getId());
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
                            game.getStack().remove(stackObject);
                        }
                        break;
                    case PICK:
                    case BATTLEFIELD: // for sacrificing permanents or putting to library
                        break;
                    default:
                        Card sourceCard = game.getCard(sourceId);
                        logger.fatal(new StringBuilder("Invalid from zone [").append(fromZone)
                                .append("] for card [").append(this.getName())
                                .append("] to zone [").append(toZone)
                                .append("] source [").append(sourceCard != null ? sourceCard.getName() : "null").append("]").toString());
                        break;
                }
                game.rememberLKI(objectId, event.getFromZone(), this);
            }

            setFaceDown(false, game);
            updateZoneChangeCounter(game);
            switch (event.getToZone()) {
                case GRAVEYARD:
                    game.getPlayer(ownerId).putInGraveyard(this, game, !flag);
                    break;
                case HAND:
                    game.getPlayer(ownerId).getHand().add(this);
                    break;
                case STACK:
                    game.getStack().push(new Spell(this, this.getSpellAbility().copy(), ownerId, event.getFromZone()));
                    break;
                case EXILED:
                    game.getExile().getPermanentExile().add(this);
                    break;
                case COMMAND:
                    game.addCommander(new Commander(this));
                    break;
                case LIBRARY:
                    if (flag) {
                        game.getPlayer(ownerId).getLibrary().putOnTop(this, game);
                    } else {
                        game.getPlayer(ownerId).getLibrary().putOnBottom(this, game);
                    }
                    break;
                case BATTLEFIELD:
                    PermanentCard permanent = new PermanentCard(this, event.getPlayerId(), game); // controller can be replaced (e.g. Gather Specimens)
                    game.addPermanent(permanent);
                    game.setZone(objectId, Zone.BATTLEFIELD);
                    game.setScopeRelevant(true);
                    game.applyEffects();
                    boolean entered = permanent.entersBattlefield(sourceId, game, event.getFromZone(), true);
                    game.setScopeRelevant(false);
                    game.applyEffects();
                    if (entered) {
                        if (flag) {
                            permanent.setTapped(true);
                        }
                        event.setTarget(permanent);
                    } else {
                        return false;
                    }
                    break;
                default:
                    Card sourceCard = game.getCard(sourceId);
                    logger.fatal(new StringBuilder("Invalid to zone [").append(toZone)
                            .append("] for card [").append(this.getName())
                            .append("] to zone [").append(toZone)
                            .append("] source [").append(sourceCard != null ? sourceCard.getName() : "null").append("]").toString());
                    return false;
            }
            game.setZone(objectId, event.getToZone());
            game.addSimultaneousEvent(event);
            return game.getState().getZone(objectId) == toZone;
        }
        return false;
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        Card mainCard = getMainCard();
        ZoneChangeEvent event = new ZoneChangeEvent(mainCard.getId(), ability.getId(), controllerId, fromZone, Zone.STACK);
        if (!game.replaceEvent(event)) {
            if (event.getFromZone() != null) {
                switch (event.getFromZone()) {
                    case GRAVEYARD:
                        game.getPlayer(ownerId).removeFromGraveyard(mainCard, game);
                        break;
                    case HAND:
                        game.getPlayer(ownerId).removeFromHand(mainCard, game);
                        break;
                    case LIBRARY:
                        game.getPlayer(ownerId).removeFromLibrary(mainCard, game);
                        break;
                    case EXILED:
                        game.getExile().removeCard(mainCard, game);
                        break;
                    case OUTSIDE:
                        game.getPlayer(ownerId).getSideboard().remove(mainCard);
                        break;

                    case COMMAND:
                        game.getState().getCommand().remove((Commander) game.getObject(mainCard.getId()));
                        break;
                    default:
                    //logger.warning("moveToZone, not fully implemented: from="+event.getFromZone() + ", to="+event.getToZone());
                }
                game.rememberLKI(mainCard.getId(), event.getFromZone(), this);
            }
            game.getStack().push(new Spell(this, ability.copy(), controllerId, event.getFromZone()));
            updateZoneChangeCounter(game);
            setZone(event.getToZone(), game);
            game.fireEvent(event);
            return game.getState().getZone(mainCard.getId()) == Zone.STACK;
        }
        return false;
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game) {
        return moveToExile(exileId, name, sourceId, game, null);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, ArrayList<UUID> appliedEffects) {
        Zone fromZone = game.getState().getZone(objectId);
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, ownerId, fromZone, Zone.EXILED, appliedEffects);
        if (!game.replaceEvent(event)) {
            if (fromZone != null) {
                switch (fromZone) {
                    case GRAVEYARD:
                        game.getPlayer(ownerId).removeFromGraveyard(this, game);
                        break;
                    case HAND:
                        game.getPlayer(ownerId).removeFromHand(this, game);
                        break;
                    case LIBRARY:
                        game.getPlayer(ownerId).removeFromLibrary(this, game);
                        break;
                    case EXILED:
                        game.getExile().removeCard(this, game);
                        break;
                    case STACK:
                        StackObject stackObject = game.getStack().getSpell(getId());
                        if (stackObject != null) {
                            game.getStack().remove(stackObject);
                        }
                        break;
                    case PICK:
                        // nothing to do
                        break;
                    default:
                        MageObject object = game.getObject(sourceId);
                        logger.warn(new StringBuilder("moveToExile, not fully implemented: from = ").append(fromZone).append(" - ").append(object != null ? object.getName() : "null"));
                }
                game.rememberLKI(objectId, event.getFromZone(), this);
            }

            if (exileId == null) {
                game.getExile().getPermanentExile().add(this);
            } else {
                game.getExile().createZone(exileId, name).add(this);
            }
            setFaceDown(false, game);
            updateZoneChangeCounter(game);
            game.setZone(objectId, event.getToZone());
            game.addSimultaneousEvent(event);
            return true;
        }
        return false;
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
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, boolean facedown) {
        return this.putOntoBattlefield(game, fromZone, sourceId, controllerId, tapped, facedown, null);
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, boolean facedown, ArrayList<UUID> appliedEffects) {
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, controllerId, fromZone, Zone.BATTLEFIELD, appliedEffects, tapped);
        if (facedown) {
            this.setFaceDown(true, game);
        }
        if (!game.replaceEvent(event)) {
            if (facedown) {
                this.setFaceDown(false, game);
            }
            if (fromZone != null) {
                boolean removed = false;
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
                        game.getExile().removeCard(this, game);
                        removed = true;
                        break;
                    case COMMAND:
                        // command object (commander) is only on the stack, so no removing neccessary here
                        removed = true;
                        break;
                    case PICK:
                        removed = true;
                        break;
                    default:
                        logger.warn("putOntoBattlefield, not fully implemented: fromZone=" + fromZone);
                }
                game.rememberLKI(objectId, event.getFromZone(), this);
                if (!removed) {
                    logger.warn("Couldn't find card in fromZone, card=" + getName() + ", fromZone=" + fromZone);
                }
            }
            updateZoneChangeCounter(game);
            PermanentCard permanent = new PermanentCard(this, event.getPlayerId(), game);
            // make sure the controller of all continuous effects of this card are switched to the current controller
            game.getContinuousEffects().setController(objectId, event.getPlayerId());
            game.addPermanent(permanent);
            setZone(Zone.BATTLEFIELD, game);
            // check if there are counters to add to the permanent (e.g. from non replacement effects like Persist)
            checkForCountersToAdd(permanent, game);
            game.setScopeRelevant(true);
            permanent.setTapped(tapped);
            permanent.setFaceDown(facedown, game);
            boolean entered = permanent.entersBattlefield(sourceId, game, event.getFromZone(), true);
            game.setScopeRelevant(false);
            game.applyEffects();
            if (entered) {
                game.addSimultaneousEvent(new ZoneChangeEvent(permanent, event.getPlayerId(), fromZone, Zone.BATTLEFIELD));
                return true;
            }
        }
        if (facedown) {
            this.setFaceDown(false, game);
        }
        return false;
    }

    private void checkForCountersToAdd(PermanentCard permanent, Game game) {
        Counters countersToAdd = game.getEnterWithCounters(permanent.getId());
        if (countersToAdd != null) {
            for (Counter counter : countersToAdd.values()) {
                permanent.addCounters(counter, game);
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
    public boolean canTransform() {
        return this.canTransform;
    }

    @Override
    public Card getSecondCardFace() {
        return this.secondSideCard;
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
    public void build() {
    }

    @Override
    public boolean getUsesVariousArt() {
        return usesVariousArt;
    }

    @Override
    public Counters getCounters(Game game) {
        return game.getState().getCardState(this.objectId).getCounters();
    }

    @Override
    public void addCounters(String name, int amount, Game game) {
        addCounters(name, amount, game, null);
    }

    @Override
    public void addCounters(String name, int amount, Game game, ArrayList<UUID> appliedEffects) {
        GameEvent countersEvent = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTERS, objectId, ownerId, name, amount);
        countersEvent.setAppliedEffects(appliedEffects);
        if (!game.replaceEvent(countersEvent)) {
            for (int i = 0; i < countersEvent.getAmount(); i++) {
                GameEvent event = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTER, objectId, ownerId, name, 1);
                event.setAppliedEffects(appliedEffects);
                if (!game.replaceEvent(event)) {
                    game.getState().getCardState(this.objectId).getCounters().addCounter(name, 1);
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER_ADDED, objectId, ownerId, name, 1));
                }
            }
        }
    }

    @Override
    public void addCounters(Counter counter, Game game) {
        addCounters(counter, game, null);
    }

    @Override
    public void addCounters(Counter counter, Game game, ArrayList<UUID> appliedEffects) {
        GameEvent countersEvent = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTERS, objectId, ownerId, counter.getName(), counter.getCount());
        countersEvent.setAppliedEffects(appliedEffects);
        if (!game.replaceEvent(countersEvent)) {
            int amount = countersEvent.getAmount();
            for (int i = 0; i < amount; i++) {
                Counter eventCounter = counter.copy();
                eventCounter.remove(amount - 1);
                GameEvent event = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTER, objectId, ownerId, counter.getName(), 1);
                event.setAppliedEffects(appliedEffects);
                if (!game.replaceEvent(event)) {
                    game.getState().getCardState(this.objectId).getCounters().addCounter(eventCounter);
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER_ADDED, objectId, ownerId, counter.getName(), 1));
                }
            }
        }
    }

    @Override
    public void removeCounters(String name, int amount, Game game) {
        for (int i = 0; i < amount; i++) {
            game.getState().getCardState(this.objectId).getCounters().removeCounter(name, 1);
            GameEvent event = GameEvent.getEvent(GameEvent.EventType.COUNTER_REMOVED, objectId, ownerId);
            event.setData(name);
            game.fireEvent(event);
        }
    }

    @Override
    public void removeCounters(Counter counter, Game game) {
        removeCounters(counter.getName(), counter.getCount(), game);
    }

    @Override
    public String getLogName() {
        if (name.isEmpty()) {
            return GameLog.getNeutralColoredText("face down card");
        } else {
            return GameLog.getColoredObjectIdName(this);
        }
    }

    @Override
    public Card getMainCard() {
        return this;
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
        return super.getColor(game); //To change body of generated methods, choose Tools | Templates.
    }
}
