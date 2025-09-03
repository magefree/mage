package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SupportiveParents extends CardImpl {

    public SupportiveParents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tap two untapped creatures you control: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new TapTargetCost(
                2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES
        )));
    }

    private SupportiveParents(final SupportiveParents card) {
        super(card);
    }

    @Override
    public SupportiveParents copy() {
        return new SupportiveParents(this);
    }
}
