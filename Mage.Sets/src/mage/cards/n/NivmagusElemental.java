
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromStackCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class NivmagusElemental extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public NivmagusElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U/R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Exile an instant or sorcery spell you control: Put two +1/+1 counters on Nivmagus Elemental. (That spell won't resolve.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new ExileFromStackCost(new TargetSpell(filter)));
        this.addAbility(ability);

    }

    public NivmagusElemental(final NivmagusElemental card) {
        super(card);
    }

    @Override
    public NivmagusElemental copy() {
        return new NivmagusElemental(this);
    }
}
