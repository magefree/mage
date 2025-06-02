package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.permanent.token.RabbitToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HareApparent extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("other creatures you control named Hare Apparent");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new NamePredicate("Hare Apparent"));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Other creatures you control named Hare Apparent", xValue);

    public HareApparent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, create a number of 1/1 white Rabbit creature tokens equal to the number of other creatures you control named Hare Apparent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new RabbitToken(), xValue)
                        .setText("create a number of 1/1 white Rabbit creature tokens equal " +
                                "to the number of other creatures you control named Hare Apparent")
        ).addHint(hint));

        // A deck can have any number of cards named Hare Apparent.
        this.getSpellAbility().addEffect(new InfoEffect("a deck can have any number of cards named Hare Apparent"));
    }

    private HareApparent(final HareApparent card) {
        super(card);
    }

    @Override
    public HareApparent copy() {
        return new HareApparent(this);
    }
}
