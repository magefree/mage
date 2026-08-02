package mage.cards.r;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class RollRollRollRoll extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("creature or land you control");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }

    public RollRollRollRoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II, III, IV -- Exile up to one target creature or land you control. If you do, return it to the battlefield under its owner's control at the beginning of the next end step.
        ExileReturnBattlefieldNextEndStepTargetEffect chapterEffect = new ExileReturnBattlefieldNextEndStepTargetEffect();
        chapterEffect.setText("exile up to one target creature or land you control. If you do, " +
            "return it to the battlefield under its owner's control at the beginning of the next end step");
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_IV,
            chapterEffect, new TargetControlledPermanent(0, 1, filter, false)
        );

        this.addAbility(sagaAbility);
    }

    private RollRollRollRoll(final RollRollRollRoll card) {
        super(card);
    }

    @Override
    public RollRollRollRoll copy() {
        return new RollRollRollRoll(this);
    }
}
