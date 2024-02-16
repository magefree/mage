package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class DiffusionSliver extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.SLIVER, "a Sliver creature you control");

    public DiffusionSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a Sliver creature you control becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays {2}.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(new CounterUnlessPaysEffect(new GenericManaCost(2))
                .setText("counter that spell or ability unless its controller pays {2}"),
                filter, StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS, SetTargetPointer.SPELL, false));
    }

    private DiffusionSliver(final DiffusionSliver card) {
        super(card);
    }

    @Override
    public DiffusionSliver copy() {
        return new DiffusionSliver(this);
    }
}
