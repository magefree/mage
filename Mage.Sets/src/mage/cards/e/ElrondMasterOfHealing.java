package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetScryAmount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElrondMasterOfHealing extends CardImpl {

    public ElrondMasterOfHealing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you scry, put a +1/+1 counter on each of up to X target creatures, where X is the number of cards looked at while scrying this way.
        Ability ability = new ScryTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on each of up to X target creatures, " +
                        "where X is the number of cards looked at while scrying this way"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.setTargetAdjuster(new TargetsCountAdjuster(GetScryAmount.instance));
        this.addAbility(ability);

        // Whenever a creature you control with a +1/+1 counter on it becomes the target of a spell or ability an opponent controls, you may draw a card.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_A_CONTROLLED_CREATURE_P1P1, StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS, SetTargetPointer.NONE, true
        ));
    }

    private ElrondMasterOfHealing(final ElrondMasterOfHealing card) {
        super(card);
    }

    @Override
    public ElrondMasterOfHealing copy() {
        return new ElrondMasterOfHealing(this);
    }
}
