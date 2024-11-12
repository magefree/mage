package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NazarTheVelvetFang extends CardImpl {

    public NazarTheVelvetFang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever you gain life, put a feeding counter on Nazar, the Velvet Fang.
        this.addAbility(new GainLifeControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.FEEDING.createInstance())));

        // Whenever Nazar attacks, you may remove three feeding counters from it. If you do, you draw three cards and you lose 3 life.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(3, true),
                new RemoveCountersSourceCost(CounterType.FEEDING.createInstance(3))
                        .setText("remove three feeding counters from it")
        ).addEffect(new LoseLifeSourceControllerEffect(3).concatBy("and"))));
    }

    private NazarTheVelvetFang(final NazarTheVelvetFang card) {
        super(card);
    }

    @Override
    public NazarTheVelvetFang copy() {
        return new NazarTheVelvetFang(this);
    }
}
