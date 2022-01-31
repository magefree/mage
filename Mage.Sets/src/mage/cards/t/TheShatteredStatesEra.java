package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheShatteredStatesEra extends CardImpl {

    public TheShatteredStatesEra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.n.NamelessConqueror.class;

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Gain control of target creature until end of turn. Untap it. It gains haste until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new Effects(
                        new GainControlTargetEffect(Duration.EndOfTurn),
                        new UntapTargetEffect().setText("Untap it."),
                        new GainAbilityTargetEffect(
                                HasteAbility.getInstance(), Duration.EndOfTurn
                        ).setText("It gains haste until end of turn.")
                ), new TargetCreaturePermanent()
        );

        // II — Creatures you control get +1/+0 until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, new BoostControlledEffect(
                        1, 0, Duration.EndOfTurn
                )
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new ExileSagaAndReturnTransformedEffect()
        );

        this.addAbility(sagaAbility);
    }

    private TheShatteredStatesEra(final TheShatteredStatesEra card) {
        super(card);
    }

    @Override
    public TheShatteredStatesEra copy() {
        return new TheShatteredStatesEra(this);
    }
}
