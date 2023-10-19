package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 *
 * @author LevelX2, xenohedron
 */
public final class RealitySmasher extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell an opponent controls");
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public RealitySmasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{C}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Whenever Reality Smasher becomes the target of a spell an opponent controls, counter that spell unless its controller discards a card.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(
                new CounterUnlessPaysEffect(new DiscardCardCost()).setText("counter that spell unless its controller discards a card"),
                filter, SetTargetPointer.SPELL, false
        ));
    }

    private RealitySmasher(final RealitySmasher card) {
        super(card);
    }

    @Override
    public RealitySmasher copy() {
        return new RealitySmasher(this);
    }
}
