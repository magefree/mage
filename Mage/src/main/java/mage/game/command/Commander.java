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
package mage.game.command;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.CastCommanderAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.Card;
import mage.cards.FrameStyle;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.GameLog;

public class Commander implements CommandObject {

    private final Card sourceObject;
    private final Abilities<Ability> abilites = new AbilitiesImpl<>();

    public Commander(Card card) {
        this.sourceObject = card;
        abilites.add(new CastCommanderAbility(card));
        for (Ability ability : card.getAbilities()) {
            if (!(ability instanceof SpellAbility)) {
                Ability newAbility = ability.copy();
                abilites.add(newAbility);
            }
        }
    }

    private Commander(Commander copy) {
        this.sourceObject = copy.sourceObject;
    }

    @Override
    public Card getSourceObject() {
        return sourceObject;
    }

    @Override
    public UUID getSourceId() {
        return sourceObject.getId();
    }

    @Override
    public UUID getControllerId() {
        return sourceObject.getOwnerId();
    }

    @Override
    public void assignNewId() {
    }

    @Override
    public CommandObject copy() {
        return new Commander(this);
    }

    @Override
    public String getName() {
        return sourceObject.getName();
    }

    @Override
    public String getIdName() {
        return sourceObject.getName() + " [" + sourceObject.getId().toString().substring(0, 3) + ']';
    }

    @Override
    public String getLogName() {
        return GameLog.getColoredObjectIdName(this);
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public EnumSet<CardType> getCardType() {
        return sourceObject.getCardType();
    }

    @Override
    public List<String> getSubtype(Game game) {
        return sourceObject.getSubtype(game);
    }

    @Override
    public boolean hasSubtype(String subtype, Game game) {
        return sourceObject.hasSubtype(subtype, game);
    }

    @Override
    public List<String> getSupertype() {
        return sourceObject.getSupertype();
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return abilites;
    }

    @Override
    public boolean hasAbility(UUID abilityId, Game game) {
        if (this.getAbilities().containsKey(abilityId)) {
            return true;
        }
        Abilities<Ability> otherAbilities = game.getState().getAllOtherAbilities(getId());
        return otherAbilities != null && otherAbilities.containsKey(abilityId);
    }

    @Override
    public ObjectColor getColor(Game game) {
        return sourceObject.getColor(game);
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        return sourceObject.getFrameColor(game);
    }

    @Override
    public FrameStyle getFrameStyle() {
        return sourceObject.getFrameStyle();
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return sourceObject.getManaCost();
    }

    @Override
    public int getConvertedManaCost() {
        return sourceObject.getConvertedManaCost();
    }

    @Override
    public MageInt getPower() {
        return sourceObject.getPower();
    }

    @Override
    public MageInt getToughness() {
        return sourceObject.getToughness();
    }

    @Override
    public int getStartingLoyalty() {
        return sourceObject.getStartingLoyalty();
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
    }

    @Override
    public void setCopy(boolean isCopy) {
    }

    @Override
    public boolean isCopy() {
        return false;
    }

    @Override
    public UUID getId() {
        return sourceObject.getId();
    }

    @Override
    public String getImageName() {
        return sourceObject.getImageName();
    }

    @Override
    public int getZoneChangeCounter(Game game) {
        return sourceObject.getZoneChangeCounter(game);
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        sourceObject.updateZoneChangeCounter(game, event);
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        sourceObject.setZoneChangeCounter(value, game);
    }

}
