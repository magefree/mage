package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.permanent.token.SpiritRedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChishiroTheShatteredBlade extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("an Aura or Equipment");
    private static final FilterPermanent filter2 = new FilterControlledPermanent("modified creature you control");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
        filter2.add(ModifiedPredicate.instance);
    }

    public ChishiroTheShatteredBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever an Aura or Equipment enters the battlefield under your control, create a 2/2 red Spirit creature token.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new CreateTokenEffect(new SpiritRedToken()), filter
        ));

        // At the beginning of your end step, put a +1/+1 counter on each modified creature you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), filter2
        ), TargetController.YOU, false));
    }

    private ChishiroTheShatteredBlade(final ChishiroTheShatteredBlade card) {
        super(card);
    }

    @Override
    public ChishiroTheShatteredBlade copy() {
        return new ChishiroTheShatteredBlade(this);
    }
}
