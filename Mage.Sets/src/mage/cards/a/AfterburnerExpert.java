package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AfterburnerExpert extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("an exhaust ability");

    static {
        filter.add(AfterburnerExpertPredicate.instance);
    }

    public AfterburnerExpert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Exhaust -- {2}{G}{G}: Put two +1/+1 counters on this creature.
        this.addAbility(new ExhaustAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new ManaCostsImpl<>("{2}{G}{G}")
        ));

        // Whenever you activate an exhaust ability, return this card from your graveyard to the battlefield.
        this.addAbility(new ActivateAbilityTriggeredAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(), filter, SetTargetPointer.NONE
        ));
    }

    private AfterburnerExpert(final AfterburnerExpert card) {
        super(card);
    }

    @Override
    public AfterburnerExpert copy() {
        return new AfterburnerExpert(this);
    }
}

enum AfterburnerExpertPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input.getStackAbility() instanceof ExhaustAbility;
    }
}
