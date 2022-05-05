package mage.cards;

import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;

import java.util.List;
import java.util.UUID;

/**
 * @author phulin
 */
public abstract class AdventureCard extends CardImpl {

    /* The adventure spell card, i.e. Swift End. */
    protected AdventureCardSpell spellCard;

    public AdventureCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, CardType[] typesSpell, String costs, String adventureName, String costsSpell) {
        super(ownerId, setInfo, types, costs);
        this.spellCard = new AdventureCardSpellImpl(ownerId, setInfo, adventureName, typesSpell, costsSpell, this);
    }

    public AdventureCard(AdventureCard card) {
        super(card);
        this.spellCard = card.getSpellCard().copy();
        this.spellCard.setParentCard(this);
    }

    public AdventureCardSpell getSpellCard() {
        return spellCard;
    }

    public void setParts(AdventureCardSpell cardSpell) {
        // for card copy only - set new parts
        this.spellCard = cardSpell;
        cardSpell.setParentCard(this);
    }

    @Override
    public void assignNewId() {
        super.assignNewId();
        spellCard.assignNewId();
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        if (super.moveToZone(toZone, source, game, flag, appliedEffects)) {
            game.getState().setZone(getSpellCard().getId(), toZone);
            return true;
        }
        return false;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        super.setZone(zone, game);
        game.setZone(getSpellCard().getId(), zone);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        if (super.moveToExile(exileId, name, source, game, appliedEffects)) {
            Zone currentZone = game.getState().getZone(getId());
            game.getState().setZone(getSpellCard().getId(), currentZone);
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
        getSpellCard().updateZoneChangeCounter(game, event);
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        if (ability.getSpellAbilityType() == SpellAbilityType.ADVENTURE_SPELL) {
            return this.getSpellCard().cast(game, fromZone, ability, controllerId);
        }
        this.getSpellCard().getSpellAbility().setControllerId(controllerId);
        return super.cast(game, fromZone, ability, controllerId);
    }

    @Override
    public Abilities<Ability> getAbilities() {
        Abilities<Ability> allAbilities = new AbilitiesImpl<>();
        allAbilities.addAll(spellCard.getAbilities());
        allAbilities.addAll(super.getAbilities());
        return allAbilities;
    }

    @Override
    public Abilities<Ability> getInitAbilities() {
        // must init only parent related abilities, spell card must be init separately
        return super.getAbilities();
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        Abilities<Ability> allAbilities = new AbilitiesImpl<>();
        allAbilities.addAll(spellCard.getAbilities(game));
        allAbilities.addAll(super.getAbilities(game));
        return allAbilities;
    }

    public Abilities<Ability> getSharedAbilities(Game game) {
        // abilities without spellcard
        return super.getAbilities(game);
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        super.setOwnerId(ownerId);
        abilities.setControllerId(ownerId);
        spellCard.getAbilities().setControllerId(ownerId);
        spellCard.setOwnerId(ownerId);
    }
}
