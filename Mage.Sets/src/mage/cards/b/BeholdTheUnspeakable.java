package mage.cards.b;

import mage.abilities.common.SagaAbility;
import mage.abilities.condition.common.HeckbentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeholdTheUnspeakable extends CardImpl {

    public BeholdTheUnspeakable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.v.VisionOfTheUnspeakable.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Creatures you don't control get -2/-0 until your next turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new BoostAllEffect(
                -2, 0, Duration.UntilYourNextTurn,
                StaticFilters.FILTER_CREATURES_YOU_DONT_CONTROL, false
        ));

        // II — If you have one or fewer cards in hand, draw four cards. Otherwise, scry 2, then draw two cards.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(4), new ScryEffect(2),
                HeckbentCondition.instance, "if you have one or fewer cards in hand, " +
                "draw four cards. Otherwise, scry 2, then draw two cards"
        ).addOtherwiseEffect(new DrawCardSourceControllerEffect(2)));

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private BeholdTheUnspeakable(final BeholdTheUnspeakable card) {
        super(card);
    }

    @Override
    public BeholdTheUnspeakable copy() {
        return new BeholdTheUnspeakable(this);
    }
}
