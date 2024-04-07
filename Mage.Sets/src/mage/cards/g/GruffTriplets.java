package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GruffTriplets extends CardImpl {

    private static final FilterPermanent filterNonToken = new FilterPermanent("non-token permanent");
    private static final FilterControlledPermanent filterNamedGruffTriplets = new FilterControlledPermanent("creature you control named Gruff Triplets");

    static {
        filterNonToken.add(TokenPredicate.FALSE);
        filterNamedGruffTriplets.add(new NamePredicate("Gruff Triplets"));
    }

    public GruffTriplets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");

        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Gruff Triplets enters the battlefield, if it isn't a token, create two tokens that are copies of it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenCopySourceEffect(2)),
                new SourceMatchesFilterCondition(filterNonToken),
                "When {this} enters the battlefield, if it isn't a token, create two tokens that are copies of it."
        ));

        // When Gruff Triplets dies, put a number of +1/+1 counters equal to its power on each creature you control named Gruff Triplets.
        this.addAbility(new DiesSourceTriggeredAbility(
                new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(),
                        new SourcePermanentPowerCount(),
                        filterNamedGruffTriplets
                ).setText("put a number of +1/+1 counters equal to its power on each creature you control named Gruff Triplets.")
        ));
    }

    private GruffTriplets(final GruffTriplets card) {
        super(card);
    }

    @Override
    public GruffTriplets copy() {
        return new GruffTriplets(this);
    }
}
