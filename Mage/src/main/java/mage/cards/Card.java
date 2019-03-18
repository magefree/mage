
package mage.cards;

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
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.GameState;
import mage.game.permanent.Permanent;

public interface Card extends MageObject {

    UUID getOwnerId();

    String getCardNumber();

    Rarity getRarity();

    void setOwnerId(UUID ownerId);

    Abilities<Ability> getAbilities(Game game);

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

    boolean moveToZone(Zone zone, UUID sourceId, Game game, boolean flag, List<UUID> appliedEffects);

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

    boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, List<UUID> appliedEffects);

    boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId);

    boolean removeFromZone(Game game, Zone fromZone, UUID sourceId);

    boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId);

    boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped);

    boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, boolean facedown);

    boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, boolean facedown, List<UUID> appliedEffects);

    void setZone(Zone zone, Game game);

    List<Mana> getMana();

    /**
     *
     * @return true if there exists various art images for this card
     */
    boolean getUsesVariousArt();

    Counters getCounters(Game game);

    Counters getCounters(GameState state);

    void addAbility(Ability ability);

    boolean addCounters(Counter counter, Ability source, Game game);

    boolean addCounters(Counter counter, Ability source, Game game, boolean isEffect);

    boolean addCounters(Counter counter, Ability source, Game game, List<UUID> appliedEffects);

    boolean addCounters(Counter counter, Ability source, Game game, List<UUID> appliedEffects, boolean isEffect);

    void removeCounters(String name, int amount, Game game);

    void removeCounters(Counter counter, Game game);

    @Override
    Card copy();

    /**
     *
     * @return The main card of a split half card, otherwise the card itself is
     * returned
     */
    Card getMainCard();

    /**
     * Gets the colors that are in the casting cost but also in the rules text
     * as far as not included in reminder text.
     *
     * @return
     */
    FilterMana getColorIdentity();

    List<UUID> getAttachments();

    boolean addAttachment(UUID permanentId, Game game);

    boolean removeAttachment(UUID permanentId, Game game);

    default boolean isOwnedBy(UUID controllerId){
        return getOwnerId().equals(controllerId);
    }
}
