package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArrogantPoet extends CardImpl {

    public ArrogantPoet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Arrogant Poet attacks, you may pay 2 life. If you do, it gains flying until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
                new DoIfCostPaid(
                        new GainAbilitySourceEffect(
                                FlyingAbility.getInstance(), Duration.EndOfTurn
                        ).setText("it gains flying until end of turn"),
                        new PayLifeCost(2)
                ), false
        ));
    }

    private ArrogantPoet(final ArrogantPoet card) {
        super(card);
    }

    @Override
    public ArrogantPoet copy() {
        return new ArrogantPoet(this);
    }
}
