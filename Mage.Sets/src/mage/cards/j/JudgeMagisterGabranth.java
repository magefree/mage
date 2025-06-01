package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JudgeMagisterGabranth extends CardImpl {

    public JudgeMagisterGabranth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever another creature or artifact you control dies, put a +1/+1 counter on Judge Magister Gabranth.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT
        ));
    }

    private JudgeMagisterGabranth(final JudgeMagisterGabranth card) {
        super(card);
    }

    @Override
    public JudgeMagisterGabranth copy() {
        return new JudgeMagisterGabranth(this);
    }
}
