package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheKamiWar extends CardImpl {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("nonland permanent an opponent controls");
    private static final FilterPermanent filter2
            = new FilterNonlandPermanent("other target nonland permanent");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter2.add(AnotherPredicate.instance);
    }

    public TheKamiWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}{B}{R}{G}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.o.OKagachiMadeManifest.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Exile target nonland permanent an opponent controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new ExileTargetEffect(), new TargetPermanent(filter)
        );

        // II — Return up to one other target nonland permanent to its owner's hand. Then each opponent discards a card.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new Effects(
                        new ReturnToHandTargetEffect(),
                        new DiscardEachPlayerEffect(TargetController.OPPONENT)
                                .concatBy("Then")
                ), new TargetPermanent(0, 1, filter2)
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private TheKamiWar(final TheKamiWar card) {
        super(card);
    }

    @Override
    public TheKamiWar copy() {
        return new TheKamiWar(this);
    }
}
