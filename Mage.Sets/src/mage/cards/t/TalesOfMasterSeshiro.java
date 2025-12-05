package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TalesOfMasterSeshiro extends TransformingDoubleFacedCard {

    public TalesOfMasterSeshiro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{4}{G}",
                "Seshiro's Living Legacy",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.SNAKE, SubType.WARRIOR}, "G"
        );

        // Tales of Master Seshiro
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I, II — Put a +1/+1 counter on target creature or Vehicle you control. It gains vigilance until end of turn.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new Effects(
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                        new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                                .setText("It gains vigilance until end of turn")
                ), new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_CREATURE_OR_VEHICLE)
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Seshiro's Living Legacy
        this.getRightHalfCard().setPT(5, 5);

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());
    }

    private TalesOfMasterSeshiro(final TalesOfMasterSeshiro card) {
        super(card);
    }

    @Override
    public TalesOfMasterSeshiro copy() {
        return new TalesOfMasterSeshiro(this);
    }
}
