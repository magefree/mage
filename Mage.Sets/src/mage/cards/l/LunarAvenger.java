
package mage.cards.l;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SunburstAbility;
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
public final class LunarAvenger extends CardImpl {

    public LunarAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // Remove a +1/+1 counter from Lunar Avenger: Lunar Avenger gains your choice of flying, first strike, or haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LunarAvengerEffect(),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1))));
    }

    private LunarAvenger(final LunarAvenger card) {
        super(card);
    }

    @Override
    public LunarAvenger copy() {
        return new LunarAvenger(this);
    }
}

class LunarAvengerEffect extends OneShotEffect {

    private static final Set<String> choices = new LinkedHashSet<>();

    static {
        choices.add("Flying");
        choices.add("First Strike");
        choices.add("Haste");
    }

    public LunarAvengerEffect() {
        super(Outcome.AddAbility);
        staticText = "{this} gains your choice of flying, first strike, or haste until end of turn";
    }

    public LunarAvengerEffect(final LunarAvengerEffect effect) {
        super(effect);
    }

    @Override
    public LunarAvengerEffect copy() {
        return new LunarAvengerEffect(this);
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
            Ability gainedAbility;
            String chosen = choice.getChoice();
            switch (chosen) {
                case "Flying":
                    gainedAbility = FlyingAbility.getInstance();
                    break;
                case "First strike":
                    gainedAbility = FirstStrikeAbility.getInstance();
                    break;
                default:
                    gainedAbility = HasteAbility.getInstance();
                    break;
            }

            game.addEffect(new GainAbilitySourceEffect(gainedAbility, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
