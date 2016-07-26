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
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import mage.MageObject;
import mage.MageObjectImpl;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

public class Token extends MageObjectImpl {

    protected String description;
    private final ArrayList<UUID> lastAddedTokenIds = new ArrayList<>();
    private UUID lastAddedTokenId;
    private int tokenType;
    private String originalCardNumber;
    private String originalExpansionSetCode;
    private boolean expansionSetCodeChecked;
    private Card copySourceCard; // the card the Token is a copy from

    // list of set codes tokene images are available for
    protected List<String> availableImageSetCodes = new ArrayList<>();

    public enum Type {

        FIRST(1),
        SECOND(2);

        int code;

        Type(int code) {
            this.code = code;
        }

        int getCode() {
            return this.code;
        }
    }

    public Token(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public Token(String name, String description, int power, int toughness) {
        this(name, description);
        this.power.setValue(power);
        this.toughness.setValue(toughness);
    }

    public Token(String name, String description, ObjectColor color, List<String> subtype, int power, int toughness, Abilities<Ability> abilities) {
        this(name, description);
        this.cardType.add(CardType.CREATURE);
        this.color = color.copy();
        this.subtype = subtype;
        this.power.setValue(power);
        this.toughness.setValue(toughness);
        if (abilities != null) {
            this.abilities = abilities.copy();
        }
        this.expansionSetCodeChecked = false;
    }

    public Token(final Token token) {
        super(token);
        this.description = token.description;
        this.tokenType = token.tokenType;
        this.lastAddedTokenId = token.lastAddedTokenId;
        this.lastAddedTokenIds.addAll(token.lastAddedTokenIds);
        this.originalCardNumber = token.originalCardNumber;
        this.originalExpansionSetCode = token.originalExpansionSetCode;
        this.expansionSetCodeChecked = token.expansionSetCodeChecked;
        this.copySourceCard = token.copySourceCard; // will never be changed
        this.availableImageSetCodes = token.availableImageSetCodes;
    }

    public String getDescription() {
        return description;
    }

    public UUID getLastAddedToken() {
        return lastAddedTokenId;
    }

    public ArrayList<UUID> getLastAddedTokenIds() {
        ArrayList<UUID> ids = new ArrayList<>();
        ids.addAll(lastAddedTokenIds);
        return ids;
    }

    public void addAbility(Ability ability) {
        ability.setSourceId(this.getId());
        abilities.add(ability);
    }

    @Override
    public Token copy() {
        return new Token(this);
    }

    public boolean putOntoBattlefield(int amount, Game game, UUID sourceId, UUID controllerId) {
        return this.putOntoBattlefield(amount, game, sourceId, controllerId, false, false);
    }

    public boolean putOntoBattlefield(int amount, Game game, UUID sourceId, UUID controllerId, boolean tapped, boolean attacking) {
        return putOntoBattlefield(amount, game, sourceId, controllerId, tapped, attacking, null);
    }

    public boolean putOntoBattlefield(int amount, Game game, UUID sourceId, UUID controllerId, boolean tapped, boolean attacking, UUID attackedPlayer) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        lastAddedTokenIds.clear();

        // moved here from CreateTokenEffect because not all cards that create tokens use CreateTokenEffect
        // they use putOntoBattlefield directly
        // TODO: Check this setCode handling because it makes no sense if token put into play with e.g. "Feldon of the third Path"
        String setCode = null;
        if (this.getOriginalExpansionSetCode() != null && !this.getOriginalExpansionSetCode().isEmpty()) {
            setCode = this.getOriginalExpansionSetCode();
        } else {
            Card source = game.getCard(sourceId);
            if (source != null) {
                setCode = source.getExpansionSetCode();
            } else {
                MageObject object = game.getObject(sourceId);
                if (object instanceof PermanentToken) {
                    ((PermanentToken) object).getExpansionSetCode();
                }
            }
        }
        if (!expansionSetCodeChecked) {
            expansionSetCodeChecked = this.updateExpansionSetCode(setCode);
        }

        GameEvent event = new GameEvent(EventType.CREATE_TOKEN, null, sourceId, controllerId, amount, this.getCardType().contains(CardType.CREATURE));
        if (!game.replaceEvent(event)) {
            amount = event.getAmount();

            List<Permanent> permanents = new ArrayList<>();
            List<Permanent> permanentsEntered = new ArrayList<>();

            for (int i = 0; i < amount; i++) {
                PermanentToken newToken = new PermanentToken(this, event.getPlayerId(), setCode, game); // use event.getPlayerId() because it can be replaced by replacement effect
                game.getState().addCard(newToken);
                permanents.add(newToken);
                game.getPermanentsEntering().put(newToken.getId(), newToken);
                newToken.setTapped(tapped);
            }
            game.setScopeRelevant(true);
            for (Permanent permanent : permanents) {
                if (permanent.entersBattlefield(sourceId, game, Zone.OUTSIDE, true)) {
                    permanentsEntered.add(permanent);
                } else {
                    game.getPermanentsEntering().remove(permanent.getId());
                }
            }
            game.setScopeRelevant(false);
            for (Permanent permanent : permanentsEntered) {
                game.addPermanent(permanent);
                permanent.setZone(Zone.BATTLEFIELD, game);
                game.getPermanentsEntering().remove(permanent.getId());

                this.lastAddedTokenIds.add(permanent.getId());
                this.lastAddedTokenId = permanent.getId();
                game.addSimultaneousEvent(new ZoneChangeEvent(permanent, permanent.getControllerId(), Zone.OUTSIDE, Zone.BATTLEFIELD));
                if (attacking && game.getCombat() != null) {
                    game.getCombat().addAttackingCreature(permanent.getId(), game, attackedPlayer);
                }
                if (!game.isSimulation()) {
                    game.informPlayers(controller.getLogName() + " puts a " + permanent.getLogName() + " token onto the battlefield");
                }

            }
            return true;
        }
        return false;
    }
        
    public void setPower(int power) {
        this.power.setValue(power);
    }
    
    public void setToughness(int toughness) {
        this.toughness.setValue(toughness);
    }

    public int getTokenType() {
        return tokenType;
    }

    public void setTokenType(int tokenType) {
        this.tokenType = tokenType;
    }

    public String getOriginalCardNumber() {
        return originalCardNumber;
    }

    public void setOriginalCardNumber(String originalCardNumber) {
        this.originalCardNumber = originalCardNumber;
    }

    public String getOriginalExpansionSetCode() {
        return originalExpansionSetCode;
    }

    public void setOriginalExpansionSetCode(String originalExpansionSetCode) {
        this.originalExpansionSetCode = originalExpansionSetCode;
    }

    public Card getCopySourceCard() {
        return copySourceCard;
    }

    public void setCopySourceCard(Card copySourceCard) {
        if (copySourceCard != null) {
            this.copySourceCard = copySourceCard.copy();
        }
    }

    public void setExpansionSetCodeForImage(String code) {
        if (availableImageSetCodes.size() > 0) {
            if (availableImageSetCodes.contains(code)) {
                setOriginalExpansionSetCode(code);
            } else {
                // we should not set random set if appropriate set is already used
                if (getOriginalExpansionSetCode() == null || getOriginalExpansionSetCode().isEmpty()
                        || !availableImageSetCodes.contains(getOriginalExpansionSetCode())) {
                    setOriginalExpansionSetCode(availableImageSetCodes.get(new Random().nextInt(availableImageSetCodes.size())));
                }
            }
        } else {
            if (getOriginalExpansionSetCode() == null || getOriginalExpansionSetCode().isEmpty()) {
                setOriginalExpansionSetCode(code);
            }
        }
    }

    public boolean updateExpansionSetCode(String setCode) {
        if (setCode == null || setCode.isEmpty()) {
            return false;
        }
        this.setExpansionSetCodeForImage(setCode);
        return true;
    }
}
