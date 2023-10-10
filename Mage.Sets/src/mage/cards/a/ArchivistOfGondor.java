package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchivistOfGondor extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(CommanderPredicate.instance);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public ArchivistOfGondor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When your commander deals combat damage to a player, if there is no monarch, you become the monarch.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DealsDamageToAPlayerAllTriggeredAbility(
                        new BecomesMonarchSourceEffect(), filter, false,
                        SetTargetPointer.NONE, true
                ), (game, source) -> game.getMonarchId() == null, "When your commander " +
                "deals combat damage to a player, if there is no monarch, you become the monarch."
        ));

        // At the beginning of the monarch's end step, that player draws a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DrawCardTargetEffect(1), TargetController.MONARCH, false
        ));
    }

    private ArchivistOfGondor(final ArchivistOfGondor card) {
        super(card);
    }

    @Override
    public ArchivistOfGondor copy() {
        return new ArchivistOfGondor(this);
    }
}
