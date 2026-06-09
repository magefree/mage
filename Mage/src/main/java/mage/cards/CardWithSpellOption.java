package mage.cards;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.GameState;
import mage.util.CardUtil;
import mage.util.SubTypes;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author phulin, jmlundeen
 * @param <P> the type of the card halves
 * @param <C> the self-referential type for the concrete card class
 */
public abstract class CardWithSpellOption<P extends CardWithSpellOptionHalf<C>, C extends CardWithSpellOption<P, C>> extends CardWithPartsImpl<P, C> {

    boolean finalized = false;

    protected CardWithSpellOption(UUID ownerId, CardSetInfo setInfo, CardType[] types, String costs, SpellAbilityType spellAbilityType) {
        super(ownerId, setInfo, types, costs, spellAbilityType);
    }

    public CardWithSpellOption(CardWithSpellOption<P, C> card) {
        super(card);
        finalized = card.finalized;
    }

    @Override
    public Card getDefaultCardSide() {
        return getLeftHalfCard();
    }

    @Override
    protected void updatePartZones(Zone zone, Game game) {
        if (Objects.requireNonNull(zone) == Zone.BATTLEFIELD) {
            throw new IllegalArgumentException("Wrong code usage: attempting to put main card directly onto the battlefield - " + this);
        } else {
            game.setZone(leftHalfCard.getId(), zone);
            game.setZone(rightHalfCard.getId(), zone);
        }
        checkGoodZones(game);
    }

    @Override
    public void checkGoodZones(Game game) {
        Card leftPart = this.getLeftHalfCard();
        Card rightPart = this.getRightHalfCard();

        Zone zoneMain = game.getState().getZone(this.getId());
        Zone zoneLeft = game.getState().getZone(leftPart.getId());
        Zone zoneRight = game.getState().getZone(rightPart.getId());

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
            throw new IllegalStateException("Wrong code usage: Omen/Adventure card uses wrong zones - " + this
                    + "\r\n" + String.format("* main zone: %s", zoneMain)
                    + "\r\n" + String.format("* left side: need %s, actual %s", needZoneLeft, zoneLeft)
                    + "\r\n" + String.format("* right side: need %s, actual %s", needZoneRight, zoneRight));
        }
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return getInnerAbilities(true, false);
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        return getInnerAbilities(true, false);
    }

    @Override
    public Counters getCounters(Game game) {
        return getCounters(game.getState());
    }

    @Override
    public Counters getCounters(GameState state) {
        return state.getCardState(getLeftHalfCard().getId()).getCounters();
    }

    @Override
    public boolean addCounters(Counter counter, UUID playerAddingCounters, Ability source, Game game, List<UUID> appliedEffects, boolean isEffect, int maxCounters) {
        return getLeftHalfCard().addCounters(counter, playerAddingCounters, source, game, appliedEffects, isEffect, maxCounters);
    }

    @Override
    public void removeCounters(String counterName, int amount, Ability source, Game game) {
        getLeftHalfCard().removeCounters(counterName, amount, source, game);
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        switch (ability.getSpellAbilityType()) {
            case ADVENTURE_OMEN_LEFT:
                return this.leftHalfCard.cast(game, fromZone, ability, controllerId);
            case ADVENTURE_OMEN_RIGHT:
                return this.rightHalfCard.cast(game, fromZone, ability, controllerId);
            default:
                return super.cast(game, fromZone, ability, controllerId);
        }
    }

    @Override
    public List<SuperType> getSuperType(Game game) {
        // rules: While an omen/adventure card isn't on the stack or battlefield, consider only the characteristics of its main face.
        return getLeftHalfCard().getSuperType(game);
    }

    @Override
    public List<CardType> getCardType(Game game) {
        // rules: While an omen/adventure card isn't on the stack or battlefield, consider only the characteristics of its main face.
        return getLeftHalfCard().getCardType(game);
    }

    @Override
    public SubTypes getSubtype(Game game) {
        // rules: While an omen/adventure card isn't on the stack or battlefield, consider only the characteristics of its main face.
        return getLeftHalfCard().getSubtype(game);
    }

    @Override
    public boolean hasSubtype(SubType subtype, Game game) {
        return getLeftHalfCard().hasSubtype(subtype, game);
    }

    @Override
    public List<String> getRules() {
        // rules must show only main side
        // card hints from both sides
        return CardUtil.getCardRulesWithAdditionalInfo(
                this,
                this.getInnerAbilities(true, false),
                this.getInnerAbilities(true, true)
        );
    }

    @Override
    public List<String> getRules(Game game) {
        // rules must show only main side
        // card hints from both sides
        return CardUtil.getCardRulesWithAdditionalInfo(
                game,
                this,
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
        return getLeftHalfCard().getColor();
    }

    @Override
    public ObjectColor getColor(Game game) {
        return getLeftHalfCard().getColor(game);
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        return getLeftHalfCard().getFrameColor(game);
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return getLeftHalfCard().getManaCost();
    }

    @Override
    public int getManaValue() {
        // Rules:
        // The characteristics of an omen/adventure card not on the stack or battlefield use its main face.
        return getLeftHalfCard().getManaValue();
    }

    @Override
    public MageInt getPower() {
        return getLeftHalfCard().getPower();
    }

    @Override
    public MageInt getToughness() {
        return getLeftHalfCard().getToughness();
    }

    protected abstract void finalizeCard();

    public boolean isFinalized() {
        return finalized;
    }
}
