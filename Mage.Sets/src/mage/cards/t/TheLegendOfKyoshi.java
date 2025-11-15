package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheLegendOfKyoshi extends CardImpl {

    public TheLegendOfKyoshi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.a.AvatarKyoshi.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Draw cards equal to the greatest power among creatures you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new DrawCardSourceControllerEffect(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES)
        );

        // II -- Earthbend X, where X is the number of cards in your hand. That land becomes an Island in addition to its other types.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new Effects(
                        new EarthbendTargetEffect(CardsInControllerHandCount.ANY),
                        new AddCardSubTypeTargetEffect(SubType.ISLAND, Duration.Custom)
                                .setText("That land becomes an Island in addition to its other types")
                ), new TargetControlledLandPermanent()
        );

        // III -- Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.addAbility(sagaAbility.addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint()));
    }

    private TheLegendOfKyoshi(final TheLegendOfKyoshi card) {
        super(card);
    }

    @Override
    public TheLegendOfKyoshi copy() {
        return new TheLegendOfKyoshi(this);
    }
}
