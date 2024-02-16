package mage.cards.f;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FallOfGilGalad extends CardImpl {

    public FallOfGilGalad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Scry 2.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new ScryEffect(2, false)
        );

        // II -- Put two +1/+1 counters on target creature you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)),
                new TargetControlledCreaturePermanent()
        );

        // III -- Until end of turn, target creature you control gains "When this creature dies, draw two cards." Then that creature fights up to one other target creature.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new GainAbilityTargetEffect(
                    new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(2))
                            .setTriggerPhrase("When this creature dies, ")
            ).setText("until end of turn, target creature you control gains \"When this creature dies, draw two cards.\""));
            ability.addEffect(new FightTargetsEffect().setText("Then that creature fights up to one other target creature"));
            ability.addTarget(new TargetControlledCreaturePermanent());
            ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).setTargetTag(2));
        });

        this.addAbility(sagaAbility);
    }

    private FallOfGilGalad(final FallOfGilGalad card) {
        super(card);
    }

    @Override
    public FallOfGilGalad copy() {
        return new FallOfGilGalad(this);
    }
}
