package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class JazalGoldmane extends CardImpl {

    public JazalGoldmane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // {3}{W}{W}: Attacking creatures you control get +X/+X until end of turn, where X is the number of attacking creatures.
        DynamicValue xValue = new AttackingCreatureCount("the number of attacking creatures");
        this.addAbility(new SimpleActivatedAbility(
                new BoostControlledEffect(xValue, xValue, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false, true),
                new ManaCostsImpl("{3}{W}{W}")));
    }

    private JazalGoldmane(final JazalGoldmane card) {
        super(card);
    }

    @Override
    public JazalGoldmane copy() {
        return new JazalGoldmane(this);
    }
}
