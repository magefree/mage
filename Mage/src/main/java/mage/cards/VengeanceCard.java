package mage.cards;

import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.abilities.SpellAbility;
import mage.game.Game;

import java.util.UUID;

/**
 * A card with a Vengeance spell inset in its lower left corner.
 * The Vengeance spell can only be cast from its owner's graveyard, and the whole card is exiled on resolution.
 */
public abstract class VengeanceCard extends CardWithSpellOption {

    public VengeanceCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, String costs, String vengeanceName, CardType[] typesSpell, String costsSpell) {
        super(ownerId, setInfo, types, costs);
        this.spellCard = new VengeanceSpellCard(ownerId, setInfo, vengeanceName, typesSpell, costsSpell, this);
    }

    public VengeanceCard(VengeanceCard card) {
        super(card);
    }

    public void finalizeVengeance() {
        spellCard.finalizeSpell();
    }

    @Override
    public boolean isSpellCardCastOptionAvailable(Game game) {
        return game.getState().getZone(getId()) == Zone.GRAVEYARD;
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        if (ability.getSpellAbilityType() == SpellAbilityType.VENGEANCE_SPELL) {
            return this.getSpellCard().cast(game, fromZone, ability, controllerId);
        }
        this.getSpellCard().getSpellAbility().setControllerId(controllerId);
        return super.cast(game, fromZone, ability, controllerId);
    }
}
