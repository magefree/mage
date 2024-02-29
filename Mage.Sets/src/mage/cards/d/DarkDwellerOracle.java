package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
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
public final class DarkDwellerOracle extends CardImpl {

    public DarkDwellerOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}, Sacrifice a creature: Exile the top card of your library. You may play that card this turn.
        Ability ability = new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn).withTextOptions("that card", true),
                new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private DarkDwellerOracle(final DarkDwellerOracle card) {
        super(card);
    }

    @Override
    public DarkDwellerOracle copy() {
        return new DarkDwellerOracle(this);
    }
}
