package mage.game.permanent;


import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.Card;
import mage.constants.*;
import mage.counters.Counter;
import mage.game.CardState;
import mage.game.Game;
import mage.game.MageObjectAttribute;
import mage.game.events.ZoneChangeEvent;
import mage.util.immutableWrappers.ImmutablePermanent;
import mage.watchers.Watcher;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Represents an immutable permanent with dynamic values from game state. All modification attempts throw {@link UnsupportedOperationException}.
 * Primarily used to save refences in Last Known Instance map. Use this if you need to save a snapshot of a permanent to reference later.
 *
 * @author Jmlundeen
 */
public class ImmutablePermanentCard extends PermanentCard implements ImmutablePermanent {

    protected ImmutablePermanentCard(PermanentCard permanent, MageObjectAttribute mageObjectAttribute, CardState cardState) {
        super(permanent);
        if (mageObjectAttribute != null) {
            this.color = mageObjectAttribute.getColor();
            this.subtype = mageObjectAttribute.getSubtype();
            this.cardType = mageObjectAttribute.getCardType();
            this.supertype = mageObjectAttribute.getSuperType();
        }
        if (cardState.hasLostAllAbilities()) {
            abilities.clear();
        } else {
            abilities.addAll(cardState.getAbilities());
        }
    }

    protected ImmutablePermanentCard(ImmutablePermanentCard permanent) {
        super(permanent);
    }

    @Override
    public PermanentCard getResetPermanent(Game game) {
        PermanentCard card = new PermanentCard(this);
        card.reset(game);
        return card;
    }

    @Override
    public void reset(Game game) {
        throwImmutableError();
    }

    @Override
    public ImmutablePermanentCard copy() {
        return new ImmutablePermanentCard(this);
    }

    @Override
    public boolean turnFaceUp(Ability source, Game game, UUID playerId) {
        return throwImmutableError();
    }

    @Override
    public boolean turnFaceDown(Ability source, Game game, UUID playerId) {
        return throwImmutableError();
    }

    @Override
    public boolean addCounters(Counter counter, Ability source, Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game, boolean isEffect) {
        return throwImmutableError();
    }

    @Override
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game, List<UUID> appliedEffects) {
        return throwImmutableError();
    }

    @Override
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game, List<UUID> appliedEffects, boolean isEffect) {
        return throwImmutableError();
    }

    @Override
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game, List<UUID> appliedEffects, boolean isEffect, int maxCounters) {
        return throwImmutableError();
    }

    @Override
    public void removeCounters(String counterName, int amount, Ability source, Game game) {
        throwImmutableError();
    }

    @Override
    public int removeCounters(String counterName, int amount, Ability source, Game game, boolean isDamage) {
        throwImmutableError();
        return 0;
    }

    @Override
    public int removeCounters(Counter counter, Ability source, Game game) {
        throwImmutableError();
		return 0;
    }

    @Override
    public int removeCounters(Counter counter, Ability source, Game game, boolean isDamage) {
        throwImmutableError();
		return 0;
    }

    @Override
    public int removeAllCounters(Ability source, Game game) {
        throwImmutableError();
		return 0;
    }

    @Override
    public int removeAllCounters(Ability source, Game game, boolean isDamage) {
        throwImmutableError();
		return 0;
    }

    @Override
    public int removeAllCounters(String counterName, Ability source, Game game) {
        throwImmutableError();
		return 0;
    }

    @Override
    public int removeAllCounters(String counterName, Ability source, Game game, boolean isDamage) {
        throwImmutableError();
		return 0;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        throwImmutableError();
    }

    @Override
    public void setSpellAbility(SpellAbility ability) {
        throwImmutableError();
    }

    @Override
    public boolean addAttachment(UUID permanentId, Ability source, Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean removeAttachment(UUID permanentId, Ability source, Game game) {
        return throwImmutableError();
    }

    @Override
    public void setName(String name) {
        throwImmutableError();
    }

    @Override
    public void setStartingLoyalty(int startingLoyalty) {
        throwImmutableError();
    }

    @Override
    public void setStartingDefense(int startingDefense) {
        throwImmutableError();
    }

    @Override
    public void setExpansionSetCode(String expansionSetCode) {
        throwImmutableError();
    }

    @Override
    public void setUsesVariousArt(boolean usesVariousArt) {
        throwImmutableError();
    }

    @Override
    public void setCardNumber(String cardNumber) {
        throwImmutableError();
    }

    @Override
    public void setImageFileName(String imageFileName) {
        throwImmutableError();
    }

    @Override
    public void setImageNumber(Integer imageNumber) {
        throwImmutableError();
    }

    @Override
    public void setManaCost(ManaCosts<ManaCost> costs) {
        throwImmutableError();
    }

    @Override
    public void setCopy(boolean isCopy, MageObject copyFrom) {
        throwImmutableError();
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        throwImmutableError();
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        throwImmutableError();
    }

    @Override
    public void addSuperType(SuperType superType) {
        throwImmutableError();
    }

    @Override
    public void addSuperType(Game game, SuperType superType) {
        throwImmutableError();
    }

    @Override
    public void removeAllSuperTypes() {
        throwImmutableError();
    }

    @Override
    public void removeAllSuperTypes(Game game) {
        throwImmutableError();
    }

    @Override
    public void removeSuperType(SuperType superType) {
        throwImmutableError();
    }

    @Override
    public void removeSuperType(Game game, SuperType superType) {
        throwImmutableError();
    }

    @Override
    public void addCardType(CardType... cardTypes) {
        throwImmutableError();
    }

    @Override
    public void addCardType(Game game, CardType... cardTypes) {
        throwImmutableError();
    }

    @Override
    public void removeCardType(CardType... cardTypes) {
        throwImmutableError();
    }

    @Override
    public void removeCardType(Game game, CardType... cardTypes) {
        throwImmutableError();
    }

    @Override
    public void removeAllCardTypes() {
        throwImmutableError();
    }

    @Override
    public void removeAllCardTypes(Game game) {
        throwImmutableError();
    }

    @Override
    public void addSubType(Game game, Collection<SubType> subTypes) {
        throwImmutableError();
    }

    @Override
    public void addSubType(SubType... subTypes) {
        throwImmutableError();
    }

    @Override
    public void addSubType(Game game, SubType... subTypes) {
        throwImmutableError();
    }

    @Override
    public void copySubTypesFrom(Game game, MageObject mageObject) {
        throwImmutableError();
    }

    @Override
    public void copySubTypesFrom(Game game, MageObject mageObject, SubTypeSet subTypeSet) {
        throwImmutableError();
    }

    @Override
    public void removeAllSubTypes(Game game) {
        throwImmutableError();
    }

    @Override
    public void removeAllSubTypes(Game game, SubTypeSet subTypeSet) {
        throwImmutableError();
    }

    @Override
    public void retainAllArtifactSubTypes(Game game) {
        throwImmutableError();
    }

    @Override
    public void retainAllEnchantmentSubTypes(Game game) {
        throwImmutableError();
    }

    @Override
    public void retainAllLandSubTypes(Game game) {
        throwImmutableError();
    }

    @Override
    public void removeAllCreatureTypes() {
        throwImmutableError();
    }

    @Override
    public void removeAllCreatureTypes(Game game) {
        throwImmutableError();
    }

    @Override
    public void removeSubType(Game game, SubType subType) {
        throwImmutableError();
    }

    @Override
    public void setIsAllCreatureTypes(boolean value) {
        throwImmutableError();
    }

    @Override
    public void setIsAllCreatureTypes(Game game, boolean value) {
        throwImmutableError();
    }

    @Override
    public void setIsAllNonbasicLandTypes(boolean value) {
        throwImmutableError();
    }

    @Override
    public void setIsAllNonbasicLandTypes(Game game, boolean value) {
        throwImmutableError();
    }

    @Override
    public void removePTCDA() {
        throwImmutableError();
    }

    @Override
    public void setControllerId(UUID controllerId) {
        throwImmutableError();
    }

    @Override
    public void setOriginalControllerId(UUID originalControllerId) {
        throwImmutableError();
    }

    @Override
    public void addInfo(String key, String value, Game game) {
        throwImmutableError();
    }

    @Override
    public void looseAllAbilities(Game game) {
        throwImmutableError();
    }

    @Override
    public void addAbility(Ability ability) {
        throwImmutableError();
    }

    @Override
    protected void addAbility(Ability ability, Watcher watcher) {
        throwImmutableError();
    }

    @Override
    public void replaceSpellAbility(SpellAbility newAbility) {
        throwImmutableError();
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        throwImmutableError();
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag) {
        return throwImmutableError();
    }

    @Override
    public Ability addAbility(Ability ability, UUID sourceId, Game game) {
        throwImmutableError();
		return null;
    }

    @Override
    public Ability addAbility(Ability ability, UUID sourceId, Game game, boolean fromExistingObject) {
        throwImmutableError();
		return null;
    }

    @Override
    public void removeAllAbilities(UUID sourceId, Game game) {
        throwImmutableError();
    }

    @Override
    public void removeAbility(Ability abilityToRemove, UUID sourceId, Game game) {
        throwImmutableError();
    }

    @Override
    public void removeAbilities(List<Ability> abilitiesToRemove, UUID sourceId, Game game) {
        throwImmutableError();
    }

    @Override
    public void beginningOfTurn(Game game) {
        throwImmutableError();
    }

    @Override
    public void endOfTurn(Game game) {
        throwImmutableError();
    }

    @Override
    public void incrementLoyaltyActivationsAvailable() {
        throwImmutableError();
    }

    @Override
    public void incrementLoyaltyActivationsAvailable(int max) {
        throwImmutableError();
    }

    @Override
    public void setLoyaltyActivationsAvailable(int setActivations) {
        throwImmutableError();
    }

    @Override
    public void addLoyaltyUsed() {
        throwImmutableError();
    }

    @Override
    public void setLegendRuleApplies(boolean legendRuleApplies) {
        throwImmutableError();
    }

    @Override
    public void setTapped(boolean tapped) {
        throwImmutableError();
    }

    @Override
    public boolean untap(Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean tap(Ability source, Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean tap(boolean forCombat, Ability source, Game game) {
        return throwImmutableError();
    }

    @Override
    public void setFaceDown(boolean value, Game game) {
        throwImmutableError();
    }

    @Override
    public boolean flip(Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean transform(Ability source, Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean transform(Ability source, Game game, boolean ignoreDayNight) {
        return throwImmutableError();
    }

    @Override
    public boolean phaseIn(Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean phaseIn(Game game, boolean onlyDirect) {
        return throwImmutableError();
    }

    @Override
    public boolean phaseOut(Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean phaseOut(Game game, boolean indirectPhase) {
        return throwImmutableError();
    }

    @Override
    public void removeSummoningSickness() {
        throwImmutableError();
    }

    @Override
    public void resetControl() {
        throwImmutableError();
    }

    @Override
    public boolean changeControllerId(UUID newControllerId, Game game, Ability source) {
        return throwImmutableError();
    }

    @Override
    public void removeUncontrolledRingBearer(Game game) {
        throwImmutableError();
    }

    @Override
    public boolean checkControlChanged(Game game) {
        return throwImmutableError();
    }

    @Override
    public void addConnectedCard(String key, UUID connectedCard) {
        throwImmutableError();
    }

    @Override
    public void clearConnectedCards(String key) {
        throwImmutableError();
    }

    @Override
    public void unattach(Game game) {
        throwImmutableError();
    }

    @Override
    public void attachTo(UUID attachToObjectId, Ability source, Game game) {
        throwImmutableError();
    }

    @Override
    public int damage(int damage, Ability source, Game game) {
        throwImmutableError();
		return 0;
    }

    @Override
    public int damage(int damage, UUID attackerId, Ability source, Game game) {
        throwImmutableError();
		return 0;
    }

    @Override
    public int damage(int damage, UUID attackerId, Ability source, Game game, boolean combat, boolean preventable) {
        throwImmutableError();
		return 0;
    }

    @Override
    public int damage(int damage, UUID attackerId, Ability source, Game game, boolean combat, boolean preventable, List<UUID> appliedEffects) {
        throwImmutableError();
		return 0;
    }

    @Override
    public void markLifelink(int damage) {
        throwImmutableError();
    }

    @Override
    public int markDamage(int damageAmount, UUID attackerId, Ability source, Game game, boolean preventable, boolean combat) {
        throwImmutableError();
		return 0;
    }

    @Override
    public int applyDamage(Game game) {
        throwImmutableError();
		return 0;
    }

    @Override
    public int damageWithExcess(int damage, Ability source, Game game) {
        throwImmutableError();
		return 0;
    }

    @Override
    public int damageWithExcess(int damage, UUID attackerId, Ability source, Game game) {
        throwImmutableError();
		return 0;
    }

    @Override
    public void removeAllDamage(Game game) {
        throwImmutableError();
    }

    @Override
    public boolean entersBattlefield(Ability source, Game game, Zone fromZone, boolean fireEvent) {
        return throwImmutableError();
    }

    @Override
    public boolean canBeTargetedBy(MageObject sourceObject, UUID sourceControllerId, Ability source, Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean destroy(Ability source, Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean destroy(Ability source, Game game, boolean noRegen) {
        return throwImmutableError();
    }

    @Override
    public boolean sacrifice(Ability source, Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean regenerate(Ability source, Game game) {
        return throwImmutableError();
    }

    @Override
    public void addPower(int power) {
        throwImmutableError();
    }

    @Override
    public void addToughness(int toughness) {
        throwImmutableError();
    }

    @Override
    public void setAttacking(boolean attacking) {
        throwImmutableError();
    }

    @Override
    public void setBlocking(int blocking) {
        throwImmutableError();
    }

    @Override
    public void setMaxBlocks(int maxBlocks) {
        throwImmutableError();
    }

    @Override
    public void setMinBlockedBy(int minBlockedBy) {
        throwImmutableError();
    }

    @Override
    public void setMaxBlockedBy(int maxBlockedBy) {
        throwImmutableError();
    }

    @Override
    public boolean removeFromCombat(Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean removeFromCombat(Game game, boolean withEvent) {
        return throwImmutableError();
    }

    @Override
    public boolean imprint(UUID imprintedCard, Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean clearImprinted(Game game) {
        return throwImmutableError();
    }

    @Override
    public void setTransformed(boolean value) {
        throwImmutableError();
    }

    @Override
    public void setMonstrous(boolean value) {
        throwImmutableError();
    }

    @Override
    public void setRenowned(boolean value) {
        throwImmutableError();
    }

    @Override
    public void setSuspected(boolean value, Game game, Ability source) {
        throwImmutableError();
    }

    @Override
    public void setRingBearer(Game game, boolean value) {
        throwImmutableError();
    }

    @Override
    public boolean setClassLevel(int classLevel) {
        return throwImmutableError();
    }

    @Override
    public void addGoadingPlayer(UUID playerId) {
        throwImmutableError();
    }

    @Override
    public void chooseProtector(Game game, Ability source) {
        throwImmutableError();
    }

    @Override
    public void setProtectorId(UUID protectorId) {
        throwImmutableError();
    }

    @Override
    public void setCanBeSacrificed(boolean canBeSacrificed) {
        throwImmutableError();
    }

    @Override
    public void setPairedCard(MageObjectReference pairedCard) {
        throwImmutableError();
    }

    @Override
    public void clearPairedCard() {
        throwImmutableError();
    }

    @Override
    public void addBandedCard(UUID bandedCard) {
        throwImmutableError();
    }

    @Override
    public void removeBandedCard(UUID bandedCard) {
        throwImmutableError();
    }

    @Override
    public void clearBandedCards() {
        throwImmutableError();
    }

    @Override
    public void setManifested(boolean value) {
        throwImmutableError();
    }

    @Override
    public void setCloaked(boolean value) {
        throwImmutableError();
    }

    @Override
    public void setMorphed(boolean value) {
        throwImmutableError();
    }

    @Override
    public void setDisguised(boolean value) {
        throwImmutableError();
    }

    @Override
    public void assignNewId() {
        throwImmutableError();
    }

    @Override
    public void setRarity(Rarity rarity) {
        throwImmutableError();
    }

    @Override
    public void setFlipCard(boolean flipCard) {
        throwImmutableError();
    }

    @Override
    public void setFlipCardName(String flipCardName) {
        throwImmutableError();
    }

    @Override
    public void setSecondCardFace(Card card) {
        throwImmutableError();
    }

    @Override
    public void setPrototyped(boolean prototyped) {
        throwImmutableError();
    }

    @Override
    public boolean solve(Game game, Ability source) {
        return throwImmutableError();
    }

    @Override
    public boolean fight(Permanent fightTarget, Ability source, Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean fight(Permanent fightTarget, Ability source, Game game, boolean batchTrigger) {
        return throwImmutableError();
    }

    @Override
    public void setCreateOrder(int createOrder) {
        throwImmutableError();
    }

    @Override
    public void switchPowerToughness() {
        throwImmutableError();
    }

    @Override
    public void detachAllAttachments(Game game) {
        throwImmutableError();
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        return throwImmutableError();
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        return throwImmutableError();
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game) {
        return throwImmutableError();
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        return throwImmutableError();
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, Ability source, UUID controllerId) {
        return throwImmutableError();
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, Ability source, UUID controllerId, boolean tapped) {
        return throwImmutableError();
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, Ability source, UUID controllerId, boolean tapped, boolean faceDown) {
        return throwImmutableError();
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, Ability source, UUID controllerId, boolean tapped, boolean faceDown, List<UUID> appliedEffects) {
        return throwImmutableError();
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, Ability source) {
        return throwImmutableError();
    }

    @Override
    public void applyEnterWithCounters(Permanent permanent, Ability source, Game game) {
        throwImmutableError();
    }
}
