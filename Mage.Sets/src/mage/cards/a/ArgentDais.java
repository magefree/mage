package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardTargetControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ArgentDais extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("another target nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ArgentDais(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        // Argent Dais enters the battlefield with two oil counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance(2)),
                "with two oil counters on it"
        ));

        // Whenever two or more creatures attack, put an oil counter on Argent Dais.
        this.addAbility(new ArgentDaisTriggeredAbility());

        // {2}, {T}, Remove two oil counters from Argent Dais: Exile another target nonland permanent. Its controller draws two cards.
        Ability ability = new SimpleActivatedAbility(
                new ExileTargetEffect(),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.OIL.createInstance(2)));
        ability.addTarget(new TargetNonlandPermanent(filter));
        ability.addEffect(new DrawCardTargetControllerEffect(2));
        this.addAbility(ability);
    }

    private ArgentDais(final ArgentDais card) {
        super(card);
    }

    @Override
    public ArgentDais copy() {
        return new ArgentDais(this);
    }
}

class ArgentDaisTriggeredAbility extends TriggeredAbilityImpl {

    ArgentDaisTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.OIL.createInstance()));
        setTriggerPhrase("Whenever two or more creatures attack, ");
    }

    private ArgentDaisTriggeredAbility(final ArgentDaisTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArgentDaisTriggeredAbility copy() {
        return new ArgentDaisTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= 2;
    }
}
