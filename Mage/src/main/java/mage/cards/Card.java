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
package mage.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.GameState;
import mage.game.permanent.Permanent;

public interface Card extends MageObject {

    UUID getOwnerId();

    String getCardNumber();

    Rarity getRarity();

    void setOwnerId(UUID ownerId);

    public Abilities<Ability> getAbilities(Game game);

    void setSpellAbility(SpellAbility ability);

    SpellAbility getSpellAbility();

    List<String> getRules(); // gets base card rules

    List<String> getRules(Game game);  // gets card rules + in game modifications

    String getExpansionSetCode();

    String getTokenSetCode();

    String getTokenDescriptor();

    void checkForCountersToAdd(Permanent permanent, Game game);

    void setFaceDown(boolean value, Game game);

    boolean isFaceDown(Game game);

    boolean turnFaceUp(Game game, UUID playerId);

    boolean turnFaceDown(Game game, UUID playerId);

    boolean isFlipCard();

    String getFlipCardName();

    boolean isSplitCard();

    boolean isTransformable();

    void setTransformable(boolean transformable);

    Card getSecondCardFace();

    boolean isNightCard();

    void assignNewId();

    void addInfo(String key, String value, Game game);

    /**
     * Moves the card to the specified zone
     *
     * @param zone
     * @param sourceId
     * @param game
     * @param flag If zone
     * <ul>
     * <li>LIBRARY: <ul><li>true - put on top</li><li>false - put on
     * bottom</li></ul></li>
     * <li>BATTLEFIELD: <ul><li>true - tapped</li><li>false -
     * untapped</li></ul></li>
     * <li>GRAVEYARD: <ul><li>true - not from Battlefield</li><li>false - from
     * Battlefield</li></ul></li>
     * </ul>
     * @return true if card was moved to zone
     */
    boolean moveToZone(Zone zone, UUID sourceId, Game game, boolean flag);

    boolean moveToZone(Zone zone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects);

    /**
     * Moves the card to an exile zone
     *
     * @param exileId set to null for generic exile zone
     * @param name used for exile zone with the specified exileId
     * @param sourceId
     * @param game
     * @return true if card was moved to zone
     */
    boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game);

    boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, ArrayList<UUID> appliedEffects);

    boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId);

    boolean removeFromZone(Game game, Zone fromZone, UUID sourceId);

    boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId);

    boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped);

    boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, boolean facedown);

    boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, boolean facedown, ArrayList<UUID> appliedEffects);

    void setZone(Zone zone, Game game);

    List<Mana> getMana();

    /**
     *
     * @return true if there exists various art images for this card
     */
    boolean getUsesVariousArt();

    Counters getCounters(Game game);

    Counters getCounters(GameState state);

    boolean addCounters(Counter counter, Game game);

    boolean addCounters(Counter counter, Game game, ArrayList<UUID> appliedEffects);

    void removeCounters(String name, int amount, Game game);

    void removeCounters(Counter counter, Game game);

    @Override
    Card copy();

    /**
     *
     * @return The main card of a split half card, otherwise thae card itself is
     * returned
     */
    Card getMainCard();
}
