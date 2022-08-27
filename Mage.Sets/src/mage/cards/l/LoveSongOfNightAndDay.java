package mage.cards.l;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.BirdToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoveSongOfNightAndDay extends CardImpl {

    public LoveSongOfNightAndDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.SAGA);

        // Read ahead
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III, true);

        // I -- You and target opponent each draw two cards.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, new DrawCardSourceControllerEffect(2).setText("you"),
                new DrawCardSourceControllerEffect(2).setText("and target opponent each draw two cards")
        );

        // II -- Create a 1/1 white Bird creature token with flying.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new CreateTokenEffect(new BirdToken()));

        // III -- Put a +1/+1 counter on each of up to two target creatures.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new TargetCreaturePermanent(0, 2)
        );
        this.addAbility(sagaAbility);
    }

    private LoveSongOfNightAndDay(final LoveSongOfNightAndDay card) {
        super(card);
    }

    @Override
    public LoveSongOfNightAndDay copy() {
        return new LoveSongOfNightAndDay(this);
    }
}
