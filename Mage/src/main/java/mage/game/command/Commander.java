package mage.game.command;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.*;
import mage.abilities.common.CastCommanderAbility;
import mage.abilities.common.PlayLandAsCommanderAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.text.TextPart;
import mage.cards.Card;
import mage.cards.FrameStyle;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.GameLog;
import mage.util.SubTypeList;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Commander implements CommandObject {

    private final Card sourceObject;
    private boolean copy;
    private MageObject copyFrom; // copied card INFO (used to call original adjusters)
    private final Abilities<Ability> abilities = new AbilitiesImpl<>();

    public Commander(Card card) {
        this.sourceObject = card;

        // replace spell ability by commander cast spell (to cast from command zone)
        if (card.getSpellAbility() != null) {
            abilities.add(new CastCommanderAbility(card, card.getSpellAbility()));
        }

        // replace alternative spell abilities by commander cast spell (to cast from command zone)
        for (Ability ability : card.getAbilities()) {
            if (ability instanceof SpellAbility) {
                SpellAbility spellAbility = (SpellAbility) ability;
                if (spellAbility.getSpellAbilityType() == SpellAbilityType.BASE_ALTERNATE) {
                    abilities.add(new CastCommanderAbility(card, spellAbility));
                }
            }
        }

        // replace play land with commander play land (to play from command zone)
        for (Ability ability : card.getAbilities()) {
            if (ability instanceof PlayLandAbility) {
                Ability newAbility = new PlayLandAsCommanderAbility((PlayLandAbility) ability);
                abilities.add(newAbility);
            }
        }

        // other abilities
        for (Ability ability : card.getAbilities()) {
            if (!(ability instanceof SpellAbility) && !(ability instanceof PlayLandAbility)) {
                Ability newAbility = ability.copy();
                abilities.add(newAbility);
            }
        }
    }

    private Commander(final Commander commander) {
        this.sourceObject = commander.sourceObject;
        this.copy = commander.copy;
        this.copyFrom = (commander.copyFrom != null ? commander.copyFrom.copy() : null);
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
    public Set<CardType> getCardType() {
        return sourceObject.getCardType();
    }

    @Override
    public SubTypeList getSubtype(Game game) {
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
    public boolean hasAbility(UUID abilityId, Game game) {
        if (this.getAbilities().containsKey(abilityId)) {
            return true;
        }
        Abilities<Ability> otherAbilities = game.getState().getAllOtherAbilities(getId());
        return otherAbilities != null && otherAbilities.containsKey(abilityId);
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
    public int getConvertedManaCost() {
        return sourceObject.getConvertedManaCost();
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
    public boolean isAllCreatureTypes() {
        return false;
    }

    @Override
    public void setIsAllCreatureTypes(boolean value) {
    }

    @Override
    public List<TextPart> getTextParts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TextPart addTextPart(TextPart textPart) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removePTCDA() {
    }
}
