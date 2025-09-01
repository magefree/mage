package mage.cards.s;

import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonMagusSisters extends CardImpl {

    public SummonMagusSisters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I,II,III - Choose one at random --
        // * Combine Powers! -- Put three +1/+1 counters on target creature.
        // * Defense! -- Put a shield counter on target creature. You gain 3 life.
        // * Fight! -- This creature fights up to one target creature an opponent controls.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III, ability -> {
            ability.getModes().setRandom(true);
            ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)));
            ability.addTarget(new TargetCreaturePermanent());
            ability.withFirstModeFlavorWord("Combine Powers!");
            ability.addMode(new Mode(new AddCountersTargetEffect(CounterType.SHIELD.createInstance()))
                    .addEffect(new GainLifeEffect(3))
                    .addTarget(new TargetCreaturePermanent())
                    .withFlavorWord("Defense!"));
            ability.addMode(new Mode(new FightTargetSourceEffect())
                    .addTarget(new TargetOpponentsCreaturePermanent(0, 1))
                    .withFlavorWord("Fight!"));
        });
        this.addAbility(sagaAbility);

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private SummonMagusSisters(final SummonMagusSisters card) {
        super(card);
    }

    @Override
    public SummonMagusSisters copy() {
        return new SummonMagusSisters(this);
    }
}
