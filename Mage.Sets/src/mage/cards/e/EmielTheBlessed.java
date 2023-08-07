package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmielTheBlessed extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public EmielTheBlessed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {3}: Exile another target creature you control, then return it to the battlefield under its owner's control.
        Ability ability = new SimpleActivatedAbility(new ExileThenReturnTargetEffect(false, false), new GenericManaCost(3));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Whenever another creature enters the battlefield under your control, you may pay {G/W}. 
        // If you do, put a +1/+1 counter on it. If it's a Unicorn, put two +1/+1 counters on it instead.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new DoIfCostPaid(new EmielTheBlessedEffect(), new ManaCostsImpl<>("{G/W}")),
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, false, SetTargetPointer.PERMANENT,
                "Whenever another creature enters the battlefield under your control, you may pay {G/W}. "
                        + "If you do, put a +1/+1 counter on it. If it's a Unicorn, put two +1/+1 counters on it instead."
        ));
    }

    private EmielTheBlessed(final EmielTheBlessed card) {
        super(card);
    }

    @Override
    public EmielTheBlessed copy() {
        return new EmielTheBlessed(this);
    }
}

class EmielTheBlessedEffect extends OneShotEffect {

    EmielTheBlessedEffect() {
        super(Outcome.Benefit);
    }

    private EmielTheBlessedEffect(final EmielTheBlessedEffect effect) {
        super(effect);
    }

    @Override
    public EmielTheBlessedEffect copy() {
        return new EmielTheBlessedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int counters = permanent.hasSubtype(SubType.UNICORN, game) ? 2 : 1;
        return permanent.addCounters(CounterType.P1P1.createInstance(counters), source.getControllerId(), source, game);
    }
}
