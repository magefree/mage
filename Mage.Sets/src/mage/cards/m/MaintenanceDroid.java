
package mage.cards.m;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Styxo
 */
public final class MaintenanceDroid extends CardImpl {

    private static final FilterCard filter = new FilterCard("target card you own with a repair counter on it");

    static {
        filter.add(CounterType.REPAIR.getPredicate());
    }

    public MaintenanceDroid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}{U}");
        this.subtype.add(SubType.DROID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Choose target card you own with a repair counter on it. You may remove a repair counter from it or put another repair counter on it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MaintenanceDroidEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Repair 4
        this.addAbility(new RepairAbility(4));
    }

    private MaintenanceDroid(final MaintenanceDroid card) {
        super(card);
    }

    @Override
    public MaintenanceDroid copy() {
        return new MaintenanceDroid(this);
    }
}

class MaintenanceDroidEffect extends OneShotEffect {

    private static final Set<String> choices = new LinkedHashSet<>();

    static {
        choices.add("Remove a repair counter");
        choices.add("Add another repair counter");
    }

    public MaintenanceDroidEffect() {
        super(Outcome.BoostCreature);
        staticText = "Choose target card you own with a repair counter on it. You may remove a repair counter from it or put another repair counter on it";
    }

    public MaintenanceDroidEffect(final MaintenanceDroidEffect effect) {
        super(effect);
    }

    @Override
    public MaintenanceDroidEffect copy() {
        return new MaintenanceDroidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose mode");
            choice.setChoices(choices);
            if (!controller.choose(outcome, choice, game)) {
                return false;
            }

            String chosen = choice.getChoice();
            switch (chosen) {
                case "Remove a repair counter":
                    new RemoveCounterTargetEffect(CounterType.REPAIR.createInstance()).apply(game, source);
                    break;
                default: //"Add another repair counter"
                    new AddCountersTargetEffect(CounterType.REPAIR.createInstance()).apply(game, source);
                    break;
            }
            return true;

        }

        return false;
    }

}
