package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class EmpyrealVoyager extends CardImpl {

    public EmpyrealVoyager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Empyreal Voyager deals combat damage to a player you get that many {E}
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new GetEnergyCountersControllerEffect(SavedDamageValue.MANY)
                .setText("you get that many {E} <i>(energy counters)</i>."),
                false, true));
    }

    private EmpyrealVoyager(final EmpyrealVoyager card) {
        super(card);
    }

    @Override
    public EmpyrealVoyager copy() {
        return new EmpyrealVoyager(this);
    }
}
