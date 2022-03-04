package mage.game.command;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.*;
import mage.abilities.common.CastCommanderAbility;
import mage.abilities.common.PlayLandAsCommanderAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.Card;
import mage.cards.FrameStyle;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.GameLog;
import mage.util.SubTypes;

import java.util.*;

public class Commander implements CommandObject {

    private final Card sourceObject;
    private boolean copy;
    private MageObject copyFrom; // copied card INFO (used to call original adjusters)
    private final Abilities<Ability> abilities = new AbilitiesImpl<>();

    public Commander(Card card) {
        this.sourceObject = card;

        // All abilities must be added to the game before usage. It adding by addCard and addCommandObject calls
        // Example: if commander from mdf card then
        // * commander object adds cast/play as commander abilities
        // * sourceObject adds normal cast/play abilities and all other things

        // replace spell ability by commander cast spell (to cast from command zone)
        for (Ability ability : card.getAbilities()) {
            if (ability instanceof SpellAbility) {
                SpellAbility spellAbility = (SpellAbility) ability;
                switch (spellAbility.getSpellAbilityType()) {
                    case BASE:
                    case BASE_ALTERNATE:
                    case SPLIT:
                    case SPLIT_FUSED:
                    case SPLIT_LEFT:
                    case SPLIT_RIGHT:
                    case MODAL:
                    case MODAL_LEFT:
                    case MODAL_RIGHT:
                    case ADVENTURE_SPELL:
                        // can be used from command zone
                        if (canUseAbilityFromCommandZone(spellAbility)) {
                            abilities.add(new CastCommanderAbility(card, spellAbility));
                        }
                        break;
                    case FACE_DOWN_CREATURE: // dynamic added spell for alternative cost like cast as face down
                    case SPLICE: // only from hand
                    case SPLIT_AFTERMATH: // only from graveyard
                        // can't use from command zone
                        break;
                    default:
                        throw new IllegalArgumentException("Error, unknown spell type in commander card: " + spellAbility.getSpellAbilityType() + " from " + card.getName());
                }
            }
        }

        // replace play land with commander play land (to play from command zone)
        for (Ability ability : card.getAbilities()) {
            if (ability instanceof PlayLandAbility) {
                if (canUseAbilityFromCommandZone(ability)) {
                    Ability newAbility = new PlayLandAsCommanderAbility((PlayLandAbility) ability);
                    abilities.add(newAbility);
                }
            }
        }

        // other abilities
        for (Ability ability : card.getAbilities()) {
            // skip already added above
            if (ability instanceof SpellAbility || ability instanceof PlayLandAbility) {
                continue;
            }

            // all other abilities must be added to commander (example: triggers from command zone, alternative cost, etc)
            // no changes to ability zone, so can add any
            Ability newAbility = ability.copy();
            abilities.add(newAbility);
        }
    }

    private boolean canUseAbilityFromCommandZone(Ability ability) {
        // ability can be restricted by zone usage, so you must ignore it for commander (example: Escape or Jumpstart)
        switch (ability.getZone()) {
            case ALL:
            case COMMAND:
            case HAND:
                return true;
            default:
                return false;
        }
    }

    private Commander(final Commander commander) {
        this.sourceObject = commander.sourceObject.copy();
        this.copy = commander.copy;
        this.copyFrom = (commander.copyFrom != null ? commander.copyFrom.copy() : null);
        this.abilities.addAll(commander.abilities.copy());
    }

    @Override
    public Card getSourceObject() {
        return sourceObject;
    }

    @Override
    public UUID getSourceId() {
        return sourceObject.getId();
    }

    @Override
    public UUID getControllerId() {
        return sourceObject.getOwnerId();
    }

    @Override
    public void assignNewId() {
    }

    @Override
    public CommandObject copy() {
        return new Commander(this);
    }

    @Override
    public void setCopy(boolean isCopy, MageObject copyFrom) {
        this.copy = isCopy;
        this.copyFrom = (copyFrom != null ? copyFrom.copy() : null);
    }

    @Override
    public boolean isCopy() {
        return this.copy;
    }

    @Override
    public MageObject getCopyFrom() {
        return this.copyFrom;
    }

    @Override
    public String getName() {
        return sourceObject.getName();
    }

    @Override
    public String getIdName() {
        return sourceObject.getName() + " [" + sourceObject.getId().toString().substring(0, 3) + ']';
    }

    @Override
    public String getLogName() {
        return GameLog.getColoredObjectIdName(this);
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public List<CardType> getCardType(Game game) {
        return sourceObject.getCardType(game);
    }

    @Override
    public SubTypes getSubtype() {
        return sourceObject.getSubtype();
    }

    @Override
    public SubTypes getSubtype(Game game) {
        return sourceObject.getSubtype(game);
    }

    @Override
    public boolean hasSubtype(SubType subtype, Game game) {
        return sourceObject.hasSubtype(subtype, game);
    }

    @Override
    public Set<SuperType> getSuperType() {
        return sourceObject.getSuperType();
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return abilities;
    }

    @Override
    public Abilities<Ability> getInitAbilities() {
        // see commander contruction comments for more info

        // collect ignore list
        Set<UUID> ignore = new HashSet<>();
        sourceObject.getAbilities().forEach(ability -> ignore.add(ability.getId()));

        // return only object specific abilities
        Abilities<Ability> res = new AbilitiesImpl<>();
        this.getAbilities().stream()
                .filter(ability -> !ignore.contains(ability.getId()))
                .forEach(res::add);
        return res;
    }

    @Override
    public boolean hasAbility(Ability ability, Game game) {
        if (this.getAbilities().contains(ability)) {
            return true;
        }
        Abilities<Ability> otherAbilities = game.getState().getAllOtherAbilities(getId());
        return otherAbilities != null && otherAbilities.contains(ability);
    }

    @Override
    public ObjectColor getColor() {
        return sourceObject.getColor();
    }

    @Override
    public ObjectColor getColor(Game game) {
        return sourceObject.getColor(game);
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        return sourceObject.getFrameColor(game);
    }

    @Override
    public FrameStyle getFrameStyle() {
        return sourceObject.getFrameStyle();
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return sourceObject.getManaCost();
    }

    @Override
    public int getManaValue() {
        return sourceObject.getManaValue();
    }

    @Override
    public MageInt getPower() {
        return sourceObject.getPower();
    }

    @Override
    public MageInt getToughness() {
        return sourceObject.getToughness();
    }

    @Override
    public int getStartingLoyalty() {
        return sourceObject.getStartingLoyalty();
    }

    @Override
    public void setStartingLoyalty(int startingLoyalty) {
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
    }

    @Override
    public UUID getId() {
        return sourceObject.getId();
    }

    @Override
    public String getImageName() {
        return sourceObject.getImageName();
    }

    @Override
    public int getZoneChangeCounter(Game game) {
        return sourceObject.getZoneChangeCounter(game);
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        sourceObject.updateZoneChangeCounter(game, event);
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        sourceObject.setZoneChangeCounter(value, game);
    }

    @Override
    public boolean isAllCreatureTypes(Game game) {
        return sourceObject.isAllCreatureTypes(game);
    }

    @Override
    public void setIsAllCreatureTypes(boolean value) {
        sourceObject.setIsAllCreatureTypes(value);
    }

    @Override
    public void setIsAllCreatureTypes(Game game, boolean value) {
        sourceObject.setIsAllCreatureTypes(game, value);
    }

    @Override
    public void removePTCDA() {
    }
}
