package mage.cards;

import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 * @author phulin
 */
public abstract class AdventureCard extends CardWithSpellOption {

    public AdventureCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, CardType[] typesSpell, String costs, String adventureName, String costsSpell) {
        super(ownerId, setInfo, types, costs);
        this.spellCard = new AdventureSpellCard(ownerId, setInfo, adventureName, typesSpell, costsSpell, this);
    }

    public AdventureCard(AdventureCard card) {
        super(card);
    }

    public void finalizeAdventure() {
        spellCard.finalizeSpell();
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        if (ability.getSpellAbilityType() == SpellAbilityType.ADVENTURE_SPELL) {
            return this.getSpellCard().cast(game, fromZone, ability, controllerId);
        }
        this.getSpellCard().getSpellAbility().setControllerId(controllerId);
        return super.cast(game, fromZone, ability, controllerId);
    }
}
