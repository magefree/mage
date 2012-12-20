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
import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Controllable;
import mage.game.Game;

import java.util.List;
import java.util.UUID;

public interface Permanent extends Card, Controllable {

    boolean isTapped();
    boolean untap(Game game);
    boolean tap(Game game);
    /**
     * use tap(game)
     * <p>setTapped doesn't trigger TAPPED event and should be used
     * only if you want permanent to enter battlefield tapped</p>
     *
     * @param tapped
     * @deprecated
     */
    @Deprecated
    void setTapped(boolean tapped);
    boolean canTap();

    boolean isFlipped();
    boolean unflip(Game game);
    boolean flip(Game game);

    boolean transform(Game game);
    boolean isTransformed();
    void setTransformed(boolean value);

    boolean isPhasedIn();
    boolean phaseIn(Game game);
    boolean phaseOut(Game game);

    boolean isFaceUp();
    boolean turnFaceUp(Game game);
    boolean turnFaceDown(Game game);

    List<UUID> getAttachments();
    UUID getAttachedTo();
    void attachTo(UUID permanentId, Game game);
    boolean addAttachment(UUID permanentId, Game game);
    boolean removeAttachment(UUID permanentId, Game game);

    boolean changeControllerId(UUID controllerId, Game game);
    boolean canBeTargetedBy(MageObject source, UUID controllerId, Game game);
    boolean hasProtectionFrom(MageObject source, Game game);
    boolean hasSummoningSickness();
    int getDamage();
    int damage(int damage, UUID sourceId, Game game, boolean preventable, boolean combat);

    int damage(int damage, UUID sourceId, Game game, boolean preventable, boolean combat, ArrayList<UUID> appliedEffects);

    /**
     * used in combat only to deal damage at the same time
     *
     * @param damage
     * @param sourceId
     * @param game
     * @param preventable
     * @param combat
     * @return
     */
    int markDamage(int damage, UUID sourceId, Game game, boolean preventable, boolean combat);
    int applyDamage(Game game);

    void removeAllDamage(Game game);
    Counters getCounters();

    void addCounters(String name, int amount, Game game);
    void addCounters(String name, int amount, Game game, ArrayList<UUID> appliedEffects);
    void addCounters(Counter counter, Game game);
    void addCounters(Counter counter, Game game, ArrayList<UUID> appliedEffects);

    void removeCounters(String name, int amount, Game game);
    void removeCounters(Counter counter, Game game);
    void reset(Game game);
    boolean destroy(UUID sourceId, Game game, boolean noRegen);
    boolean sacrifice(UUID sourceId, Game game);
    boolean regenerate(UUID sourceId, Game game);
    void entersBattlefield(UUID sourceId, Game game);
    String getValue();

    @Deprecated
    @Override
    void addAbility(Ability ability);
    @Deprecated
    void addAbility(Ability ability, Game game);
    void addAbility(Ability ability, UUID sourceId, Game game);
    
    void removeAllAbilities(UUID sourceId, Game game);

    void setLoyaltyUsed(boolean used);
    boolean isLoyaltyUsed();

    void beginningOfTurn(Game game);
    void endOfTurn(Game game);
    void checkControlChanged(Game game);
    int getTurnsOnBattlefield();

    void addPower(int power);
    void addToughness(int toughness);

    boolean isAttacking();
    int getBlocking();
    void setAttacking(boolean attacking);
    void setBlocking(int blocking);
    int getMaxBlocks();
    void setMaxBlocks(int maxBlocks);
    int getMinBlockedBy();
    void setMinBlockedBy(int minBlockedBy);
    boolean canAttack(Game game);
    boolean canBlock(UUID attackerId, Game game);
    boolean canBlockAny(Game game);
    boolean removeFromCombat(Game game);
    boolean isDeathtouched();

    /**
     * Returns the list of sources that dealt damage this turn to this permanent
     * @return
     */
    List<UUID> getDealtDamageByThisTurn();

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
     *
     * Can be null if no card was imprinted.
     * @return Imprinted card UUID.
     */
    List<UUID> getImprinted();

    /**
     * Allows to connect any card to permanent.
     * Very similar to Imprint except that it is for internal use only.
     *
     * @param key
     * @param connectedCard
     */
    void addConnectedCard(String key, UUID connectedCard);

    /**
     * Returns connected cards.
     * Very similar to Imprint except that it is for internal use only.
     * @return
     */
    List<UUID> getConnectedCards(String key);

    /**
     * Clear all connected cards.
     */
    void clearConnectedCards(String key);

    /**
     * Sets paired card.
     *
     * @param pairedCard
     */
    void setPairedCard(UUID pairedCard);

    /**
     * Gets paired card. Can return null.
     *
     * @return
     */
    UUID getPairedCard();

    /**
     * Makes permanent paired with no other permanent.
     */
    void clearPairedCard();

    @Override
    Permanent copy();

}
