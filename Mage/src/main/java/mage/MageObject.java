package mage;

import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.text.TextPart;
import mage.cards.Card;
import mage.cards.FrameStyle;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SubTypeSet;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.SubTypeList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface MageObject extends MageItem, Serializable {

    String getName();

    String getIdName();

    String getLogName();

    String getImageName();

    void setName(String name);

    ArrayList<CardType> getCardType();

    SubTypeList getSubtype(Game game);

    boolean hasSubtype(SubType subtype, Game game);

    Set<SuperType> getSuperType();

    /**
     * For cards: return basic abilities (without dynamic added) For permanents:
     * return all abilities (dynamic ability inserts into permanent)
     *
     * @return
     */
    Abilities<Ability> getAbilities();

    boolean hasAbility(Ability ability, Game game);

    ObjectColor getColor(Game game);

    ObjectColor getFrameColor(Game game);

    FrameStyle getFrameStyle();

    ManaCosts<ManaCost> getManaCost();

    int getConvertedManaCost();

    MageInt getPower();

    MageInt getToughness();

    int getStartingLoyalty();

    void setStartingLoyalty(int startingLoyalty);

    void adjustCosts(Ability ability, Game game);

    void adjustTargets(Ability ability, Game game);

    // memory object copy (not mtg)
    MageObject copy();

    // copied card info (mtg)
    void setCopy(boolean isCopy, MageObject copiedFrom);

    MageObject getCopyFrom();

    boolean isCopy();

    int getZoneChangeCounter(Game game);

    void updateZoneChangeCounter(Game game, ZoneChangeEvent event);

    void setZoneChangeCounter(int value, Game game);

    default boolean isHistoric() {
        return getCardType().contains(CardType.ARTIFACT)
                || getSuperType().contains(SuperType.LEGENDARY)
                || hasSubtype(SubType.SAGA, null);
    }

    default boolean isCreature() {
        return getCardType().contains(CardType.CREATURE);
    }

    default boolean isArtifact() {
        return getCardType().contains(CardType.ARTIFACT);
    }

    default boolean isLand() {
        return getCardType().contains(CardType.LAND);
    }

    default boolean isEnchantment() {
        return getCardType().contains(CardType.ENCHANTMENT);
    }

    default boolean isInstant() {
        return getCardType().contains(CardType.INSTANT);
    }

    default boolean isSorcery() {
        return getCardType().contains(CardType.SORCERY);
    }

    default boolean isInstantOrSorcery() {
        return this.isInstant() || this.isSorcery();
    }

    default boolean isPlaneswalker() {
        return getCardType().contains(CardType.PLANESWALKER);
    }

    default boolean isTribal() {
        return getCardType().contains(CardType.TRIBAL);
    }

    default boolean isPermanent() {
        return isCreature() || isArtifact() || isPlaneswalker() || isEnchantment() || isLand();
    }

    default boolean isLegendary() {
        return getSuperType().contains(SuperType.LEGENDARY);
    }

    default boolean isSnow() {
        return getSuperType().contains(SuperType.SNOW);
    }

    default void addSuperType(SuperType superType) {
        if (getSuperType().contains(superType)) {
            return;
        }
        getSuperType().add(superType);
    }

    default boolean isBasic() {
        return getSuperType().contains(SuperType.BASIC);
    }

    default boolean isWorld() {
        return getSuperType().contains(SuperType.WORLD);
    }

    default void addCardType(CardType cardType) {
        if (getCardType().contains(cardType)) {
            return;
        }
        getCardType().add(cardType);
    }

    default void addSubType(Game game, SubType... subTypes) {
        for (SubType subType : subTypes) {
            if (subType.canGain(this)
                    && !hasSubtype(subType, game)) {
                getSubtype(game).add(subType);
            }
        }
    }

    default void removeAllSubTypes(Game game) {
        getSubtype(game).clear();
        setIsAllCreatureTypes(false);
    }

    default void removeAllCreatureTypes(Game game) {
        getSubtype(game).removeAll(SubType.getCreatureTypes());
        setIsAllCreatureTypes(false);
    }

    /**
     * Checks whether two cards share card types.
     *
     * @param otherCard
     * @return
     */
    default boolean shareTypes(Card otherCard) {
        return this.shareTypes(otherCard, false);
    }

    default boolean shareTypes(Card otherCard, boolean permanentOnly) {

        if (otherCard == null) {
            throw new IllegalArgumentException("Params can't be null");
        }

        for (CardType type : getCardType()) {
            if (otherCard.getCardType().contains(type)
                    && (!permanentOnly || type.isPermanentType())) {
                return true;
            }
        }

        return false;
    }

    default boolean shareCreatureTypes(Card otherCard, Game game) {
        if (!isCreature() && !isTribal()) {
            return false;
        }
        if (!otherCard.isCreature() && !otherCard.isTribal()) {
            return false;
        }
        boolean isAllA = this.isAllCreatureTypes();
        boolean isAnyA = isAllA || this.getSubtype(game)
                .stream()
                .map(SubType::getSubTypeSet)
                .anyMatch(SubTypeSet.CreatureType::equals);
        boolean isAllB = otherCard.isAllCreatureTypes();
        boolean isAnyB = isAllB || otherCard
                .getSubtype(game)
                .stream()
                .map(SubType::getSubTypeSet)
                .anyMatch(SubTypeSet.CreatureType::equals);
        if (!isAnyA || !isAnyB) {
            return false;
        }
        if (isAllA) {
            return isAllB || isAnyB;
        }
        return isAnyA
                && (isAllB || this
                .getSubtype(game)
                .stream()
                .filter(subType -> subType.getSubTypeSet() == SubTypeSet.CreatureType)
                .anyMatch(subType -> otherCard.hasSubtype(subType, game)));
    }

    boolean isAllCreatureTypes();

    void setIsAllCreatureTypes(boolean value);

    default void addCardTypes(ArrayList<CardType> cardType) {
        getCardType().addAll(cardType);
    }

    List<TextPart> getTextParts();

    TextPart addTextPart(TextPart textPart);

    void removePTCDA();

    default void changeSubType(SubType fromSubType, SubType toSubType) {

    }
}
