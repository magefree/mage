package mage.cards.o;

import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.HalflingToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class OfHerbsAndStewedRabbit extends CardImpl {

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent(SubType.FOOD, "Food you control");

    public OfHerbsAndStewedRabbit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Put a +1/+1 counter on up to one target creature. Create a Food token.
        Effects effect1 = new Effects();
        effect1.add(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        effect1.add(new CreateTokenEffect(new FoodToken()));

        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                effect1, new TargetCreaturePermanent(0,1)
        );

        // II -- Draw a card. Create a Food token.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new DrawCardSourceControllerEffect(1),
                new CreateTokenEffect(new FoodToken())
        );

        // III -- Create a 1/1 white Halfling creature token for each Food you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new CreateTokenEffect(
                        new HalflingToken(),
                        new PermanentsOnBattlefieldCount(filter)
                )
        );

        this.addAbility(sagaAbility);
    }

    private OfHerbsAndStewedRabbit(final OfHerbsAndStewedRabbit card) {
        super(card);
    }

    @Override
    public OfHerbsAndStewedRabbit copy() {
        return new OfHerbsAndStewedRabbit(this);
    }
}
