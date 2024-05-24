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
 * @author JayDi85
 */
public abstract class ModalDoubleFacedCard extends CardImpl implements CardWithHalves {

    protected Card leftHalfCard; // main card in all zone
    protected Card rightHalfCard; // second side card, can be only in stack and battlefield zones

    public ModalDoubleFacedCard(
            UUID ownerId, CardSetInfo setInfo,
            CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String secondSideName,
            CardType[] typesRight, SubType[] subTypesRight, String costsRight
    ) {
        this(
                ownerId, setInfo,
                new SuperType[]{}, typesLeft, subTypesLeft, costsLeft,
                secondSideName,
                new SuperType[]{}, typesRight, subTypesRight, costsRight
        );
    }

    public ModalDoubleFacedCard(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] superTypesLeft, CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String secondSideName,
            SuperType[] superTypesRight, CardType[] typesRight, SubType[] subTypesRight, String costsRight
    ) {
        super(ownerId, setInfo, typesLeft, costsLeft + costsRight, SpellAbilityType.MODAL);
        // main card name must be same as left side
        leftHalfCard = new ModalDoubleFacedCardHalfImpl(
                this.getOwnerId(), setInfo.copy(),
                superTypesLeft, typesLeft, subTypesLeft, costsLeft,
                this, SpellAbilityType.MODAL_LEFT
        );
        rightHalfCard = new ModalDoubleFacedCardHalfImpl(
                this.getOwnerId(), new CardSetInfo(secondSideName, setInfo),
                superTypesRight, typesRight, subTypesRight, costsRight,
                this, SpellAbilityType.MODAL_RIGHT
        );
    }

    public ModalDoubleFacedCard(ModalDoubleFacedCard card) {
        super(card);
        this.leftHalfCard = card.getLeftHalfCard().copy();
        ((ModalDoubleFacedCardHalf) leftHalfCard).setParentCard(this);
        this.rightHalfCard = card.rightHalfCard.copy();
        ((ModalDoubleFacedCardHalf) rightHalfCard).setParentCard(this);
    }

    public ModalDoubleFacedCardHalf getLeftHalfCard() {
        return (ModalDoubleFacedCardHalf) leftHalfCard;
    }

    public ModalDoubleFacedCardHalf getRightHalfCard() {
        return (ModalDoubleFacedCardHalf) rightHalfCard;
    }

    public void setParts(ModalDoubleFacedCardHalf leftHalfCard, ModalDoubleFacedCardHalf rightHalfCard) {
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
        leftHalfCard.setCopy(isCopy, copiedFrom); // TODO: must check copiedFrom and assign sides? (??? related to #8476 ???)
        rightHalfCard.setCopy(isCopy, copiedFrom);
    }

    private void setSideZones(Zone mainZone, Game game) {
        switch (mainZone) {
            case BATTLEFIELD:
            case STACK:
                throw new IllegalArgumentException("Wrong code usage: you must put to battlefield/stack only real side card (half), not main");
            default:
                // must keep both sides in same zone cause xmage need access to cost reduction, spell
                // and other abilities before put it to stack (in playable calcs)
                game.setZone(leftHalfCard.getId(), mainZone);
                game.setZone(rightHalfCard.getId(), mainZone);
                break;
        }
        checkGoodZones(game, this);
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        if (super.moveToZone(toZone, source, game, flag, appliedEffects)) {
            Zone currentZone = game.getState().getZone(getId());
            setSideZones(currentZone, game);
            return true;
        }
        return false;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        super.setZone(zone, game);
        setSideZones(zone, game);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        if (super.moveToExile(exileId, name, source, game, appliedEffects)) {
            Zone currentZone = game.getState().getZone(getId());
            setSideZones(currentZone, game);
            return true;
        }
        return false;
    }

    /**
     * Runtime check for good zones and other MDF data
     */
    public static void checkGoodZones(Game game, ModalDoubleFacedCard card) {
        Card leftPart = card.getLeftHalfCard();
        Card rightPart = card.getRightHalfCard();

        Zone zoneMain = game.getState().getZone(card.getId());
        Zone zoneLeft = game.getState().getZone(leftPart.getId());
        Zone zoneRight = game.getState().getZone(rightPart.getId());

        // runtime check:
        // * in battlefield and stack - card + one of the sides (another side in outside zone)
        // * in other zones - card + both sides (need both sides due cost reductions, spell and other access before put to stack)
        //
        // 712.8a While a double-faced card is outside the game or in a zone other than the battlefield or stack,
        // it has only the characteristics of its front face.
        //
        // 712.8f While a modal double-faced spell is on the stack or a modal double-faced permanent is on the battlefield,
        // it has only the characteristics of the face that’s up.
        Zone needZoneLeft;
        Zone needZoneRight;
        switch (zoneMain) {
            case BATTLEFIELD:
            case STACK:
                if (zoneMain == zoneLeft) {
                    needZoneLeft = zoneMain;
                    needZoneRight = Zone.OUTSIDE;
                } else if (zoneMain == zoneRight) {
                    needZoneLeft = Zone.OUTSIDE;
                    needZoneRight = zoneMain;
                } else {
                    // impossible
                    needZoneLeft = zoneMain;
                    needZoneRight = Zone.OUTSIDE;
                }
                break;
            default:
                needZoneLeft = zoneMain;
                needZoneRight = zoneMain;
                break;
        }

        if (zoneLeft != needZoneLeft || zoneRight != needZoneRight) {
            throw new IllegalStateException("Wrong code usage: MDF card uses wrong zones - " + card
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
                if (this.leftHalfCard.getSpellAbility() != null) {
                    this.leftHalfCard.getSpellAbility().setControllerId(controllerId);
                }
                if (this.rightHalfCard.getSpellAbility() != null) {
                    this.rightHalfCard.getSpellAbility().setControllerId(controllerId);
                }
                return super.cast(game, fromZone, ability, controllerId);
        }
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
