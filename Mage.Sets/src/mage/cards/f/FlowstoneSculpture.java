
package mage.cards.f;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public final class FlowstoneSculpture extends CardImpl {

    public FlowstoneSculpture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}, Discard a card: Put a +1/+1 counter on Flowstone Sculpture or Flowstone Sculpture gains flying, first strike, or trample.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new FlowstoneSculptureEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private FlowstoneSculpture(final FlowstoneSculpture card) {
        super(card);
    }

    @Override
    public FlowstoneSculpture copy() {
        return new FlowstoneSculpture(this);
    }
}

class FlowstoneSculptureEffect extends OneShotEffect {

    private static final Set<String> choices = new HashSet<>();

    static {
        choices.add("+1/+1 counter");
        choices.add("Flying");
        choices.add("First Strike");
        choices.add("Trample");
    }

    public FlowstoneSculptureEffect() {
        super(Outcome.Benefit);
        staticText = "Put a +1/+1 counter on {this} or {this} gains flying, first strike, or trample.";
    }

    public FlowstoneSculptureEffect(final FlowstoneSculptureEffect effect) {
        super(effect);
    }

    @Override
    public FlowstoneSculptureEffect copy() {
        return new FlowstoneSculptureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose ability to add");
            choice.setChoices(choices);
            if (!controller.choose(outcome, choice, game)) {
                return false;
            }

            String chosen = choice.getChoice();
            if (chosen.equals("+1/+1 counter")) {
                return new AddCountersSourceEffect(CounterType.P1P1.createInstance()).apply(game, source);
            } else {
                Ability gainedAbility;
                switch (chosen) {
                    case "Flying":
                        gainedAbility = FlyingAbility.getInstance();
                        break;
                    case "First strike":
                        gainedAbility = FirstStrikeAbility.getInstance();
                        break;
                    default:
                        gainedAbility = TrampleAbility.getInstance();
                        break;
                }
                game.addEffect(new GainAbilitySourceEffect(gainedAbility, Duration.WhileOnBattlefield), source);
                return true;
            }

        }
        return false;
    }
}
