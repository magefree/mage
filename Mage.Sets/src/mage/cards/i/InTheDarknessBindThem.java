package mage.cards.i;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.game.permanent.token.WraithToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachOpponentTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class InTheDarknessBindThem extends CardImpl {

    public InTheDarknessBindThem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{B}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II, III-- Create a 3/3 black Wraith creature token with menace. The Ring tempts you.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III,
                new Effects(
                        new CreateTokenEffect(new WraithToken()),
                        new TheRingTemptsYouEffect()
                )
        );

        // IV-- For each opponent, gain control of up to one target creature that player controls until end of turn. Untap those creatures. They gain haste until end of turn. The Ring tempts you.

        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                ability -> {
                    ability.addEffect(new GainControlTargetEffect(Duration.EndOfTurn)
                            .setText("For each opponent, gain control of up to one target creature that player controls until end of turn."));
                    ability.addEffect(new UntapTargetEffect()
                            .setText("Untap those creatures."));
                    ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                            .setText("They gain haste until end of turn."));
                    ability.addEffect(new TheRingTemptsYouEffect());

                    ability.getEffects().setTargetPointer(new EachTargetPointer());
                    ability.addTarget(new TargetCreaturePermanent(0,1));
                    ability.setTargetAdjuster(new ForEachOpponentTargetsAdjuster());
                }
        );

        this.addAbility(sagaAbility);
    }

    private InTheDarknessBindThem(final InTheDarknessBindThem card) {
        super(card);
    }

    @Override
    public InTheDarknessBindThem copy() {
        return new InTheDarknessBindThem(this);
    }
}
