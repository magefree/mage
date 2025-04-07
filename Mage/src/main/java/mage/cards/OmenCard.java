package mage.cards;

import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

public abstract class OmenCard extends CardWithSpellOption {

    public OmenCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, CardType[] typesSpell, String costs, String omenName, String costsSpell) {
        super(ownerId, setInfo, types, costs);
        this.spellCard = new OmenSpellCard(ownerId, setInfo, omenName, typesSpell, costsSpell, this);
    }

    public OmenCard(OmenCard card) {
        super(card);
    }

    public void finalizeOmen() {
        spellCard.finalizeSpell();
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        if (ability.getSpellAbilityType() == SpellAbilityType.OMEN_SPELL) {
            return this.getSpellCard().cast(game, fromZone, ability, controllerId);
        }
        this.getSpellCard().getSpellAbility().setControllerId(controllerId);
        return super.cast(game, fromZone, ability, controllerId);
    }
}
