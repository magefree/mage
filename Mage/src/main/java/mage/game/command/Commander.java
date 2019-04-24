
package mage.game.command;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.CastCommanderAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.text.TextPart;
import mage.cards.Card;
import mage.cards.FrameStyle;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.GameLog;
import mage.util.SubTypeList;

public class Commander implements CommandObject {

    private final Card sourceObject;
    private final Abilities<Ability> abilities = new AbilitiesImpl<>();

    public Commander(Card card) {
        this.sourceObject = card;
        abilities.add(new CastCommanderAbility(card));
        for (Ability ability : card.getAbilities()) {
            if (!(ability instanceof SpellAbility)) {
                Ability newAbility = ability.copy();
                abilities.add(newAbility);
            }
        }
    }

    private Commander(Commander copy) {
        this.sourceObject = copy.sourceObject;
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
    public EnumSet<CardType> getCardType() {
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
    public EnumSet<SuperType> getSuperType() {
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
    public void setCopy(boolean isCopy) {
    }

    @Override
    public boolean isCopy() {
        return false;
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
