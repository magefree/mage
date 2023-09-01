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
import java.util.UUID;

/**
 * @author JayDi85, TheElk801
 */
public abstract class DoubleFacedCard extends CardImpl implements CardWithHalves {

    protected DoubleFacedCardHalf leftHalfCard; // main card in all zone
    protected DoubleFacedCardHalf rightHalfCard; // second side card, can be only in stack and battlefield zones

    protected DoubleFacedCard(
            UUID ownerId, CardSetInfo setInfo,
            CardType[] cardTypes, String costs, SpellAbilityType spellAbilityType,
            DoubleFacedCardHalf leftHalfCard, DoubleFacedCardHalf rightHalfCard
    ) {
        super(ownerId, setInfo, cardTypes, costs, spellAbilityType);
        this.leftHalfCard = leftHalfCard;
        this.leftHalfCard.setParentCard(this);
        this.leftHalfCard.setIsFront(true);
        this.rightHalfCard = rightHalfCard;
        this.rightHalfCard.setParentCard(this);
        this.rightHalfCard.setIsFront(false);
    }

    protected DoubleFacedCard(final DoubleFacedCard card) {
        super(card);
        this.leftHalfCard = card.getLeftHalfCard().copy();
        this.leftHalfCard.setParentCard(this);
        this.rightHalfCard = card.getRightHalfCard().copy();
        this.rightHalfCard.setParentCard(this);
    }

    public DoubleFacedCardHalf getLeftHalfCard() {
        return leftHalfCard;
    }

    public DoubleFacedCardHalf getRightHalfCard() {
        return rightHalfCard;
    }

    public void setParts(DoubleFacedCardHalf leftHalfCard, DoubleFacedCardHalf rightHalfCard) {
        // for card copy only - set new parts
        this.leftHalfCard = leftHalfCard;
        this.leftHalfCard.setParentCard(this);
        this.rightHalfCard = rightHalfCard;
        this.rightHalfCard.setParentCard(this);
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
    public List<SuperType> getSuperType(Game game) {
        // CardImpl's constructor can call some code on init, so you must check left/right before
        // it's a bad workaround
        return leftHalfCard != null ? leftHalfCard.getSuperType(game) : supertype;
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
        if (ability instanceof SpellAbility) {
            switch (((SpellAbility) ability).getSpellAbilityType()) {
                case MODAL:
                case TRANSFORMING:
                    return true;
                default:
                    return false;
            }
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

    @Override
    public Card getSecondCardFace() {
        return rightHalfCard;
    }
}
