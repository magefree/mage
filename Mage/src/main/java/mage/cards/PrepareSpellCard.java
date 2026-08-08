package mage.cards;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.List;
import java.util.UUID;

/*
The inset frame of a preparation card that includes its alternative characteristics. See rule 722, “Preparation Cards.”
*/

/**
 * @author nandmp
 */
public class PrepareSpellCard extends CardImpl implements SpellOptionCard {

    private PrepareCard prepareCardParent;

    public PrepareSpellCard(UUID ownerId, CardSetInfo setInfo, String preparationName, CardType[] cardTypes, String costs, PrepareCard prepareCardParent) {
        super(ownerId, setInfo, cardTypes, costs, SpellAbilityType.PREPARE_SPELL);

        PrepareCardSpellAbility newSpellAbility
                = new PrepareCardSpellAbility(getSpellAbility(), preparationName, cardTypes, costs);
        this.replaceSpellAbility(newSpellAbility);
        spellAbility = newSpellAbility;

        this.setName(preparationName);
        this.prepareCardParent = prepareCardParent;
    }

    protected PrepareSpellCard(final PrepareSpellCard card) {
        super(card);
        this.prepareCardParent = card.prepareCardParent;
    }

    @Override
    public PrepareSpellCard copy() {
        return new PrepareSpellCard(this);
    }

    @Override
    public void finalizeSpell() {
        // Prepare spells need no post-construction ability changes.
    }

    @Override
    public PrepareCard getMainCard() {
        return prepareCardParent;
    }

    @Override
    public void setParentCard(CardWithSpellOption card) {
        this.prepareCardParent = (PrepareCard) card;
    }

    @Override
    public PrepareCard getParentCard() {
        return prepareCardParent;
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        return prepareCardParent.moveToZone(toZone, source, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        return prepareCardParent.moveToExile(exileId, name, source, game, appliedEffects);
    }

    @Override
    public void setZone(Zone zone, Game game) {
        prepareCardParent.setZone(zone, game);
    }

    @Override
    public String getSpellType() {
        return "Prepare";
    }
}

class PrepareCardSpellAbility extends SpellOptionCardSpellAbility {

    PrepareCardSpellAbility(SpellAbility baseSpellAbility, String preparationName,
                            CardType[] cardTypes, String costs) {
        super(baseSpellAbility, "", preparationName, cardTypes, costs, "");
    }

    private PrepareCardSpellAbility(final PrepareCardSpellAbility ability) {
        super(ability);
    }

    @Override
    public PrepareCardSpellAbility copy() {
        return new PrepareCardSpellAbility(this);
    }
}
