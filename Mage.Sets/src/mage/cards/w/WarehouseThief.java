package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarehouseThief extends CardImpl {

    public WarehouseThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // {2}, {T}, Sacrifice an artifact or creature: Exile the top card of your library. Until the end of your next turn, you may play that card.
        Ability ability = new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1, false, Duration.UntilEndOfYourNextTurn), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private WarehouseThief(final WarehouseThief card) {
        super(card);
    }

    @Override
    public WarehouseThief copy() {
        return new WarehouseThief(this);
    }
}
