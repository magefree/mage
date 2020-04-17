package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChitteringHarvester extends CardImpl {

    public ChitteringHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Mutate {4}{B}
        this.addAbility(new MutateAbility(this, "{4}{B}"));

        // Whenever this creature mutates, each opponent sacrifices a creature.
        this.addAbility(new MutatesSourceTriggeredAbility(
                new SacrificeOpponentsEffect(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ));
    }

    private ChitteringHarvester(final ChitteringHarvester card) {
        super(card);
    }

    @Override
    public ChitteringHarvester copy() {
        return new ChitteringHarvester(this);
    }
}
