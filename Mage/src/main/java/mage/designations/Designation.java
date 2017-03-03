/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.designations;

import java.util.*;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.FrameStyle;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.GameLog;

/**
 *
 * @author LevelX2
 */
public abstract class Designation implements MageObject {

    private static EnumSet emptySet = EnumSet.noneOf(CardType.class);
    private static List emptyList = new ArrayList();
    private static ObjectColor emptyColor = new ObjectColor();
    private static ManaCosts<ManaCost> emptyCost = new ManaCostsImpl();

    private String name;
    private UUID id;
    private FrameStyle frameStyle;
    private Abilities<Ability> abilites = new AbilitiesImpl<>();
    private String expansionSetCodeForImage;

    public Designation(String name, String expansionSetCode) {
        this.name = name;
        this.id = UUID.randomUUID();
        this.frameStyle = FrameStyle.M15_NORMAL;
        this.expansionSetCodeForImage = expansionSetCode;
    }

    public Designation(final Designation designation) {
        this.id = designation.id;
        this.name = designation.name;
        this.frameStyle = designation.frameStyle;
        this.abilites = designation.abilites.copy();
    }

    public abstract void start(Game game, UUID controllerId);

    @Override
    public FrameStyle getFrameStyle() {
        return frameStyle;
    }

    public void assignNewId() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIdName() {
        return getName() + " [" + getId().toString().substring(0, 3) + ']';
    }

    @Override
    public String getImageName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void addAbility(Ability ability) {
        ability.setSourceId(id);
        abilites.add(ability);
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return abilites;
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        return emptyColor;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public void setExpansionSetCodeForImage(String expansionSetCodeForImage) {
        this.expansionSetCodeForImage = expansionSetCodeForImage;
    }

    public String getExpansionSetCodeForImage() {
        return expansionSetCodeForImage;
    }

    @Override
    public String getLogName() {
        return GameLog.getColoredObjectIdName(this);
    }

    @Override
    public EnumSet<CardType> getCardType() {
        return emptySet;
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
    public boolean hasAbility(UUID abilityId, Game game) {
        return abilites.containsKey(abilityId);
    }

    @Override
    public ObjectColor getColor(Game game) {
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
    public void setCopy(boolean isCopy) {
    }

    @Override
    public boolean isCopy() {
        return false;
    }

    @Override
    public Designation copy() {
        return this;
    }

    @Override
    public int getZoneChangeCounter(Game game) {
        return 1; // Emblems can't move zones until now so return always 1
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

}
