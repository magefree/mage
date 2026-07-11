package mage.cards;

import mage.constants.CardType;

import java.util.UUID;

/**
 * A prepare card: a permanent that has an associated "prepare spell" (its alternative
 * characteristics, a {@link PrepareSpellCard}). While the permanent is prepared, its controller may
 * cast a copy of the prepare spell (CR 722). Casting the copy does not move the permanent; it only
 * becomes unprepared.
 * <p>
 * Card implementations add the prepare spell's effects and targets to
 * {@code getSpellCard().getSpellAbility()}.
 *
 * @author TheElk801
 */
public abstract class PrepareCard extends CardImpl {

    protected PrepareSpellCard spellCard;

    protected PrepareCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, String costs, String preparationName, CardType typeSpell, String costsSpell) {
        this(ownerId, setInfo, types, costs, preparationName, new CardType[]{typeSpell}, costsSpell);
    }

    protected PrepareCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, String costs, String preparationName, CardType[] typesSpell, String costsSpell) {
        super(ownerId, setInfo, types, costs);
        this.spellCard = new PrepareSpellCard(ownerId, setInfo, preparationName, typesSpell, costsSpell, this);
    }

    protected PrepareCard(final PrepareCard card) {
        super(card);
        this.spellCard = card.spellCard.copy();
    }

    public PrepareSpellCard getSpellCard() {
        return spellCard;
    }
}
