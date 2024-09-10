package mage.cards.a;

import mage.Mana;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
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
 * @author weirddan455
 */
public final class ArniSlaysTheTroll extends CardImpl {

    public ArniSlaysTheTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Target creature you control fights up to one target creature you don't control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                ability -> {
                    ability.addEffect(new FightTargetsEffect().setText(
                            "Target creature you control fights up to one target creature you don't control"
                    ));
                    ability.addTarget(new TargetControlledCreaturePermanent());
                    ability.addTarget(new TargetPermanent(
                            0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL
                    ));
                }
        );

        // II — Add {R}. Put two +1/+1 counters on up to one target creature you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                ability -> {
                    ability.addEffect(new BasicManaEffect(Mana.RedMana(1)));
                    ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
                    ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
                }
        );

        // III — You gain life equal to the greatest power among creatures you control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III,
                new GainLifeEffect(GreatestPowerAmongControlledCreaturesValue.instance,
                        "You gain life equal to the greatest power among creatures you control"
                )
        );
        this.addAbility(sagaAbility);
    }

    private ArniSlaysTheTroll(final ArniSlaysTheTroll card) {
        super(card);
    }

    @Override
    public ArniSlaysTheTroll copy() {
        return new ArniSlaysTheTroll(this);
    }
}
