package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class StarshipEnterprise extends CardImpl {

    public StarshipEnterprise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // Landfall -- Whenever a land you control enters, put four charge counters on Starship Enterprise.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(4))));

        // Station
        this.addAbility(new StationAbility());

        // STATION 16+
        // Flying
        // Whenever Starship Enterprise deals combat damage to a player, draw that many cards.
        // 4/6
        this.addAbility(new StationLevelAbility(16)
            .withLevelAbility(FlyingAbility.getInstance())
            .withLevelAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedDamageValue.MANY), false
            ))
            .withPT(4, 6));
    }

    private StarshipEnterprise(final StarshipEnterprise card) {
        super(card);
    }

    @Override
    public StarshipEnterprise copy() {
        return new StarshipEnterprise(this);
    }
}
