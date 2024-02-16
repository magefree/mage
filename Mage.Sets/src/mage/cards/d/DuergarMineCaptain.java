package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class DuergarMineCaptain extends CardImpl {

    public DuergarMineCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {1}{RW}, {untap}: Attacking creatures get +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostAllEffect(1, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false),
                new ManaCostsImpl<>("{1}{R/W}")
        );
        ability.addCost(new UntapSourceCost());
        this.addAbility(ability);

    }

    private DuergarMineCaptain(final DuergarMineCaptain card) {
        super(card);
    }

    @Override
    public DuergarMineCaptain copy() {
        return new DuergarMineCaptain(this);
    }
}
