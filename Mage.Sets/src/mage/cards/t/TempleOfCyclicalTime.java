package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TimingRule;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TempleOfCyclicalTime extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.TIME, ComparisonType.EQUAL_TO, 0);

    public TempleOfCyclicalTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.nightCard = true;

        // (Transforms from Ojer Pakpatiq, Deepest Epoch.)

        // {T}: Add {U}. Remove a time counter from Temple of Cyclical Time.
        Ability ability = new BlueManaAbility();
        ability.addEffect(new RemoveCounterSourceEffect(CounterType.TIME.createInstance()));
        this.addAbility(ability);

        // {2}{U}, {T}: Transform Temple of Cyclical Time. Activate only if it has no time counters on it and only as a sorcery.
        ability = new ActivateIfConditionActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{2}{U}"), condition
        ).setTiming(TimingRule.SORCERY);
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TempleOfCyclicalTime(final TempleOfCyclicalTime card) {
        super(card);
    }

    @Override
    public TempleOfCyclicalTime copy() {
        return new TempleOfCyclicalTime(this);
    }
}
