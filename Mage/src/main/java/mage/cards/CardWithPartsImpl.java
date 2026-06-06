package mage.cards;

import mage.MageObject;
import mage.abilities.*;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;

import java.util.List;
import java.util.UUID;

/**
 * Base class for cards with two parts (left and right halves).
 * This includes split cards, modal double-faced cards, adventure cards, and flip cards.
 *
 * @param <P> the type of the card parts (halves), which must be SubCard of this card type
 * @param <C> self-referential type for the concrete card class
 * @author JayDi85, Jmlundeen
 */
@SuppressWarnings("unchecked")
public abstract class CardWithPartsImpl<P extends SubCard<C>, C extends CardWithPartsImpl<P, C>> extends CardImpl implements CardWithParts {

    protected P leftHalfCard;  // main card/front face in most zones
    protected P rightHalfCard; // secondary card/back face

    protected CardWithPartsImpl(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, String costs, SpellAbilityType spellAbilityType) {
        super(ownerId, setInfo, cardTypes, costs, spellAbilityType);
    }

    protected CardWithPartsImpl(CardWithPartsImpl<P, C> card) {
        super(card);
        // Copy the parts and set parent references
        // Unchecked casts are safe: P is the part type from the source card, C is this class type
        this.leftHalfCard = (P) card.getLeftHalfCard().copy();
        this.rightHalfCard = (P) card.getRightHalfCard().copy();
        // Set parent references
        this.leftHalfCard.setParentCard((C) this);
        this.rightHalfCard.setParentCard((C) this);
    }

    @Override
    public P getLeftHalfCard() {
        return leftHalfCard;
    }

    @Override
    public P getRightHalfCard() {
        return rightHalfCard;
    }

    @Override
    public void assignNewId() {
        super.assignNewId();
        leftHalfCard.assignNewId();
        rightHalfCard.assignNewId();
    }

    @Override
    public void setCopy(boolean isCopy, MageObject copiedFrom) {
        super.setCopy(isCopy, copiedFrom);
        leftHalfCard.setCopy(isCopy, copiedFrom);
        rightHalfCard.setCopy(isCopy, copiedFrom);
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        if (super.moveToZone(toZone, source, game, flag, appliedEffects)) {
            Zone currentZone = game.getState().getZone(getId());
            updatePartZones(currentZone, game);
            return true;
        }
        return false;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        super.setZone(zone, game);
        updatePartZones(zone, game);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        if (super.moveToExile(exileId, name, source, game, appliedEffects)) {
            Zone currentZone = game.getState().getZone(getId());
            updatePartZones(currentZone, game);
            return true;
        }
        return false;
    }

    @Override
    public void setParts(SubCard<?> left, SubCard<?> right) {
        // for card copy only - set new parts
        this.leftHalfCard = (P) left;
        this.rightHalfCard = (P) right;
        ((SubCard<C>) left).setParentCard((C) this);
        ((SubCard<C>) right).setParentCard((C) this);
    }

    /**
     * Updates the zones of the card parts when the main card moves zones.
     * Subclasses can override this for special zone handling (e.g., double-faced cards).
     *
     * @param zone the new zone for the main card
     * @param game the current game
     */
    protected void updatePartZones(Zone zone, Game game) {
        game.setZone(leftHalfCard.getId(), zone);
        game.setZone(rightHalfCard.getId(), zone);
        checkGoodZones(game);
    }

    /**
     * Runtime check for good zones of the parts.
     */
    protected void checkGoodZones(Game game) {
        Card leftPart = this.getLeftHalfCard();
        Card rightPart = this.getRightHalfCard();

        Zone zoneMain = game.getState().getZone(this.getId());
        Zone zoneLeft = game.getState().getZone(leftPart.getId());
        Zone zoneRight = game.getState().getZone(rightPart.getId());

        Zone needZoneLeft;
        Zone needZoneRight;
        needZoneLeft = needZoneRight = zoneMain;

        if (zoneLeft != needZoneLeft || zoneRight != needZoneRight) {
            String className = this.getClass().getSimpleName();
            throw new IllegalStateException("Wrong code usage: " + className + " uses wrong zones - " + this
                    + "\r\n" + String.format("* main zone: %s", zoneMain)
                    + "\r\n" + String.format("* left side: need %s, actual %s", needZoneLeft, zoneLeft)
                    + "\r\n" + String.format("* right side: need %s, actual %s", needZoneRight, zoneRight));

        }
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, Ability source) {
        // zone contains only one main card
        return super.removeFromZone(game, fromZone, source);
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        if (isCopy()) {
            super.updateZoneChangeCounter(game, event);
            return;
        }
        super.updateZoneChangeCounter(game, event);
        game.getState().updateZoneChangeCounter(leftHalfCard.getId());
        game.getState().updateZoneChangeCounter(rightHalfCard.getId());
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        super.setOwnerId(ownerId);
        abilities.setControllerId(ownerId);
        leftHalfCard.getAbilities().setControllerId(ownerId);
        leftHalfCard.setOwnerId(ownerId);
        rightHalfCard.getAbilities().setControllerId(ownerId);
        rightHalfCard.setOwnerId(ownerId);
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return getInnerAbilities(true, true);
    }

    @Override
    public Abilities<Ability> getInitAbilities() {
        // must init only parent related abilities, parts must be init separately
        return getInnerAbilities(false, false);
    }

    public Abilities<Ability> getSharedAbilities(Game game) {
        return super.getAbilities(game);
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        return getInnerAbilities(game, true, true);
    }

    /**
     * Determines if an ability should be ignored when combining abilities from parts.
     * Subclasses should override this to handle their specific spell ability types.
     *
     * @param ability the ability to check
     * @return true if the ability should be ignored
     */
    protected boolean isIgnoreDefaultAbility(Ability ability) {
        if (ability instanceof PlayLandAbility) {
            return true;
        }
        return ability instanceof SpellAbility && !((SpellAbility) ability).getSpellAbilityType().canCast();
    }

    /**
     * Gets combined abilities from this card and its parts.
     *
     * @param showLeftSide whether to include left side abilities
     * @param showRightSide whether to include right side abilities
     * @return combined abilities
     */
    protected Abilities<Ability> getInnerAbilities(boolean showLeftSide, boolean showRightSide) {
        return checkAbilities(super.getAbilities(),
                showLeftSide ? leftHalfCard.getAbilities() : new AbilitiesImpl<>(),
                showRightSide ? rightHalfCard.getAbilities() : new AbilitiesImpl<>());
    }


    /**
     * Gets combined abilities from this card and its parts (game-aware version).
     *
     * @param game the current game
     * @param showLeftSide whether to include left side abilities
     * @param showRightSide whether to include right side abilities
     * @return combined abilities
     */
    protected Abilities<Ability> getInnerAbilities(Game game, boolean showLeftSide, boolean showRightSide) {
        return checkAbilities(super.getAbilities(game),
                showLeftSide ? leftHalfCard.getAbilities(game) : new AbilitiesImpl<>(),
                showRightSide ? rightHalfCard.getAbilities(game) : new AbilitiesImpl<>());
    }

    /**
     * Combines abilities from main, left, and right parts, filtering out unwanted abilities.
     * @param mainAbilities
     * @param leftAbilities
     * @param rightAbilities
     * @return
     */
    private Abilities<Ability> checkAbilities(Abilities<Ability> mainAbilities, Abilities<Ability> leftAbilities, Abilities<Ability> rightAbilities) {
        Abilities<Ability> allAbilities = new AbilitiesImpl<>();

        for (Ability ability : mainAbilities) {
            if (isIgnoreDefaultAbility(ability)) {
                continue;
            }
            allAbilities.add(ability);
        }

        allAbilities.addAll(leftAbilities);

        for (Ability ability : rightAbilities) {
            if (ability instanceof SpellAbility && !((SpellAbility) ability).getSpellAbilityType().canCast()) {
                continue;
            }
            allAbilities.add(ability);
        }
        return allAbilities;
    }

    @Override
    public UUID getIdForBattlefield(Game game, Ability source) {
        return getDefaultCardSide().getId();
    }
}

