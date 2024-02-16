package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValiantVeteran extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.SOLDIER, "Soldiers");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent(SubType.SOLDIER, "Soldier you control");

    public ValiantVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Soldiers you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // {3}{W}{W}, Exile Valiant Veteran from your graveyard: Put a +1/+1 counter on each Soldier you control.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(), filter2
                ), new ManaCostsImpl<>("{3}{W}{W}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private ValiantVeteran(final ValiantVeteran card) {
        super(card);
    }

    @Override
    public ValiantVeteran copy() {
        return new ValiantVeteran(this);
    }
}
