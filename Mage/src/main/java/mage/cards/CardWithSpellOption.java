package mage.cards;

import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author phulin, jmlundeen
 */
public abstract class CardWithSpellOption extends CardImpl {

    /* The adventure/omen spell card, i.e. Swift End. */
    protected SpellOptionCard spellCard;

    public CardWithSpellOption(UUID ownerId, CardSetInfo setInfo, CardType[] types, String costs) {
        super(ownerId, setInfo, types, costs);
    }

    public CardWithSpellOption(CardWithSpellOption card) {
        super(card);
        // make sure all parts created and parent ref added
        this.spellCard = card.getSpellCard().copy();
        this.spellCard.setParentCard(this);
    }

    public SpellOptionCard getSpellCard() {
        return spellCard;
    }

    public void setParts(SpellOptionCard cardSpell) {
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
            Zone currentZone = game.getState().getZone(getId());
            game.getState().setZone(getSpellCard().getId(), currentZone);
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
        // abilities without spellCard
        return super.getAbilities(game);
    }

    public List<String> getSharedRules(Game game) {
        // rules without spellCard
        Abilities<Ability> sourceAbilities = this.getSharedAbilities(game);
        return CardUtil.getCardRulesWithAdditionalInfo(game, this, sourceAbilities, sourceAbilities);
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        super.setOwnerId(ownerId);
        abilities.setControllerId(ownerId);
        spellCard.getAbilities().setControllerId(ownerId);
        spellCard.setOwnerId(ownerId);
    }
}
