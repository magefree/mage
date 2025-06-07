package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Luna Skyrise
 */
public final class Veldt extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.DEPLETION);

    public Veldt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Veldt doesn't untap during your untap step if it has a depletion counter on it.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousRuleModifyingEffect(
                new DontUntapInControllersUntapStepSourceEffect(false, true), condition
        ).setText("{this} doesn't untap during your untap step if it has a depletion counter on it")));

        // At the beginning of your upkeep, remove a depletion counter from Veldt.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.DEPLETION.createInstance())
        ));

        // {tap}: Add {G} or {W}. Put a depletion counter on Veldt.
        Ability ability = new GreenManaAbility();
        ability.addEffect(new AddCountersSourceEffect(CounterType.DEPLETION.createInstance()));
        this.addAbility(ability);
        Ability ability2 = new WhiteManaAbility();
        ability2.addEffect(new AddCountersSourceEffect(CounterType.DEPLETION.createInstance()));
        this.addAbility(ability2);
    }

    private Veldt(final Veldt card) {
        super(card);
    }

    @Override
    public Veldt copy() {
        return new Veldt(this);
    }
}
