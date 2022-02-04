package mage.cards.t;

import java.util.UUID;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TransformAbility;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.SpiritToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class TeachingsOfTheKirin extends CardImpl {

    public TeachingsOfTheKirin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.k.KirinTouchedOrochi.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I - Mill three cards. Create a 1/1 colorless Spirit creature token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I,
                new MillCardsControllerEffect(3),
                new CreateTokenEffect(new SpiritToken())
        );

        // II — Put a +1/+1 counter on target creature you control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new TargetControlledCreaturePermanent()
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private TeachingsOfTheKirin(final TeachingsOfTheKirin card) {
        super(card);
    }

    @Override
    public TeachingsOfTheKirin copy() {
        return new TeachingsOfTheKirin(this);
    }
}
