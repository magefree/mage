package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.GrismoldPlantToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrismoldTheDreadsower extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public GrismoldTheDreadsower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your end step, each player creates a 1/1 green Plant creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenAllEffect(new GrismoldPlantToken(), TargetController.EACH_PLAYER),
                TargetController.YOU, false
        ));

        // Whenever a creature token dies, put a +1/+1 counter on Grismold, the Dreadsower.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, filter
        ));
    }

    private GrismoldTheDreadsower(final GrismoldTheDreadsower card) {
        super(card);
    }

    @Override
    public GrismoldTheDreadsower copy() {
        return new GrismoldTheDreadsower(this);
    }
}
