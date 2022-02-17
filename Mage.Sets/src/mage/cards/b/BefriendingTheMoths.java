package mage.cards.b;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BefriendingTheMoths extends CardImpl {

    public BefriendingTheMoths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.i.ImperialMoth.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Target creature you control gets +1/+1 and gains flying until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new Effects(
                        new BoostTargetEffect(1, 1)
                                .setText("target creature you control gets +1/+1"),
                        new GainAbilityTargetEffect(FlyingAbility.getInstance())
                                .setText("and gains flying until end of turn")
                ), new TargetControlledCreaturePermanent()
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private BefriendingTheMoths(final BefriendingTheMoths card) {
        super(card);
    }

    @Override
    public BefriendingTheMoths copy() {
        return new BefriendingTheMoths(this);
    }
}
