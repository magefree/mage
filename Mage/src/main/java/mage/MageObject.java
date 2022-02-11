package mage;

import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.Card;
import mage.cards.FrameStyle;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SubTypeSet;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.Copyable;
import mage.util.SubTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MageObject extends MageItem, Serializable, Copyable<MageObject> {

    String getName();

    String getIdName();

    String getLogName();

    String getImageName();

    void setName(String name);

    default List<CardType> getCardType() {
        return getCardType(null);
    }

    /**
     * Return dynamic card types (game isn't null) or static card types (game is null)
     *
     * @param game can be null
     * @return
     */
    List<CardType> getCardType(Game game);

    /**
     * Return original object's subtypes
     *
     * @return
     */
    SubTypes getSubtype();

    /**
     * Return full subtypes list (from object, from effects)
     *
     * @param game
     * @return
     */
    SubTypes getSubtype(Game game);

    boolean hasSubtype(SubType subtype, Game game);

    Set<SuperType> getSuperType();

    /**
     * For cards: return basic abilities (without dynamic added)
     * For permanents: return all abilities (dynamic ability inserts into permanent)
     *
     * @return
     */
    Abilities<Ability> getAbilities();

    /**
     * Must return object specific abilities that will be added to the game
     * It's a one time action on card/command object putting to the game
     * <p>
     * If your object contains inner cards/parts then each card will be added separately,
     * see GameImpl.loadCards. So you must return only parent specific abilities here. All
     * other abilities will be added from the parts.
     *
     * @return
     */
    default Abilities<Ability> getInitAbilities() {
        return getAbilities();
    }

    boolean hasAbility(Ability ability, Game game);

    ObjectColor getColor();

    ObjectColor getColor(Game game);

    ObjectColor getFrameColor(Game game);

    FrameStyle getFrameStyle();

    ManaCosts<ManaCost> getManaCost();

    default List<String> getManaCostSymbols() {
        List<String> symbols = new ArrayList<>();
        for (ManaCost cost : getManaCost()) {
            symbols.add(cost.getText());
        }
        return symbols;
    }

    int getManaValue();

    MageInt getPower();

    MageInt getToughness();

    int getStartingLoyalty();

    void setStartingLoyalty(int startingLoyalty);

    /**
     * Dynamic cost modification for card (process only OWN abilities).
     * <p>
     * Usage example: if it need stack related info (like real targets) then must check two
     * states (game.inCheckPlayableState):
     * <p>
     * 1. In playable state it must check all possible use cases (e.g. allow to
     * reduce on any available target and modes)
     * <p>
     * 2. In real cast state it must check current use case (e.g. real selected
     * targets and modes)
     *
     * @param ability
     * @param game
     */
    void adjustCosts(Ability ability, Game game);

    void adjustTargets(Ability ability, Game game);

    // memory object copy (not mtg)
    @Override
    MageObject copy();

    // copied card info (mtg)
    void setCopy(boolean isCopy, MageObject copiedFrom);

    MageObject getCopyFrom();

    boolean isCopy();

    int getZoneChangeCounter(Game game);

    void updateZoneChangeCounter(Game game, ZoneChangeEvent event);

    void setZoneChangeCounter(int value, Game game);

    default boolean isHistoric(Game game) {
        return getCardType(game).contains(CardType.ARTIFACT)
                || getSuperType().contains(SuperType.LEGENDARY)
                || hasSubtype(SubType.SAGA, game);
    }

    default boolean isCreature() {
        return isCreature(null);
    }

    default boolean isCreature(Game game) {
        return getCardType(game).contains(CardType.CREATURE);
    }

    default boolean isArtifact() {
        return isArtifact(null);
    }

    default boolean isArtifact(Game game) {
        return getCardType(game).contains(CardType.ARTIFACT);
    }

    default boolean isLand() {
        return isLand(null);
    }

    default boolean isLand(Game game) {
        return getCardType(game).contains(CardType.LAND);
    }

    default boolean isEnchantment() {
        return isEnchantment(null);
    }

    default boolean isEnchantment(Game game) {
        return getCardType(game).contains(CardType.ENCHANTMENT);
    }

    default boolean isInstant() {
        return isInstant(null);
    }

    default boolean isInstant(Game game) {
        return getCardType(game).contains(CardType.INSTANT);
    }

    default boolean isSorcery() {
        return isSorcery(null);
    }

    default boolean isSorcery(Game game) {
        return getCardType(game).contains(CardType.SORCERY);
    }

    default boolean isInstantOrSorcery() {
        return this.isInstant() || this.isSorcery();
    }

    default boolean isInstantOrSorcery(Game game) {
        return this.isInstant(game) || this.isSorcery(game);
    }

    default boolean isPlaneswalker() {
        return isPlaneswalker(null);
    }

    default boolean isPlaneswalker(Game game) {
        return getCardType(game).contains(CardType.PLANESWALKER);
    }

    default boolean isTribal() {
        return isTribal(null);
    }

    default boolean isTribal(Game game) {
        return getCardType(game).contains(CardType.TRIBAL);
    }

    default boolean isPermanent() {
        return isCreature() || isArtifact() || isPlaneswalker() || isEnchantment() || isLand();
    }

    default boolean isPermanent(Game game) {
        return isCreature(game) || isArtifact(game) || isPlaneswalker(game) || isEnchantment(game) || isLand(game);
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

    /**
     * Add card type from static effects (permanently)
     *
     * @param cardTypes
     */
    default void addCardType(CardType... cardTypes) {
        addCardType(null, cardTypes);
    }

    /**
     * Add card type from dynamic effects (game isn't null) and from static effects (game is null)
     *
     * @param game
     * @param cardTypes
     */
    default void addCardType(Game game, CardType... cardTypes) {
        List<CardType> currentCardTypes;
        if (game != null) {
            // dynamic
            currentCardTypes = game.getState().getCreateMageObjectAttribute(this, game).getCardType();
        } else {
            // static
            currentCardTypes = getCardType();
        }
        for (CardType cardType : cardTypes) {
            if (!currentCardTypes.contains(cardType)) {
                currentCardTypes.add(cardType);
            }
        }
    }

    default void removeCardType(CardType... cardTypes) {
        removeCardType(null, cardTypes);
    }

    default void removeCardType(Game game, CardType... cardTypes) {
        List<CardType> currentCardTypes;
        if (game != null) {
            // dynamic
            currentCardTypes = game.getState().getCreateMageObjectAttribute(this, game).getCardType();
        } else {
            // static
            currentCardTypes = getCardType();
        }
        for (CardType cardType : cardTypes) {
            currentCardTypes.remove(cardType);
        }
    }

    default void removeAllCardTypes() {
        removeAllCardTypes(null);
    }

    default void removeAllCardTypes(Game game) {
        List<CardType> currentCardTypes;
        if (game != null) {
            // dynamic
            currentCardTypes = game.getState().getCreateMageObjectAttribute(this, game).getCardType();
        } else {
            // static
            currentCardTypes = getCardType();
        }
        currentCardTypes.clear();
    }

    /**
     * Add subtype temporary, for continuous effects only
     *
     * @param game
     * @param subTypes
     */
    default void addSubType(Game game, Collection<SubType> subTypes) {
        for (SubType subType : subTypes) {
            addSubType(game, subType);
        }
    }

    /**
     * Add subtype permanently, for one shot effects and tokens setup
     *
     * @param subTypes
     */
    default void addSubType(SubType... subTypes) {
        for (SubType subType : subTypes) {
            if (subType.canGain(this)
                    && !getSubtype().contains(subType)) {
                getSubtype().add(subType);
            }
        }
    }

    /**
     * Add subtype temporary, for continuous effects only
     *
     * @param game
     * @param subTypes
     */
    default void addSubType(Game game, SubType... subTypes) {
        for (SubType subType : subTypes) {
            if (subType.canGain(game, this)
                    && !hasSubtype(subType, game)) {
                game.getState().getCreateMageObjectAttribute(this, game).getSubtype().add(subType);
            }
        }
    }

    default void copySubTypesFrom(Game game, MageObject mageObject) {
        copySubTypesFrom(game, mageObject, null);
    }

    default void copySubTypesFrom(Game game, MageObject mageObject, SubTypeSet subTypeSet) {
        if (subTypeSet == SubTypeSet.CreatureType || subTypeSet == null) {
            this.setIsAllCreatureTypes(game, mageObject.isAllCreatureTypes(game));
        }
        for (SubType subType : mageObject.getSubtype(game)) {
            if (subType.getSubTypeSet() == subTypeSet || subTypeSet == null) {
                this.addSubType(game, subType);
            }
        }
    }

    default void removeAllSubTypes(Game game) {
        removeAllSubTypes(game, null);
    }

    default void removeAllSubTypes(Game game, SubTypeSet subTypeSet) {
        if (subTypeSet == null) {
            setIsAllCreatureTypes(game, false);
            game.getState().getCreateMageObjectAttribute(this, game).getSubtype().clear();
        } else if (subTypeSet == SubTypeSet.CreatureType) {
            removeAllCreatureTypes(game);
        } else if (subTypeSet == SubTypeSet.NonBasicLandType) {
            game.getState().getCreateMageObjectAttribute(this, game).getSubtype().removeAll(SubType.getLandTypes());
        } else {
            game.getState().getCreateMageObjectAttribute(this, game).getSubtype().removeAll(SubType.getBySubTypeSet(subTypeSet));
        }
    }

    default void retainAllArtifactSubTypes(Game game) {
        setIsAllCreatureTypes(game, false);
        game.getState().getCreateMageObjectAttribute(this, game).getSubtype().retainAll(SubType.getArtifactTypes());
    }

    default void retainAllEnchantmentSubTypes(Game game) {
        setIsAllCreatureTypes(game, false);
        game.getState().getCreateMageObjectAttribute(this, game).getSubtype().retainAll(SubType.getEnchantmentTypes());
    }

    default void retainAllLandSubTypes(Game game) {
        setIsAllCreatureTypes(game, false);
        game.getState().getCreateMageObjectAttribute(this, game).getSubtype().retainAll(SubType.getLandTypes());
    }

    /**
     * Remove object's own creature types forever (for copy effects usage)
     */
    default void removeAllCreatureTypes() {
        setIsAllCreatureTypes(false);
        getSubtype().removeAll(SubType.getCreatureTypes());
    }

    /**
     * Remove object attribute's creature types temporary (for continuous effects usage)
     *
     * @param game
     */
    default void removeAllCreatureTypes(Game game) {
        setIsAllCreatureTypes(game, false);
        game.getState().getCreateMageObjectAttribute(this, game).getSubtype().removeAll(SubType.getCreatureTypes());
    }

    default void removeSubType(Game game, SubType subType) {
        game.getState().getCreateMageObjectAttribute(this, game).getSubtype().remove(subType);
    }

    /**
     * Checks whether two cards share card types.
     *
     * @param otherCard
     * @param game
     * @return
     */
    default boolean shareTypes(Card otherCard, Game game) {
        return this.shareTypes(otherCard, game, false);
    }

    default boolean shareTypes(Card otherCard, Game game, boolean permanentOnly) {

        if (otherCard == null) {
            throw new IllegalArgumentException("Params can't be null");
        }

        for (CardType type : getCardType(game)) {
            if (otherCard.getCardType(game).contains(type)
                    && (!permanentOnly || type.isPermanentType())) {
                return true;
            }
        }

        return false;
    }

    default boolean shareCreatureTypes(Game game, MageObject otherCard) {
        if (!isCreature(game) && !isTribal(game)) {
            return false;
        }
        if (!otherCard.isCreature(game) && !otherCard.isTribal(game)) {
            return false;
        }
        boolean isAllA = this.isAllCreatureTypes(game);
        boolean isAnyA = isAllA || this.getSubtype(game)
                .stream()
                .map(SubType::getSubTypeSet)
                .anyMatch(SubTypeSet.CreatureType::equals);
        boolean isAllB = otherCard.isAllCreatureTypes(game);
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

    boolean isAllCreatureTypes(Game game);

    void setIsAllCreatureTypes(boolean value);

    /**
     * Change all creature type mark temporary, for continuous effects only
     *
     * @param game
     * @param value
     */
    void setIsAllCreatureTypes(Game game, boolean value);

    void removePTCDA();
}
