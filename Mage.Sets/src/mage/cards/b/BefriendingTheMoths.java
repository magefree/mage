package mage.cards.b;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BefriendingTheMoths extends TransformingDoubleFacedCard {

    public BefriendingTheMoths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{3}{W}",
                "Imperial Moth",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.INSECT}, "W");

        // Befriending the Moths
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(getLeftHalfCard());

        // I, II — Target creature you control gets +1/+1 and gains flying until end of turn.
        sagaAbility.addChapterEffect(
                getLeftHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new Effects(
                        new BoostTargetEffect(1, 1)
                                .setText("target creature you control gets +1/+1"),
                        new GainAbilityTargetEffect(FlyingAbility.getInstance())
                                .setText("and gains flying until end of turn")
                ), new TargetControlledCreaturePermanent()
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.getLeftHalfCard().addAbility(sagaAbility);

        // Imperial Moth
        this.getRightHalfCard().setPT(2, 4);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());
    }

    private BefriendingTheMoths(final BefriendingTheMoths card) {
        super(card);
    }

    @Override
    public BefriendingTheMoths copy() {
        return new BefriendingTheMoths(this);
    }
}
