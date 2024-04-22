

package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class ContagionClasp extends CardImpl {

    public ContagionClasp (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // When Contagion Clasp enters the battlefield, put a -1/-1 counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance(), Outcome.UnboostCreature), false);
        TargetCreaturePermanent target = new TargetCreaturePermanent();
        ability.addTarget(target);
        this.addAbility(ability);

        /* {4}, {T}: Proliferate. (You choose any number of permanents and/or players
         * with counters on them, then give each another counter of a kind already there.) */
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ProliferateEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ContagionClasp(final ContagionClasp card) {
        super(card);
    }

    @Override
    public ContagionClasp copy() {
        return new ContagionClasp(this);
    }

}
