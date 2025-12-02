package mage.cards.m;

import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
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
public final class MichikosReignOfTruth extends TransformingDoubleFacedCard {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            StaticFilters.FILTER_PERMANENT_CONTROLLED_ARTIFACT_OR_ENCHANTMENT
    );

    public MichikosReignOfTruth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{1}{W}",
                "Portrait of Michiko",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.NOBLE}, "W"
        );

        // Michiko's Reign of Truth
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I, II — Target creature gets +1/+1 until end of turn for each artifact and/or enchantment you control.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn)
                        .setText("target creature gets +1/+1 until end of turn " +
                                "for each artifact and/or enchantment you control"),
                new TargetCreaturePermanent()
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.getLeftHalfCard().addAbility(sagaAbility);

        // Portrait of Michiko
        this.getRightHalfCard().setPT(0, 0);

        // Portrait of Michiko gets +1/+1 for each artifact and/or enchantment you control.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)
                .setText("{this} gets +1/+1 for each artifact and/or enchantment you control"))
        );
    }

    private MichikosReignOfTruth(final MichikosReignOfTruth card) {
        super(card);
    }

    @Override
    public MichikosReignOfTruth copy() {
        return new MichikosReignOfTruth(this);
    }
}
