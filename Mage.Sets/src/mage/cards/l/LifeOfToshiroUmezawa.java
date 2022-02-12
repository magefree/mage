package mage.cards.l;

import mage.abilities.Mode;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.target.Targets;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LifeOfToshiroUmezawa extends CardImpl {

    public LifeOfToshiroUmezawa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.m.MemoryOfToshiro.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Choose one —
        // • Target creature gets +2/+2 until end of turn.
        // • Target creature gets -1/-1 until end of turn.
        // • You gain 2 life.
        Mode mode = new Mode(new BoostTargetEffect(-1, -1));
        mode.addTarget(new TargetCreaturePermanent());
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new Effects(new BoostTargetEffect(2, 2)),
                new Targets(new TargetCreaturePermanent()), false,
                mode, new Mode(new GainLifeEffect(2))
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private LifeOfToshiroUmezawa(final LifeOfToshiroUmezawa card) {
        super(card);
    }

    @Override
    public LifeOfToshiroUmezawa copy() {
        return new LifeOfToshiroUmezawa(this);
    }
}
