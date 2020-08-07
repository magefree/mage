package mage.cards;

import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public abstract class AdventureCard extends CardImpl {

    /* The adventure spell card, i.e. Swift End. */
    protected Card spellCard;

    public AdventureCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, CardType[] typesSpell, String costs, String adventureName, String costsSpell) {
        super(ownerId, setInfo, types, costs);
        this.spellCard = new AdventureCardSpellImpl(ownerId, setInfo, adventureName, typesSpell, costsSpell, this);
    }

    public AdventureCard(AdventureCard card) {
        super(card);
        this.spellCard = card.getSpellCard().copy();
        ((AdventureCardSpell) this.spellCard).setParentCard(this);
    }

    public Card getSpellCard() {
        return spellCard;
    }

    @Override
    public void assignNewId() {
        super.assignNewId();
        spellCard.assignNewId();
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, List<UUID> appliedEffects) {
        if (super.moveToZone(toZone, sourceId, game, flag, appliedEffects)) {
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
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, List<UUID> appliedEffects) {
        if (super.moveToExile(exileId, name, sourceId, game, appliedEffects)) {
            Zone currentZone = game.getState().getZone(getId());
            game.getState().setZone(getSpellCard().getId(), currentZone);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFromZone(Game game, Zone fromZone, UUID sourceId) {
        // zone contains only one main card
        return super.removeFromZone(game, fromZone, sourceId);
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
        switch (ability.getSpellAbilityType()) {
            case ADVENTURE_SPELL:
                return this.getSpellCard().cast(game, fromZone, ability, controllerId);
            default:
                this.getSpellCard().getSpellAbility().setControllerId(controllerId);
                return super.cast(game, fromZone, ability, controllerId);
        }
    }

    @Override
    public Abilities<Ability> getAbilities() {
        Abilities<Ability> allAbilities = new AbilitiesImpl<>();
        allAbilities.addAll(spellCard.getAbilities());
        allAbilities.addAll(super.getAbilities());
        return allAbilities;
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
