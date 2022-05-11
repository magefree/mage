package mage.game.permanent;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.GameState;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface Permanent extends Card, Controllable {

    void setOriginalControllerId(UUID controllerId);

    void setControllerId(UUID controllerId);

    boolean isTapped();

    boolean untap(Game game);

    boolean tap(Ability source, Game game);

    boolean tap(boolean forCombat, Ability source, Game game);

    /**
     * use tap(game)
     * <p>
     * setTapped doesn't trigger TAPPED event and should be used only if you
     * want permanent to enter battlefield tapped</p>
     *
     * @param tapped
     * @deprecated
     */
    void setTapped(boolean tapped);

    boolean canTap(Game game);

    boolean isFlipped();

    boolean unflip(Game game);

    boolean flip(Game game);

    boolean transform(Ability source, Game game);

    boolean transform(Ability source, Game game, boolean ignoreDayNight);

    boolean isTransformed();

    void setTransformed(boolean value);

    int getTransformCount();

    boolean isPhasedIn();

    boolean isPhasedOutIndirectly();

    boolean phaseIn(Game game);

    boolean phaseIn(Game game, boolean onlyDirect);

    boolean phaseOut(Game game);

    boolean phaseOut(Game game, boolean indirectPhase);

    boolean isMonstrous();

    void setMonstrous(boolean value);

    boolean isRenowned();

    void setRenowned(boolean value);

    int getClassLevel();

    /**
     * Level up to next level.
     *
     * @param classLevel
     * @return false on wrong settings (e.g. level up to multiple levels)
     */
    boolean setClassLevel(int classLevel);

    void addGoadingPlayer(UUID playerId);

    Set<UUID> getGoadingPlayers();

    void setCardNumber(String cid);

    void setExpansionSetCode(String expansionSetCode);

    void setRarity(Rarity rarity);

    void setFlipCard(boolean flipCard);

    void setFlipCardName(String flipCardName);

    void setSecondCardFace(Card card);

    List<UUID> getAttachments();

    UUID getAttachedTo();

    int getAttachedToZoneChangeCounter();

    void attachTo(UUID permanentId, Ability source, Game game);

    void unattach(Game game);

    boolean canBeTargetedBy(MageObject source, UUID controllerId, Game game);

    boolean hasProtectionFrom(MageObject source, Game game);

    /**
     * @param attachment
     * @param source     can be null for default checks like state base
     * @param game
     * @param silentMode - use it to ignore warning message for users (e.g. for
     *                   checking only)
     * @return
     */
    boolean cantBeAttachedBy(MageObject attachment, Ability source, Game game, boolean silentMode);

    boolean wasControlledFromStartOfControllerTurn();

    boolean hasSummoningSickness();

    int getDamage();

    int damage(int damage, Ability source, Game game);

    int damage(int damage, UUID attackerId, Ability source, Game game);

    int damage(int damage, UUID attackerId, Ability source, Game game, boolean combat, boolean preventable);

    /**
     * Uses in replace events only
     *
     * @param damage
     * @param attackerId     id of the permanent or player who make damage (source.getSourceId() in most cases)
     * @param source         can be null for default game actions like combat
     * @param game
     * @param combat
     * @param preventable
     * @param appliedEffects
     * @return
     */
    int damage(int damage, UUID attackerId, Ability source, Game game, boolean combat, boolean preventable, List<UUID> appliedEffects);

    /**
     * Uses in combat only to deal damage at the same time
     *
     * @param damage
     * @param attackerId  id of the permanent or player who make damage (source.getSourceId() in most cases)
     * @param source      can be null for default game actions like combat
     * @param game
     * @param preventable
     * @param combat
     * @return
     */
    int markDamage(int damage, UUID attackerId, Ability source, Game game, boolean preventable, boolean combat);

    void markLifelink(int damage);

    int applyDamage(Game game);

    int getLethalDamage(UUID attackerId, Game game);

    void removeAllDamage(Game game);

    void reset(Game game);

    MageObject getBasicMageObject(Game game);

    boolean destroy(Ability source, Game game);

    boolean destroy(Ability source, Game game, boolean noRegen);

    /**
     * @param source can be null for state base actions
     * @param game
     * @return
     */
    boolean sacrifice(Ability source, Game game);

    boolean regenerate(Ability source, Game game);

    boolean fight(Permanent fightTarget, Ability source, Game game);

    boolean fight(Permanent fightTarget, Ability source, Game game, boolean batchTrigger);

    boolean entersBattlefield(Ability source, Game game, Zone fromZone, boolean fireEvent);

    String getValue(GameState state);

    /**
     * Add abilities to the permanent, can be used in effects
     *
     * @param ability
     * @param sourceId
     * @param game
     */
    void addAbility(Ability ability, UUID sourceId, Game game);

    void removeAllAbilities(UUID sourceId, Game game);

    void removeAbility(Ability abilityToRemove, UUID sourceId, Game game);

    void removeAbilities(List<Ability> abilitiesToRemove, UUID sourceId, Game game);

    void addLoyaltyUsed();

    boolean canLoyaltyBeUsed(Game game);

    void resetControl();

    boolean changeControllerId(UUID controllerId, Game game, Ability source);

    boolean checkControlChanged(Game game);

    void beginningOfTurn(Game game);

    void endOfTurn(Game game);

    int getTurnsOnBattlefield();

    void addPower(int power);

    void addToughness(int toughness);

    boolean isAttacking();

    boolean isBlocked(Game game);

    int getBlocking();

    void setAttacking(boolean attacking);

    void setBlocking(int blocking);

    int getMaxBlocks();

    void setMaxBlocks(int maxBlocks);

    int getMinBlockedBy();

    void setMinBlockedBy(int minBlockedBy);

    int getMaxBlockedBy();

    boolean isRemovedFromCombat();

    void setRemovedFromCombat(boolean removedFromCombat);

    /**
     * Sets the maximum number of blockers the creature can be blocked by.
     * Default = 0 which means there is no restriction in the number of
     * blockers.
     *
     * @param maxBlockedBy maximum number of blockers
     */
    void setMaxBlockedBy(int maxBlockedBy);

    /**
     * @param defenderId id of planeswalker or player to attack - can be empty
     *                   to check generally
     * @param game
     * @return
     */
    boolean canAttack(UUID defenderId, Game game);

    /**
     * Checks if a creature can attack (also if it is tapped)
     *
     * @param defenderId
     * @param game
     * @return
     */
    boolean canAttackInPrinciple(UUID defenderId, Game game);

    boolean canBlock(UUID attackerId, Game game);

    boolean canBlockAny(Game game);

    /**
     * Checks by restriction effects if the permanent can use activated
     * abilities
     *
     * @param game
     * @return true - permanent can use activated abilities
     */
    boolean canUseActivatedAbilities(Game game);

    boolean removeFromCombat(Game game);

    boolean removeFromCombat(Game game, boolean withInfo);

    boolean isDeathtouched();

    /**
     * Returns a list of object refrences that dealt damage this turn to this
     * permanent
     *
     * @return
     */
    Set<MageObjectReference> getDealtDamageByThisTurn();

    /**
     * Imprint some other card to this one.
     *
     * @param imprintedCard Card to count as imprinted
     * @param game
     * @return true if card was imprinted
     */
    boolean imprint(UUID imprintedCard, Game game);

    /**
     * Removes all imprinted cards from permanent.
     *
     * @param game
     * @return
     */
    boolean clearImprinted(Game game);

    /**
     * Get card that was imprinted on this one.
     * <p>
     * Can be null if no card was imprinted.
     *
     * @return Imprinted card UUID.
     */
    List<UUID> getImprinted();

    /**
     * Allows to connect any card to permanent. Very similar to Imprint except
     * that it is for internal use only.
     *
     * @param key
     * @param connectedCard
     */
    void addConnectedCard(String key, UUID connectedCard);

    /**
     * Returns connected cards. Very similar to Imprint except that it is for
     * internal use only.
     *
     * @param key
     * @return
     */
    List<UUID> getConnectedCards(String key);

    /**
     * Clear all connected cards.
     *
     * @param key
     */
    void clearConnectedCards(String key);

    /**
     * Sets paired card.
     *
     * @param pairedCard
     */
    void setPairedCard(MageObjectReference pairedCard);

    /**
     * Gets paired card. Can return null.
     *
     * @return
     */
    MageObjectReference getPairedCard();

    /**
     * Makes permanent paired with no other permanent.
     */
    void clearPairedCard();

    void addBandedCard(UUID bandedCard);

    void removeBandedCard(UUID bandedCard);

    List<UUID> getBandedCards();

    void clearBandedCards();

    void setMorphed(boolean value);

    boolean isMorphed();

    void setManifested(boolean value);

    boolean isManifested();

    @Override
    Permanent copy();

    // Simple int counter to set a timewise create order , the lower the number the earlier the object was created
    // if objects enter the battlefield at the same time they can get (and should) get the same number.
    int getCreateOrder();

    void setCreateOrder(int createOrder);

    default boolean isAttachedTo(UUID otherId) {
        if (getAttachedTo() == null) {
            return false;
        }
        return getAttachedTo().equals(otherId);
    }
}
