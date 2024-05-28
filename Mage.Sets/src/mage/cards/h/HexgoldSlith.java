package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HexgoldSlith extends CardImpl {

    public HexgoldSlith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SLITH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Hexgold Slith enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // Whenever Hexgold Slith attacks, you may pay {E}. If you do, it gains first strike until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn)
                        .setText("it gains first strike until end of turn"),
                new PayEnergyCost(1)
        )));

        // Whenever Hexgold Slith deals combat damage to a player, put a +1/+1 counter on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), false
        ));
    }

    private HexgoldSlith(final HexgoldSlith card) {
        super(card);
    }

    @Override
    public HexgoldSlith copy() {
        return new HexgoldSlith(this);
    }
}
