package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AetherstreamLeopard extends CardImpl {

    public AetherstreamLeopard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Aetherstream Leopard enters the battlefield, you get {E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(1)));

        // Whenever Aetherstream Leopard attacks, you may pay {E}. If you do, it gets +2/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(new BoostSourceEffect(
                2, 0, Duration.EndOfTurn
        ).setText("it gets +2/+0 until end of turn"), new PayEnergyCost(1))));
    }

    private AetherstreamLeopard(final AetherstreamLeopard card) {
        super(card);
    }

    @Override
    public AetherstreamLeopard copy() {
        return new AetherstreamLeopard(this);
    }
}
