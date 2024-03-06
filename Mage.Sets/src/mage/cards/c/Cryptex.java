package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class Cryptex extends CardImpl {

    public Cryptex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}, Collect evidence 3: Add one mana of any color. Put an unlock counter on Cryptex.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new CollectEvidenceCost(3));
        ability.addEffect(new AddCountersSourceEffect(CounterType.UNLOCK.createInstance()));
        this.addAbility(ability);

        // Sacrifice Cryptex: Surveil 3, then draw three cards. Activate only if Cryptex has five or more unlock counters on it.
        Ability sacAbility = new ConditionalActivatedAbility(new SurveilEffect(3, false), new SacrificeSourceCost(),
                new SourceHasCounterCondition(CounterType.UNLOCK, 5));
        sacAbility.addEffect(new DrawCardSourceControllerEffect(3).concatBy(", then"));
        this.addAbility(sacAbility);

    }

    private Cryptex(final Cryptex card) {
        super(card);
    }

    @Override
    public Cryptex copy() {
        return new Cryptex(this);
    }
}
