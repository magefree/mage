package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

/**
 *
 * @author TheElk801
 */
public final class RalsStaticaster extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Ral planeswalker");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.RAL.getPredicate());
    }

    public RalsStaticaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Ral's Staticaster attacks, if you control a Ral planeswalker, Ral's Staticaster gets +1/+0 for each card in your hand until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new BoostSourceEffect(
                        CardsInControllerHandCount.instance, StaticValue.get(0),
                        Duration.EndOfTurn), false),
                new PermanentsOnTheBattlefieldCondition(filter),
                "Whenever {this} attacks, if you control a Ral planeswalker, "
                + "{this} gets +1/+0 for each card in your hand until end of turn."
        ));
    }

    private RalsStaticaster(final RalsStaticaster card) {
        super(card);
    }

    @Override
    public RalsStaticaster copy() {
        return new RalsStaticaster(this);
    }
}
