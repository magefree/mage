package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrygonPrime extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("other attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public TrygonPrime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Subterranean Assault -- Whenever Trygon Prime attacks, put a +1/+1 counter on it and a +1/+1 counter on up to one other target attacking creature. That creature can't be blocked this turn.
        Ability ability = new AttacksTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on it"));
        ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("and a +1/+1 counter on up to one other target attacking creature"));
        ability.addEffect(new CantBeBlockedTargetEffect(Duration.EndOfTurn)
                .setText("That creature can't be blocked this turn"));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability.withFlavorWord("Subterranean Assault"));
    }

    private TrygonPrime(final TrygonPrime card) {
        super(card);
    }

    @Override
    public TrygonPrime copy() {
        return new TrygonPrime(this);
    }
}
