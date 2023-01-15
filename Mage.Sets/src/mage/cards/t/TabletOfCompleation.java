package mage.cards.t;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 * @author TheElk801
 */
public final class TabletOfCompleation extends CardImpl {

    private static final Condition condition1 = new SourceHasCounterCondition(CounterType.OIL, 2);
    private static final Condition condition2 = new SourceHasCounterCondition(CounterType.OIL, 5);

    public TabletOfCompleation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}: Put an oil counter on Tablet of Compleation.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance()), new TapSourceCost()
        ));

        // {T}: Add {C}. Activate only if Tablet of Compleation has two or more oil counters on it.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new BasicManaEffect(new Mana(ManaType.COLORLESS)), new TapSourceCost(), condition1
        ));

        // {1}, {T}: Draw a card. Activate only if Tablet of Compleation has five or more oil counters on it.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                new GenericManaCost(1), condition2
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TabletOfCompleation(final TabletOfCompleation card) {
        super(card);
    }

    @Override
    public TabletOfCompleation copy() {
        return new TabletOfCompleation(this);
    }
}
