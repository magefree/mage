
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author North, Loki, noxx
 */
public final class SphereOfTheSuns extends CardImpl {

    private static final String ruleText = "{this} enters the battlefield tapped and with three charge counters on it.";

    public SphereOfTheSuns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Sphere of the Suns enters the battlefield tapped and with three charge counters on it.
        Ability ability = new EntersBattlefieldTappedAbility(ruleText);
        ((EntersBattlefieldEffect) ability.getEffects().get(0)).addEffect(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(3)));
        this.addAbility(ability);

        RemoveCountersSourceCost removeCounterCost = new RemoveCountersSourceCost(CounterType.CHARGE.createInstance());
        ability = new AnyColorManaAbility();
        ability.addCost(removeCounterCost);
        this.addAbility(ability);
    }

    private SphereOfTheSuns(final SphereOfTheSuns card) {
        super(card);
    }

    @Override
    public SphereOfTheSuns copy() {
        return new SphereOfTheSuns(this);
    }
}
