package mage.cards.b;

import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HeckbentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeholdTheUnspeakable extends TransformingDoubleFacedCard {

    public BeholdTheUnspeakable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{3}{U}{U}",
                "Vision of the Unspeakable",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "U");

        // Behold the Unspeakable
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(getLeftHalfCard());

        // I — Creatures you don't control get -2/-0 until your next turn.
        sagaAbility.addChapterEffect(getLeftHalfCard(), SagaChapter.CHAPTER_I, new BoostAllEffect(
                -2, 0, Duration.UntilYourNextTurn,
                StaticFilters.FILTER_CREATURES_YOU_DONT_CONTROL, false
        ));

        // II — If you have one or fewer cards in hand, draw four cards. Otherwise, scry 2, then draw two cards.
        sagaAbility.addChapterEffect(getLeftHalfCard(), SagaChapter.CHAPTER_II, new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(4), new ScryEffect(2),
                HeckbentCondition.instance, "if you have one or fewer cards in hand, " +
                "draw four cards. Otherwise, scry 2, then draw two cards"
        ).addOtherwiseEffect(new DrawCardSourceControllerEffect(2)));

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.getLeftHalfCard().addAbility(sagaAbility);

        // Vision of the Unspeakable
        this.getRightHalfCard().setPT(0, 0);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Vision of the Unspeakable gets +1/+1 for each card in your hand.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                CardsInControllerHandCount.ANY_SINGULAR,
                CardsInControllerHandCount.ANY_SINGULAR,
                Duration.WhileOnBattlefield
        )));
    }

    private BeholdTheUnspeakable(final BeholdTheUnspeakable card) {
        super(card);
    }

    @Override
    public BeholdTheUnspeakable copy() {
        return new BeholdTheUnspeakable(this);
    }
}
