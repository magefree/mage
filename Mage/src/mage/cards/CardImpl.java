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

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageObjectImpl;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.PlayLandAbility;
import mage.abilities.SpellAbility;
import mage.abilities.mana.ManaAbility;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.watchers.Watcher;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.*;

public abstract class CardImpl<T extends CardImpl<T>> extends MageObjectImpl<T> implements Card {
    private static final long serialVersionUID = 1L;

    private final static Logger logger = Logger.getLogger(CardImpl.class);

    protected UUID ownerId;
    protected int cardNumber;
    protected List<Watcher> watchers = new ArrayList<Watcher>();
    protected String expansionSetCode;
    protected Rarity rarity;
    protected boolean faceDown;
    protected boolean canTransform;
    protected Card secondSideCard;
    protected boolean nightCard;
    protected SpellAbility spellAbility;
    protected boolean flipCard;
    protected int zoneChangeCounter = 1;
    protected Map<String, String> info;

    public CardImpl(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs) {
        this(ownerId, name);
        this.rarity = rarity;
        this.cardNumber = cardNumber;
        this.cardType.addAll(Arrays.asList(cardTypes));
        this.manaCost.load(costs);
        if (cardType.contains(CardType.LAND))
            addAbility(new PlayLandAbility(name));
        else
            addAbility(new SpellAbility(manaCost, name));
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
        for (Watcher watcher: (List<Watcher>)card.watchers) {
            this.watchers.add(watcher.copy());
        }
        faceDown = card.faceDown;

        canTransform = card.canTransform;
        if (canTransform) {
            secondSideCard = card.secondSideCard;
            nightCard = card.nightCard;
        }

        if (card.info != null) {
            info = new HashMap<String, String>();
            info.putAll(card.info);
        }
    }

    @Override
    public void assignNewId() {
        this.objectId = UUID.randomUUID();
        this.abilities.newOriginalId();
        this.abilities.setSourceId(objectId);
    }

    public static Card createCard(String name) {
        try {
            Class<?> theClass = Class.forName(name);
            Constructor<?> con = theClass.getConstructor(new Class[]{UUID.class});
            Card card = (Card) con.newInstance(new Object[]{null});
            return card;
        } catch (Exception e) {
            logger.fatal("Error loading card: " + name, e);
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
    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    @Override
    public List<String> getRules() {
        List<String> rules = abilities.getRules(this.name);
        if (cardType.contains(CardType.INSTANT) || cardType.contains(CardType.SORCERY)) {
            rules.add(0, getSpellAbility().getRule(this.name));
        }
        if (info != null) {
            for (String data : info.values()) {
                rules.add(data);
            }
        }
        return rules;
    }

    @Override
    public void addAbility(Ability ability) {
        ability.setSourceId(this.getId());
        abilities.add(ability);
    }

    @Override
    public void addWatcher(Watcher watcher) {
        watcher.setSourceId(this.getId());
        watcher.setControllerId(this.ownerId);
        watchers.add(watcher);
    }

    @Override
    public SpellAbility getSpellAbility() {
        if (spellAbility == null) {
            for (Ability ability : abilities.getActivatedAbilities(Zone.HAND)) {
                if (ability instanceof SpellAbility)
                    spellAbility = (SpellAbility) ability;
            }
        }
        return spellAbility;
    }

    @Override
    public void setControllerId(UUID controllerId) {
        abilities.setControllerId(controllerId);
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
        abilities.setControllerId(ownerId);
    }

    @Override
    public List<Watcher> getWatchers() {
        return watchers;
    }

    @Override
    public String getExpansionSetCode() {
        return expansionSetCode;
    }

    @Override
    public void setExpansionSetCode(String expansionSetCode) {
        this.expansionSetCode = expansionSetCode;
    }

    @Override
    public List<Mana> getMana() {
        List<Mana> mana = new ArrayList<Mana>();
        for (ManaAbility ability : this.abilities.getManaAbilities(Zone.BATTLEFIELD)) {
            mana.add(ability.getNetMana(null));
        }
        return mana;
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag) {
        Zone fromZone = game.getState().getZone(objectId);
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, ownerId, fromZone, toZone);
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
                    case STACK:
                    case OUTSIDE:
                    case PICK:
                        break;
                    default:
                        logger.fatal("invalid zone for card - " + fromZone);
                        break;
                }
                game.rememberLKI(objectId, event.getFromZone(), this);
            }
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
                case LIBRARY:
                    if (flag)
                        game.getPlayer(ownerId).getLibrary().putOnTop(this, game);
                    else
                        game.getPlayer(ownerId).getLibrary().putOnBottom(this, game);
                    break;
                case BATTLEFIELD:
                    PermanentCard permanent = new PermanentCard(this, ownerId);
                    game.getBattlefield().addPermanent(permanent);
                    permanent.entersBattlefield(sourceId, game);
                    game.applyEffects();
                    if (flag)
                        permanent.setTapped(true);
                    event.setTarget(permanent);
                    break;
                default:
                    logger.fatal("invalid zone for card - " + toZone);
                    return false;
            }
            setControllerId(ownerId);
            updateZoneChangeCounter();
            game.setZone(objectId, event.getToZone());
            game.fireEvent(event);
            return game.getState().getZone(objectId) == toZone;
        }
        return false;
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, ability.getId(), controllerId, fromZone, Zone.STACK);
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
                    default:
                        //logger.warning("moveToZone, not fully implemented: from="+event.getFromZone() + ", to="+event.getToZone());
                }
                game.rememberLKI(objectId, event.getFromZone(), this);
            }
            game.getStack().push(new Spell(this, ability.copy(), controllerId, event.getFromZone()));
            game.setZone(objectId, event.getToZone());
            game.fireEvent(event);
            return game.getState().getZone(objectId) == Zone.STACK;
        }
        return false;
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game) {
        Zone fromZone = game.getState().getZone(objectId);
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, ownerId, fromZone, Zone.EXILED);
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
                    default:
                        //logger.warning("moveToExile, not fully implemented: from="+fromZone);
                }
                game.rememberLKI(objectId, event.getFromZone(), this);
            }

            if (exileId == null) {
                game.getExile().getPermanentExile().add(this);
            } else {
                game.getExile().createZone(exileId, name).add(this);
            }
            updateZoneChangeCounter();
            game.setZone(objectId, event.getToZone());
            game.fireEvent(event);
            return true;
        }
        return false;
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId) {
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, controllerId, fromZone, Zone.BATTLEFIELD);
        if (!game.replaceEvent(event)) {
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
                    default:
                        //logger.warning("putOntoBattlefield, not fully implemented: from="+fromZone);
                }
                game.rememberLKI(objectId, event.getFromZone(), this);
                if (!removed) {
                    logger.warn("Couldn't find card in fromZone, card=" + getName() + ", fromZone=" + fromZone);
                }
            }
            updateZoneChangeCounter();
            PermanentCard permanent = new PermanentCard(this, controllerId);
            game.getBattlefield().addPermanent(permanent);
            game.setZone(objectId, Zone.BATTLEFIELD);
            game.applyEffects();
            permanent.entersBattlefield(sourceId, game);
            game.applyEffects();
            game.fireEvent(new ZoneChangeEvent(permanent, controllerId, fromZone, Zone.BATTLEFIELD));
            return true;
        }
        return false;
    }

    @Override
    public void setCardNumber(int cid) {
        this.cardNumber = cid;
    }

    @Override
    public void setFaceDown(boolean value) {
        this.faceDown = value;
    }

    @Override
    public boolean isFaceDown() {
        return this.faceDown;
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
    public void setSecondCardFace(Card card) {
        this.secondSideCard = card;
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
    public int getZoneChangeCounter() {
        return zoneChangeCounter;
    }

    private void updateZoneChangeCounter() {
        zoneChangeCounter++;
    }

    @Override
    public void addInfo(String key, String value) {
        if (info == null) {
            info = new HashMap<String, String>();
        }
        info.put(key, value);
    }
}
