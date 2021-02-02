
package mage.cards.p;

import java.util.UUID;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author noxx
 */
public final class PillarOfTheParuns extends CardImpl {

    public PillarOfTheParuns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add one mana of any color. Spend this mana only to cast a multicolored spell.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new ConditionalSpellManaBuilder(StaticFilters.FILTER_SPELL_A_MULTICOLORED)));
    }

    private PillarOfTheParuns(final PillarOfTheParuns card) {
        super(card);
    }

    @Override
    public PillarOfTheParuns copy() {
        return new PillarOfTheParuns(this);
    }
}
