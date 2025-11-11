package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class DaiLiCensor extends CardImpl {

    public DaiLiCensor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {1}, Sacrifice another creature: This creature gets +2/+2 until end of turn. Activate only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private DaiLiCensor(final DaiLiCensor card) {
        super(card);
    }

    @Override
    public DaiLiCensor copy() {
        return new DaiLiCensor(this);
    }
}
