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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.CardType;
import mage.game.Game;
import mage.util.GameLog;

/**
 * @author nantuko
 */
public class Emblem implements CommandObject {

    private static List emptyList = new ArrayList();
    private static ObjectColor emptyColor = new ObjectColor();
    private static ManaCosts emptyCost = new ManaCostsImpl();

    private String name;
    private UUID id;
    private UUID controllerId;
    private UUID sourceId;
    private Abilities<Ability> abilites = new AbilitiesImpl<>();
    private String expansionSetCodeForImage = null;

    public Emblem() {
        this.id = UUID.randomUUID();
    }

    public Emblem(final Emblem emblem) {
        this.id = emblem.id;
        this.name = emblem.name;
        this.controllerId = emblem.controllerId;
        this.sourceId = emblem.sourceId;
        this.abilites = emblem.abilites.copy();
    }

    @Override
    public void assignNewId() {
        this.id = UUID.randomUUID();
    }

    @Override
    public UUID getSourceId() {
        return this.sourceId;
    }

    @Override
    public UUID getControllerId() {
        return this.controllerId;
    }

    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
        this.abilites.setControllerId(controllerId);
    }

    public void setSourceId(UUID sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIdName() {
        return getName() + " [" + getId().toString().substring(0, 3) + "]";
    }

    @Override
    public String getLogName() {
        return GameLog.getColoredObjectIdName(this);
    }

    @Override
    public String getImageName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<CardType> getCardType() {
        return emptyList;
    }

    @Override
    public List<String> getSubtype(Game game) {
        return emptyList;
    }

    @Override
    public boolean hasSubtype(String subtype, Game game) {
        return false;
    }

    @Override
    public List<String> getSupertype() {
        return emptyList;
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return abilites;
    }

    @Override
    public boolean hasAbility(UUID abilityId, Game game) {
        return abilites.containsKey(abilityId);
    }

    @Override
    public ObjectColor getColor(Game game) {
        return emptyColor;
    }
    
    @Override 
    public ObjectColor getFrameColor(Game game) {
        return emptyColor;
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return emptyCost;
    }

    @Override
    public int getConvertedManaCost() {
        return 0;
    }

    @Override
    public MageInt getPower() {
        return MageInt.EmptyMageInt;
    }

    @Override
    public MageInt getToughness() {
        return MageInt.EmptyMageInt;
    }
    
    @Override
    public int getStartingLoyalty() {
        return 0;
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public void setCopy(boolean isCopy) {
    }

    @Override
    public boolean isCopy() {
        return false;
    }

    @Override
    public Emblem copy() {
        return new Emblem(this);
    }

    public void setExpansionSetCodeForImage(String expansionSetCodeForImage) {
        this.expansionSetCodeForImage = expansionSetCodeForImage;
    }

    public String getExpansionSetCodeForImage() {
        return expansionSetCodeForImage;
    }

    @Override
    public int getZoneChangeCounter(Game game) {
        return 1; // Emblems can't move zones until now so return always 1
    }

    @Override
    public void updateZoneChangeCounter(Game game) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

}
