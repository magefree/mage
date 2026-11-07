package mage.cards;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.ExileVengeanceSpellEffect;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.List;
import java.util.UUID;

public class VengeanceSpellCard extends CardImpl implements SpellOptionCard {

    private VengeanceCard vengeanceCardParent;

    public VengeanceSpellCard(UUID ownerId, CardSetInfo setInfo, String vengeanceName, CardType[] cardTypes, String costs, VengeanceCard vengeanceCardParent) {
        super(ownerId, setInfo, cardTypes, costs, SpellAbilityType.VENGEANCE_SPELL);
        this.subtype.add(SubType.VENGEANCE);

        // the spell is cast from the graveyard, not from hand
        SpellAbility graveyardAbility = (SpellAbility) getSpellAbility().copyWithZone(Zone.GRAVEYARD);
        VengeanceCardSpellAbility newSpellAbility = new VengeanceCardSpellAbility(graveyardAbility, vengeanceName, cardTypes, costs);
        this.replaceSpellAbility(newSpellAbility);
        spellAbility = newSpellAbility;

        this.setName(vengeanceName);
        this.vengeanceCardParent = vengeanceCardParent;
    }

    protected VengeanceSpellCard(final VengeanceSpellCard card) {
        super(card);
        this.vengeanceCardParent = card.vengeanceCardParent;
    }

    @Override
    public void finalizeSpell() {
        if (spellAbility instanceof VengeanceCardSpellAbility) {
            ((VengeanceCardSpellAbility) spellAbility).finalizeVengeance();
        }
    }

    @Override
    public UUID getOwnerId() {
        return vengeanceCardParent.getOwnerId();
    }

    @Override
    public String getExpansionSetCode() {
        return vengeanceCardParent.getExpansionSetCode();
    }

    @Override
    public String getCardNumber() {
        return vengeanceCardParent.getCardNumber();
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        return vengeanceCardParent.moveToZone(toZone, source, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        return vengeanceCardParent.moveToExile(exileId, name, source, game, appliedEffects);
    }

    @Override
    public VengeanceCard getMainCard() {
        return vengeanceCardParent;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        game.setZone(vengeanceCardParent.getId(), zone);
        game.setZone(vengeanceCardParent.getSpellCard().getId(), zone);
    }

    @Override
    public VengeanceSpellCard copy() {
        return new VengeanceSpellCard(this);
    }

    @Override
    public void setParentCard(CardWithSpellOption card) {
        this.vengeanceCardParent = (VengeanceCard) card;
    }

    @Override
    public VengeanceCard getParentCard() {
        return this.vengeanceCardParent;
    }

    @Override
    public String getIdName() {
        // id must send to main card (popup card hint in game logs)
        return getName() + " [" + vengeanceCardParent.getId().toString().substring(0, 3) + ']';
    }

    @Override
    public String getSpellType() {
        return "Vengeance";
    }
}

class VengeanceCardSpellAbility extends SpellOptionCardSpellAbility {

    private boolean finalized = false;

    public VengeanceCardSpellAbility(final SpellAbility baseSpellAbility, String vengeanceName, CardType[] cardTypes, String costs) {
        super(baseSpellAbility, "Vengeance", vengeanceName, cardTypes, costs,
                " <i>(Cast only from your graveyard. Then exile it.)</i>");
    }

    // The exile effect needs to be added last.
    public void finalizeVengeance() {
        if (finalized) {
            throw new IllegalStateException("Wrong code usage. "
                    + "Vengeance (" + cardName + ") "
                    + "need to call finalizeVengeance() exactly once.");
        }
        this.addEffect(ExileVengeanceSpellEffect.getInstance());
        this.finalized = true;
    }

    protected VengeanceCardSpellAbility(final VengeanceCardSpellAbility ability) {
        super(ability);
        if (!ability.finalized) {
            throw new IllegalStateException("Wrong code usage. "
                    + "Vengeance (" + cardName + ") "
                    + "need to call finalizeVengeance() at the very end of the card's constructor.");
        }
        this.finalized = true;
    }

    @Override
    public VengeanceCardSpellAbility copy() {
        return new VengeanceCardSpellAbility(this);
    }
}
