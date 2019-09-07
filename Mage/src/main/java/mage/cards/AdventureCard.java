package mage.cards;

import mage.abilities.SpellAbility;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public abstract class AdventureCard extends CardImpl {

    protected SpellAbility adventureSpellAbility = new SpellAbility(null, null);

    public AdventureCard(UUID ownerId, CardSetInfo setInfo, CardType[] typesLeft, CardType[] typesRight, String costsLeft, String adventureName, String costsRight) {
        super(ownerId, setInfo, typesLeft, costsLeft);
    }

    public AdventureCard(AdventureCard card) {
        super(card);
    }

    public SpellAbility getAdventureSpellAbility() {
        return adventureSpellAbility;
    }
}
