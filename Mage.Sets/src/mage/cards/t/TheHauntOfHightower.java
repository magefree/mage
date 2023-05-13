package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheHauntOfHightower extends CardImpl {

    public TheHauntOfHightower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever The Haunt of Hightower attacks, defending player discards a card.
        this.addAbility(new AttacksTriggeredAbility(
                new DiscardTargetEffect(1), false,
                "Whenever {this} attacks, defending player discards a card", SetTargetPointer.PLAYER
        ));

        // Whenever a card is put into an opponent's graveyard from anywhere, put a +1/+1 counter on The Haunt of Hightower.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, TargetController.OPPONENT
        ));
    }

    private TheHauntOfHightower(final TheHauntOfHightower card) {
        super(card);
    }

    @Override
    public TheHauntOfHightower copy() {
        return new TheHauntOfHightower(this);
    }
}
