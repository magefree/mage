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
import mage.abilities.*;
import mage.abilities.effects.common.NameACardEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.repository.PluginClassloaderRegistery;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.*;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.util.GameLog;
import mage.util.SubTypeList;
import mage.watchers.Watcher;
import org.apache.log4j.Logger;

public abstract class CardImpl extends MageObjectImpl implements Card {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(CardImpl.class);

    protected UUID ownerId;
    protected String cardNumber;
    public String expansionSetCode;
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
    protected boolean allCreatureTypes;

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
        try {
            Card card;
            if (setInfo == null) {
                Constructor<?> con = clazz.getConstructor(UUID.class);
                card = (Card) con.newInstance(new Object[]{null});
            } else {
                Constructor<?> con = clazz.getConstructor(UUID.class, CardSetInfo.class);
                card = (Card) con.newInstance(null, setInfo);
            }
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

    protected static final ArrayList<String> rulesError = new ArrayList<String>() {
        {
            add("Exception occurred in rules generation");
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

//    @Override
//    public void adjustCosts(Ability ability, Game game) {
//    }
    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue;
        TargetPermanent oldTargetPermanent;
        FilterPermanent permanentFilter;
        int minTargets;
        int maxTargets;
        switch (ability.getTargetAdjustment()) {
            case NONE:
                break;
            case X_CMC_EQUAL_PERM:
                xValue = ability.getManaCostsToPay().getX();
                oldTargetPermanent = (TargetPermanent) ability.getTargets().get(0);
                minTargets = oldTargetPermanent.getMinNumberOfTargets();
                maxTargets = oldTargetPermanent.getMaxNumberOfTargets();
                permanentFilter = oldTargetPermanent.getFilter().copy();
                permanentFilter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue));
                ability.getTargets().clear();
                ability.getTargets().add(new TargetPermanent(minTargets, maxTargets, permanentFilter, false));
                break;
            case X_TARGETS:
                xValue = ability.getManaCostsToPay().getX();
                permanentFilter = ((TargetPermanent) ability.getTargets().get(0)).getFilter();
                ability.getTargets().clear();
                ability.addTarget(new TargetPermanent(xValue, permanentFilter));
                break;
            case X_POWER_LEQ:// Minamo Sightbender only
                xValue = ability.getManaCostsToPay().getX();
                oldTargetPermanent = (TargetPermanent) ability.getTargets().get(0);
                minTargets = oldTargetPermanent.getMinNumberOfTargets();
                maxTargets = oldTargetPermanent.getMaxNumberOfTargets();
                permanentFilter = oldTargetPermanent.getFilter().copy();
                permanentFilter.add(new PowerPredicate(ComparisonType.FEWER_THAN, xValue + 1));
                ability.getTargets().clear();
                ability.getTargets().add(new TargetPermanent(minTargets, maxTargets, permanentFilter, false));
                break;
            case X_CMC_EQUAL_GY_CARD: //Geth, Lord of the Vault only
                xValue = ability.getManaCostsToPay().getX();
                TargetCard oldTarget = (TargetCard) ability.getTargets().get(0);
                FilterCard filterCard = oldTarget.getFilter().copy();
                filterCard.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue));
                ability.getTargets().clear();
                ability.getTargets().add(new TargetCardInOpponentsGraveyard(filterCard));
                break;
            case CHOSEN_NAME: //Declaration of Naught only
                ability.getTargets().clear();
                FilterSpell filterSpell = new FilterSpell("spell with the chosen name");
                filterSpell.add(new NamePredicate((String) game.getState().getValue(ability.getSourceId().toString() + NameACardEffect.INFO_KEY)));
                TargetSpell target = new TargetSpell(1, filterSpell);
                ability.addTarget(target);
                break;
            case CHOSEN_COLOR: //Pentarch Paladin only
                ObjectColor chosenColor = (ObjectColor) game.getState().getValue(ability.getSourceId() + "_color");
                ability.getTargets().clear();
                FilterPermanent filter = new FilterPermanent("permanent of the chosen color.");
                if (chosenColor != null) {
                    filter.add(new ColorPredicate(chosenColor));
                } else {
                    filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, -5));// Pretty sure this is always false
                }
                oldTargetPermanent = new TargetPermanent(filter);
                ability.addTarget(oldTargetPermanent);
                break;
        }
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
                = new ZoneChangeInfo.Stack(event, new Spell(this, ability.copy(), controllerId, event.getFromZone()));
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
                    removed = game.getStack().remove(stackObject);
                    lkiObject = stackObject;
                }
                break;
            case COMMAND:
                lkiObject = game.getObject(objectId);
                if (lkiObject != null) {
                    removed = game.getState().getCommand().remove(game.getObject(objectId));
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
        assert cardInfo.size() == 1;    // should find 1 second side card
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
        return addCounters(counter, source, game, null);
    }

    @Override
    public boolean addCounters(Counter counter, Ability source, Game game, List<UUID> appliedEffects) {
        boolean returnCode = true;
        UUID sourceId = (source == null ? getId() : source.getSourceId());
        GameEvent countersEvent = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTERS, objectId, sourceId, getControllerOrOwner(), counter.getName(), counter.getCount());
        countersEvent.setAppliedEffects(appliedEffects);
        if (!game.replaceEvent(countersEvent)) {
            int amount = countersEvent.getAmount();
            int finalAmount = amount;
            for (int i = 0; i < amount; i++) {
                Counter eventCounter = counter.copy();
                eventCounter.remove(eventCounter.getCount() - 1);
                GameEvent event = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTER, objectId, sourceId, getControllerOrOwner(), counter.getName(), 1);
                event.setAppliedEffects(appliedEffects);
                if (!game.replaceEvent(event)) {
                    getCounters(game).addCounter(eventCounter);
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER_ADDED, objectId, sourceId, getControllerOrOwner(), counter.getName(), 1));
                } else {
                    finalAmount--;
                    returnCode = false;
                }
            }
            if (finalAmount > 0) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTERS_ADDED, objectId, sourceId, getControllerOrOwner(), counter.getName(), amount));
            }
        } else {
            returnCode = false;
        }
        return returnCode;
    }

    @Override
    public void removeCounters(String name, int amount, Game game) {
        for (int i = 0; i < amount; i++) {
            if (!getCounters(game).removeCounter(name, 1)) {
                break;
            }
            GameEvent event = GameEvent.getEvent(GameEvent.EventType.COUNTER_REMOVED, objectId, getControllerOrOwner());
            event.setData(name);
            game.fireEvent(event);
        }
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

    public boolean isAllCreatureTypes() {
        return allCreatureTypes;
    }

    public void setIsAllCreatureTypes(boolean value) {
        allCreatureTypes = value;
    }
}
