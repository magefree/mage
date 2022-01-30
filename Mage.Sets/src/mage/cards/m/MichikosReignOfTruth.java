package mage.cards.m;

import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MichikosReignOfTruth extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            StaticFilters.FILTER_PERMANENT_CONTROLLED_ARTIFACT_OR_ENCHANTMENT
    );

    public MichikosReignOfTruth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.p.PortraitOfMichiko.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_I);

        // I, II — Target creature gets +1/+1 until end of turn for each artifact and/or enchantment you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn)
                        .setText("target creature gets +1/+1 until end of turn " +
                                "for each artifact and/or enchantment you control"),
                new TargetCreaturePermanent()
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private MichikosReignOfTruth(final MichikosReignOfTruth card) {
        super(card);
    }

    @Override
    public MichikosReignOfTruth copy() {
        return new MichikosReignOfTruth(this);
    }
}
