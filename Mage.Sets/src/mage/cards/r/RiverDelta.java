package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Luna Skyrise
 */
public final class RiverDelta extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.DEPLETION);

    public RiverDelta(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // River Delta doesn't untap during your untap step if it has a depletion counter on it.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousRuleModifyingEffect(
                new DontUntapInControllersUntapStepSourceEffect(false, true), condition
        ).setText("{this} doesn't untap during your untap step if it has a depletion counter on it")));

        // At the beginning of your upkeep, remove a depletion counter from River Delta.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.DEPLETION.createInstance())
        ));

        // {tap}: Add {U} or {B}. Put a depletion counter on River Delta.
        Ability ability = new BlueManaAbility();
        ability.addEffect(new AddCountersSourceEffect(CounterType.DEPLETION.createInstance()));
        this.addAbility(ability);
        Ability ability2 = new BlackManaAbility();
        ability2.addEffect(new AddCountersSourceEffect(CounterType.DEPLETION.createInstance()));
        this.addAbility(ability2);
    }

    private RiverDelta(final RiverDelta card) {
        super(card);
    }

    @Override
    public RiverDelta copy() {
        return new RiverDelta(this);
    }
}
