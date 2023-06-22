
package mage.cards.g;

import java.util.UUID;
import mage.Mana;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 *
 * @author Backfir3
 */
public final class GaeasCradle extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Number of creatures you control", new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED)
    );

    public GaeasCradle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add {G} for each creature you control.
        DynamicManaAbility ability = new DynamicManaAbility(
                Mana.GreenMana(1),
                new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED)
        );
        this.addAbility(ability.addHint(hint));
    }

    private GaeasCradle(final GaeasCradle card) {
        super(card);
    }

    @Override
    public GaeasCradle copy() {
        return new GaeasCradle(this);
    }
}
