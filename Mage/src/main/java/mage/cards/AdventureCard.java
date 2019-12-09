package mage.cards;

import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ExileAdventureSpellEffect;
import mage.abilities.keyword.AdventureCreatureAbility;
import mage.constants.*;
import mage.game.Game;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public abstract class AdventureCard extends CardImpl {

    /* The adventure spell card, i.e. Swift End. */
    protected Card spellCard;
    /* The ability to cast the creature from exile. */
    protected SpellAbility adventureCreatureAbility;

    public AdventureCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, CardType[] typesSpell, String costs, String adventureName, String costsSpell) {
        super(ownerId, setInfo, types, costs);
        spellCard = new AdventureCardSpellImpl(ownerId, setInfo, typesSpell, costsSpell, this);
        spellCard.getSpellAbility().addEffect(ExileAdventureSpellEffect.getInstance());
        adventureCreatureAbility = new AdventureCreatureAbility(new ManaCostsImpl(costs));
    }

    public AdventureCard(AdventureCard card) {
        super(card);
        this.spellCard = card.getSpellCard().copy();
        ((AdventureCardSpell)this.spellCard).setParentCard(this);
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
    public Abilities<Ability> getAbilities(Game game) {
        Abilities<Ability> allAbilities = new AbilitiesImpl<>();
        for (Ability ability : super.getAbilities(game)) {
            if (ability instanceof SpellAbility
                    && ((SpellAbility) ability).getSpellAbilityType() != SpellAbilityType.SPLIT
                    && ((SpellAbility) ability).getSpellAbilityType() != SpellAbilityType.SPLIT_AFTERMATH) {
                allAbilities.add(ability);
            }
        }
        allAbilities.addAll(spellCard.getAbilities(game));
        return allAbilities;
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        super.setOwnerId(ownerId);
        abilities.setControllerId(ownerId);
        spellCard.getAbilities().setControllerId(ownerId);
        spellCard.setOwnerId(ownerId);
    }
}
