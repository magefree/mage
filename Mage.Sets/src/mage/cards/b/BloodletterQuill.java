
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PutCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class BloodletterQuill extends CardImpl {

    public BloodletterQuill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}, {T}, Put a blood counter on Bloodletter Quill: Draw a card, then lose 1 life for each blood counter on Bloodletter Quill.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addEffect(new LoseLifeSourceControllerEffect(new CountersSourceCount(CounterType.BLOOD))
                .setText(", then you lose 1 life for each blood counter on {this}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PutCountersSourceCost(CounterType.BLOOD.createInstance()));
        this.addAbility(ability);
        // {U}{B}: Remove a blood counter from Bloodletter Quill.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new RemoveCounterSourceEffect(CounterType.BLOOD.createInstance()), new ManaCostsImpl<>("{U}{B}")));
    }

    private BloodletterQuill(final BloodletterQuill card) {
        super(card);
    }

    @Override
    public BloodletterQuill copy() {
        return new BloodletterQuill(this);
    }
}
