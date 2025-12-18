package mage.cards.t;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainControlAllOwnedEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheFallOfLordKonda extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("creature an opponent controls with mana value 4 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
    }

    public TheFallOfLordKonda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{2}{W}",
                "Fragment of Konda",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.NOBLE}, "W"
        );

        // The Fall of Lord Konda
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I — Exile target creature an opponent controls with mana value 4 or greater.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new ExileTargetEffect(), new TargetPermanent(filter)
        );

        // II — Each player gains control of all permanents they own.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new GainControlAllOwnedEffect(StaticFilters.FILTER_PERMANENTS)
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_III,
                new ExileSagaAndReturnTransformedEffect()
        );

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Fragment of Konda
        this.getRightHalfCard().setPT(1, 3);

        // Defender
        this.getRightHalfCard().addAbility(DefenderAbility.getInstance());

        // When Fragment of Konda dies, draw a card.
        this.getRightHalfCard().addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private TheFallOfLordKonda(final TheFallOfLordKonda card) {
        super(card);
    }

    @Override
    public TheFallOfLordKonda copy() {
        return new TheFallOfLordKonda(this);
    }
}
