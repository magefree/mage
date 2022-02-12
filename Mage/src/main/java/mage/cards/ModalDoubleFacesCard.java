package mage.cards;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.*;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.GameState;
import mage.game.events.ZoneChangeEvent;
import mage.util.CardUtil;
import mage.util.SubTypes;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author JayDi85
 */
public abstract class ModalDoubleFacesCard extends CardImpl implements CardWithHalves {

    protected Card leftHalfCard; // main card in all zone
    protected Card rightHalfCard; // second side card, can be only in stack and battlefield zones

    public ModalDoubleFacesCard(UUID ownerId, CardSetInfo setInfo,
                                CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
                                String secondSideName, CardType[] typesRight, SubType[] subTypesRight, String costsRight) {
        super(ownerId, setInfo, typesLeft, costsLeft + costsRight, SpellAbilityType.MODAL);
        // main card name must be same as left side
        leftHalfCard = new ModalDoubleFacesCardHalfImpl(this.getOwnerId(), new CardSetInfo(setInfo.getName(), setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()),
                typesLeft, subTypesLeft, costsLeft, this, SpellAbilityType.MODAL_LEFT);
        rightHalfCard = new ModalDoubleFacesCardHalfImpl(this.getOwnerId(), new CardSetInfo(secondSideName, setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()),
                typesRight, subTypesRight, costsRight, this, SpellAbilityType.MODAL_RIGHT);
    }

    public ModalDoubleFacesCard(ModalDoubleFacesCard card) {
        super(card);
        this.leftHalfCard = card.getLeftHalfCard().copy();
        ((ModalDoubleFacesCardHalf) leftHalfCard).setParentCard(this);
        this.rightHalfCard = card.rightHalfCard.copy();
        ((ModalDoubleFacesCardHalf) rightHalfCard).setParentCard(this);
    }

    public ModalDoubleFacesCardHalf getLeftHalfCard() {
        return (ModalDoubleFacesCardHalf) leftHalfCard;
    }

    public ModalDoubleFacesCardHalf getRightHalfCard() {
        return (ModalDoubleFacesCardHalf) rightHalfCard;
    }

    public void setParts(ModalDoubleFacesCardHalf leftHalfCard, ModalDoubleFacesCardHalf rightHalfCard) {
        // for card copy only - set new parts
        this.leftHalfCard = leftHalfCard;
        leftHalfCard.setParentCard(this);
        this.rightHalfCard = rightHalfCard;
        rightHalfCard.setParentCard(this);
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
            game.getState().setZone(leftHalfCard.getId(), toZone);
            game.getState().setZone(rightHalfCard.getId(), toZone);
            return true;
        }
        return false;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        super.setZone(zone, game);
        game.setZone(leftHalfCard.getId(), zone);
        game.setZone(rightHalfCard.getId(), zone);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        if (super.moveToExile(exileId, name, source, game, appliedEffects)) {
            Zone currentZone = game.getState().getZone(getId());
            game.getState().setZone(leftHalfCard.getId(), currentZone);
            game.getState().setZone(rightHalfCard.getId(), currentZone);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, Ability source) {
        // zone contains only one main card
        return super.removeFromZone(game, fromZone, source);
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        if (isCopy()) { // same as meld cards
            super.updateZoneChangeCounter(game, event);
            return;
        }
        super.updateZoneChangeCounter(game, event);
        leftHalfCard.updateZoneChangeCounter(game, event);
        rightHalfCard.updateZoneChangeCounter(game, event);
    }

    @Override
    public Counters getCounters(Game game) {
        return getCounters(game.getState());
    }

    @Override
    public Counters getCounters(GameState state) {
        return state.getCardState(leftHalfCard.getId()).getCounters();
    }

    @Override
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game, List<UUID> appliedEffects, boolean isEffect, int maxCounters) {
        return leftHalfCard.addCounters(counter, playerAddingCounters, source, game, appliedEffects, isEffect, maxCounters);
    }

    @Override
    public void removeCounters(String name, int amount, Ability source, Game game) {
        leftHalfCard.removeCounters(name, amount, source, game);
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        switch (ability.getSpellAbilityType()) {
            case MODAL_LEFT:
                return this.leftHalfCard.cast(game, fromZone, ability, controllerId);
            case MODAL_RIGHT:
                return this.rightHalfCard.cast(game, fromZone, ability, controllerId);
            default:
                if (this.leftHalfCard.getSpellAbility() != null)
                    this.leftHalfCard.getSpellAbility().setControllerId(controllerId);
                if (this.rightHalfCard.getSpellAbility() != null)
                    this.rightHalfCard.getSpellAbility().setControllerId(controllerId);
                return super.cast(game, fromZone, ability, controllerId);
        }
    }

    @Override
    public Set<SuperType> getSuperType() {
        // CardImpl's constructor can call some code on init, so you must check left/right before
        // it's a bad workaround
        return leftHalfCard != null ? leftHalfCard.getSuperType() : supertype;
    }

    @Override
    public List<CardType> getCardType(Game game) {
        // CardImpl's constructor can call some code on init, so you must check left/right before
        // it's a bad workaround
        return leftHalfCard != null ? leftHalfCard.getCardType(game) : cardType;
    }

    @Override
    public SubTypes getSubtype() {
        // rules: While a double-faced card isn’t on the stack or battlefield, consider only the characteristics of its front face.
        // CardImpl's constructor can call some code on init, so you must check left/right before
        return leftHalfCard != null ? leftHalfCard.getSubtype() : subtype;
    }

    @Override
    public SubTypes getSubtype(Game game) {
        // rules: While a double-faced card isn’t on the stack or battlefield, consider only the characteristics of its front face.
        // CardImpl's constructor can call some code on init, so you must check left/right before
        return leftHalfCard != null ? leftHalfCard.getSubtype(game) : subtype;
    }

    @Override
    public boolean hasSubtype(SubType subtype, Game game) {
        return leftHalfCard.hasSubtype(subtype, game);
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return getInnerAbilities(true, true);
    }

    @Override
    public Abilities<Ability> getInitAbilities() {
        // must init only parent related abilities, spell card must be init separately
        return getInnerAbilities(false, false);
    }

    public Abilities<Ability> getSharedAbilities(Game game) {
        // no shared abilities for mdf cards (e.g. must be left or right only)
        return new AbilitiesImpl<>();
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        return getInnerAbilities(game, true, true);
    }

    private boolean isIgnoreDefaultAbility(Ability ability) {
        // ignore default play/spell ability from main card (only halfes are actual)
        // default abilities added on card creation from card type and can't be skipped

        // skip cast spell
        if (ability instanceof SpellAbility && ((SpellAbility) ability).getSpellAbilityType() == SpellAbilityType.MODAL) {
            return true;
        }

        // skip play land
        return ability instanceof PlayLandAbility;
    }

    private Abilities<Ability> getInnerAbilities(Game game, boolean showLeftSide, boolean showRightSide) {
        Abilities<Ability> allAbilites = new AbilitiesImpl<>();

        for (Ability ability : super.getAbilities(game)) {
            if (isIgnoreDefaultAbility(ability)) {
                continue;
            }
            allAbilites.add(ability);
        }

        if (showLeftSide) {
            allAbilites.addAll(leftHalfCard.getAbilities(game));
        }
        if (showRightSide) {
            allAbilites.addAll(rightHalfCard.getAbilities(game));
        }

        return allAbilites;
    }

    private Abilities<Ability> getInnerAbilities(boolean showLeftSide, boolean showRightSide) {
        Abilities<Ability> allAbilites = new AbilitiesImpl<>();

        for (Ability ability : super.getAbilities()) {
            if (isIgnoreDefaultAbility(ability)) {
                continue;
            }
            allAbilites.add(ability);
        }

        if (showLeftSide) {
            allAbilites.addAll(leftHalfCard.getAbilities());
        }

        if (showRightSide) {
            allAbilites.addAll(rightHalfCard.getAbilities());
        }

        return allAbilites;
    }

    @Override
    public List<String> getRules() {
        // rules must show only main side (another side visible by toggle/transform button in GUI)
        // card hints from both sides
        return CardUtil.getCardRulesWithAdditionalInfo(
                this.getId(),
                this.getName(),
                this.getInnerAbilities(true, false),
                this.getInnerAbilities(true, true)
        );
    }

    @Override
    public List<String> getRules(Game game) {
        // rules must show only main side (another side visible by toggle/transform button in GUI)
        // card hints from both sides
        return CardUtil.getCardRulesWithAdditionalInfo(
                game,
                this.getId(),
                this.getName(),
                this.getInnerAbilities(game, true, false),
                this.getInnerAbilities(game, true, true)
        );
    }

    @Override
    public boolean hasAbility(Ability ability, Game game) {
        return super.hasAbility(ability, game);
    }

    @Override
    public ObjectColor getColor() {
        return leftHalfCard.getColor();
    }

    @Override
    public ObjectColor getColor(Game game) {
        return leftHalfCard.getColor(game);
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        return leftHalfCard.getFrameColor(game);
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
    public ManaCosts<ManaCost> getManaCost() {
        return leftHalfCard.getManaCost();
    }

    @Override
    public int getManaValue() {
        // Rules:
        // The converted mana cost of a modal double-faced card is based on the characteristics of the
        // face that’s being considered. On the stack and battlefield, consider whichever face is up.
        // In all other zones, consider only the front face. This is different than how the converted
        // mana cost of a transforming double-faced card is determined.

        // on stack or battlefield it must be half card with own cost
        return leftHalfCard.getManaValue();
    }

    @Override
    public MageInt getPower() {
        return leftHalfCard.getPower();
    }

    @Override
    public MageInt getToughness() {
        return leftHalfCard.getToughness();
    }
}
