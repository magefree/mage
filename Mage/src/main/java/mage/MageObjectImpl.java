package mage;

import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.text.TextPart;
import mage.abilities.text.TextPartSubType;
import mage.cards.FrameStyle;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.GameLog;
import mage.util.SubTypeList;

import java.util.*;

public abstract class MageObjectImpl implements MageObject {

    protected UUID objectId;

    protected String name;
    protected ManaCosts<ManaCost> manaCost;
    protected ObjectColor color;
    protected ObjectColor frameColor;
    protected FrameStyle frameStyle;
    protected Set<CardType> cardType = EnumSet.noneOf(CardType.class);
    protected SubTypeList subtype = new SubTypeList();
    protected boolean isAllCreatureTypes;
    protected Set<SuperType> supertype = EnumSet.noneOf(SuperType.class);
    protected Abilities<Ability> abilities;
    protected String text;
    protected MageInt power;
    protected MageInt toughness;
    protected boolean copy;
    protected MageObject copyFrom; // copied card INFO (used to call original adjusters)
    protected List<TextPart> textParts;

    public MageObjectImpl() {
        this(UUID.randomUUID());
    }

    public MageObjectImpl(UUID id) {
        objectId = id;
        power = new MageInt(0);
        toughness = new MageInt(0);
        color = new ObjectColor();
        frameColor = new ObjectColor();
        frameStyle = FrameStyle.M15_NORMAL;
        manaCost = new ManaCostsImpl<>("");
        abilities = new AbilitiesImpl<>();
        textParts = new ArrayList<>();
    }

    public MageObjectImpl(final MageObjectImpl object) {
        objectId = object.objectId;
        name = object.name;
        manaCost = object.manaCost.copy();
        text = object.text;
        color = object.color.copy();
        frameColor = object.frameColor.copy();
        frameStyle = object.frameStyle;
        power = object.power.copy();
        toughness = object.toughness.copy();
        abilities = object.abilities.copy();
        this.cardType.addAll(object.cardType);
        this.subtype.addAll(object.subtype);
        isAllCreatureTypes = object.isAllCreatureTypes;
        supertype.addAll(object.supertype);
        this.copy = object.copy;
        this.copyFrom = (object.copyFrom != null ? object.copyFrom.copy() : null);
        textParts = new ArrayList<>();
        textParts.addAll(object.textParts);
    }

    @Override
    public UUID getId() {
        return objectId;
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
    public String getLogName() {
        return GameLog.getColoredObjectIdName(this);
    }

    @Override
    public String getImageName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<CardType> getCardType() {
        return cardType;
    }

    @Override
    public SubTypeList getSubtype(Game game) {
        return subtype;
    }

    @Override
    public Set<SuperType> getSuperType() {
        return supertype;
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return abilities;
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
    public MageInt getPower() {
        return power;
    }

    @Override
    public MageInt getToughness() {
        return toughness;
    }

    @Override
    public int getStartingLoyalty() {
        for (Ability ab : getAbilities()) {
            if (ab instanceof PlaneswalkerEntersWithLoyaltyCountersAbility) {
                return ((PlaneswalkerEntersWithLoyaltyCountersAbility) ab).getStartingLoyalty();
            }
        }
        return 0;
    }

    @Override
    public ObjectColor getColor(Game game) {
        return color;
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        // For lands, add any colors of mana the land can produce to
        // its frame colors.
        if (this.isLand()) {
            ObjectColor cl = frameColor.copy();
            for (Ability ab : getAbilities()) {
                if (ab instanceof ActivatedManaAbilityImpl) {
                    ActivatedManaAbilityImpl mana = (ActivatedManaAbilityImpl) ab;
                    try {
                        List<Mana> manaAdded = mana.getNetMana(game);
                        for (Mana m : manaAdded) {
                            if (m.getAny() > 0) {
                                return new ObjectColor("WUBRG");
                            }
                            if (m.getWhite() > 0) {
                                cl.setWhite(true);
                            }
                            if (m.getBlue() > 0) {
                                cl.setBlue(true);
                            }
                            if (m.getBlack() > 0) {
                                cl.setBlack(true);
                            }
                            if (m.getRed() > 0) {
                                cl.setRed(true);
                            }
                            if (m.getGreen() > 0) {
                                cl.setGreen(true);
                            }
                        }
                    } catch (NullPointerException e) {
                        // Ability depends on game
                        // but no game passed
                        // All such abilities are 5-color ones
                        return new ObjectColor("WUBRG");
                    }
                }
            }
            return cl;
        } else {
            // For everything else, just return the frame colors
            return frameColor;
        }
    }

    @Override
    public FrameStyle getFrameStyle() {
        return frameStyle;
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return manaCost;
    }

    @Override
    public int getConvertedManaCost() {
        if (manaCost != null) {
            return manaCost.convertedManaCost();
        }
        return 0;
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
    }

    @Override
    public boolean hasSubtype(SubType value, Game game) {
        if (value == null) {
            return false;
        }
        SubTypeList subtypes = this.getSubtype(game);
        if (subtypes.contains(value)) {
            return true;
        } else {
            // checking for Changeling
            // first make sure input parameter is a creature subtype
            // if not, then ChangelingAbility doesn't matter
            if (value.getSubTypeSet() != SubTypeSet.CreatureType) {
                return false;
            }
            // as it is creature subtype, then check the existence of Changeling
            return abilities.contains(ChangelingAbility.getInstance()) || isAllCreatureTypes();
        }
    }

    @Override
    public void setCopy(boolean isCopy, MageObject copyFrom) {
        this.copy = isCopy;
        this.copyFrom = (copyFrom != null ? copyFrom.copy() : null);
    }

    @Override
    public MageObject getCopyFrom() {
        return this.copyFrom;
    }

    @Override
    public boolean isCopy() {
        return copy;
    }

    @Override
    public int getZoneChangeCounter(Game game) {
        return game.getState().getZoneChangeCounter(objectId);
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        game.getState().updateZoneChangeCounter(objectId);
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        game.getState().setZoneChangeCounter(objectId, value);
    }

    @Override
    public boolean isAllCreatureTypes() {
        return isAllCreatureTypes;
    }

    @Override
    public void setIsAllCreatureTypes(boolean value) {
        isAllCreatureTypes = value;
    }

    @Override
    public List<TextPart> getTextParts() {
        return textParts;
    }

    @Override
    public TextPart addTextPart(TextPart textPart) {
        textParts.add(textPart);
        return textPart;
    }

    @Override
    public void changeSubType(SubType fromSubType, SubType toSubType) {
        for (TextPart textPart : textParts) {
            if (textPart instanceof TextPartSubType && textPart.getCurrentValue().equals(fromSubType)) {
                textPart.replaceWith(toSubType);
            }
        }
    }

    /**
     * Remove power/toughness character defining abilities
     */
    @Override
    public void removePTCDA() {
        for (Iterator<Ability> iter = this.getAbilities().iterator(); iter.hasNext(); ) {
            Ability ability = iter.next();
            for (Effect effect : ability.getEffects()) {
                if (effect instanceof ContinuousEffect && ((ContinuousEffect) effect).getSublayer() == SubLayer.CharacteristicDefining_7a) {
                    iter.remove();
                    break;
                }
            }
        }
    }
}
