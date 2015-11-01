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
package mage.game.permanent;

import java.util.ArrayList;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.LevelerCard;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentCard extends PermanentImpl {

    protected int maxLevelCounters;
    protected Card card;
    // protected int zoneChangeCounter;

    public PermanentCard(Card card, UUID controllerId, Game game) {
        super(card.getId(), card.getOwnerId(), controllerId, card.getName());
        // this.card = card.copy();
        this.card = card;
        init(card, game);
    }

    private void init(Card card, Game game) {
        copyFromCard(card);
        // this.zoneChangeCounter = card.getZoneChangeCounter(game);
        /*if (card.getCardType().contains(CardType.PLANESWALKER)) {
         this.loyalty = new MageInt(card.getLoyalty().getValue());
         }*/
        if (card instanceof LevelerCard) {
            maxLevelCounters = ((LevelerCard) card).getMaxLevelCounters();
        }
        if (canTransform()) {
            if (game.getState().getValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + getId()) != null) {
                game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + getId(), null);
                setTransformed(true);
                TransformAbility.transform(this, getSecondCardFace(), game);
            }
        }
    }

    public PermanentCard(final PermanentCard permanent) {
        super(permanent);
        this.card = permanent.card.copy();
        this.maxLevelCounters = permanent.maxLevelCounters;
    }

    @Override
    public void reset(Game game) {
        // when the permanent is reset, copy all original values from the card
        // must copy card each reset so that the original values don't get modified
        copyFromCard(card);
        super.reset(game);
    }

    protected void copyFromCard(Card card) {
        this.name = card.getName();
        this.abilities.clear();
        if (this.faceDown) {
            for (Ability ability : card.getAbilities()) {
                if (ability.getWorksFaceDown()) {
                    this.abilities.add(ability.copy());
                }
            }
        } else {
            this.abilities = card.getAbilities().copy();
        }
        this.abilities.setControllerId(this.controllerId);
        this.abilities.setSourceId(objectId);
        this.cardType.clear();
        this.cardType.addAll(card.getCardType());
        this.color = card.getColor(null).copy();
        this.manaCost = card.getManaCost().copy();
        this.power = card.getPower().copy();
        this.toughness = card.getToughness().copy();
        if (card instanceof PermanentCard) {
            this.maxLevelCounters = ((PermanentCard) card).maxLevelCounters;
        }
        this.subtype.clear();
        this.subtype.addAll(card.getSubtype());
        this.supertype.clear();
        this.supertype.addAll(card.getSupertype());
        this.expansionSetCode = card.getExpansionSetCode();
        this.rarity = card.getRarity();
        this.cardNumber = card.getCardNumber();
        this.usesVariousArt = card.getUsesVariousArt();

        canTransform = card.canTransform();
        if (canTransform) {
            secondSideCard = card.getSecondCardFace();
            nightCard = card.isNightCard();
        }
        this.flipCard = card.isFlipCard();
        this.flipCardName = card.getFlipCardName();
    }

    public Card getCard() {
        return card;
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag) {
        return moveToZone(toZone, sourceId, game, flag, null);
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects) {
        Zone fromZone = game.getState().getZone(objectId);
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            ZoneChangeEvent event = new ZoneChangeEvent(this, sourceId, controllerId, fromZone, toZone, appliedEffects);
            if (!game.replaceEvent(event)) {
                controller.removeFromBattlefield(this, game);
                Player owner = game.getPlayer(ownerId);
                game.rememberLKI(objectId, Zone.BATTLEFIELD, this);
                if (owner != null) {
                    card.updateZoneChangeCounter(game);
                    switch (event.getToZone()) {
                        case GRAVEYARD:
                            owner.putInGraveyard(card, game, !flag);
                            break;
                        case HAND:
                            owner.getHand().add(card);
                            break;
                        case EXILED:
                            game.getExile().getPermanentExile().add(card);
                            break;
                        case COMMAND:
                            game.addCommander(new Commander(card));
                            break;
                        case LIBRARY:
                            if (flag) {
                                owner.getLibrary().putOnTop(card, game);
                            } else {
                                owner.getLibrary().putOnBottom(card, game);
                            }
                            break;
                        case BATTLEFIELD:
                            //should never happen
                            break;
                    }
                    game.setZone(objectId, event.getToZone());
                    game.addSimultaneousEvent(event);
                    return game.getState().getZone(objectId) == toZone;
                }
            }
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
        Player controller = game.getPlayer(controllerId);
        if (controller != null && controller.removeFromBattlefield(this, game)) {
            ZoneChangeEvent event = new ZoneChangeEvent(this, sourceId, ownerId, fromZone, Zone.EXILED, appliedEffects);
            if (!game.replaceEvent(event)) {
                game.rememberLKI(objectId, Zone.BATTLEFIELD, this);
                // update zone change counter of original card
                card.updateZoneChangeCounter(game);
                if (exileId == null) {
                    game.getExile().getPermanentExile().add(card);
                } else {
                    game.getExile().createZone(exileId, name).add(card);
                }
                game.setZone(objectId, event.getToZone());
                game.addSimultaneousEvent(event);
                return true;
            }
        }
        return false;
    }

    @Override
    public PermanentCard copy() {
        return new PermanentCard(this);
    }

    public int getMaxLevelCounters() {
        return this.maxLevelCounters;
    }

    @Override
    public boolean turnFaceUp(Game game, UUID playerId) {
        if (super.turnFaceUp(game, playerId)) {
            setManifested(false);
            setMorphed(false);
            return true;
        }
        return false;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        card.adjustTargets(ability, game);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        card.adjustCosts(ability, game);
    }

    @Override
    public void adjustChoices(Ability ability, Game game) {
        card.adjustChoices(ability, game);
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        if (faceDown) { // face down permanent has always {0} mana costs
            manaCost.clear();
            return manaCost;
        }
        return super.getManaCost();
    }

    @Override
    public int getZoneChangeCounter(Game game) {
        return card.getZoneChangeCounter(game);
    }

    @Override
    public void updateZoneChangeCounter(Game game) {
        card.updateZoneChangeCounter(game);
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        card.setZoneChangeCounter(value, game);
    }

}
