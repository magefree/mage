
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class OrderOfTheGoldenCricket extends CardImpl {

    public OrderOfTheGoldenCricket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Order of the Golden Cricket attacks, you may pay {W}. If you do, it gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(new DoIfCostPaid(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{W}")),
                false,
                "Whenever {this} attacks, you may pay {W}. If you do, it gains flying until end of turn.");
        this.addAbility(ability);
    }

    private OrderOfTheGoldenCricket(final OrderOfTheGoldenCricket card) {
        super(card);
    }

    @Override
    public OrderOfTheGoldenCricket copy() {
        return new OrderOfTheGoldenCricket(this);
    }
}