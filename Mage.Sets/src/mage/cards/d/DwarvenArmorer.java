
package mage.cards.d;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LoneFox
 */
public final class DwarvenArmorer extends CardImpl {

    public DwarvenArmorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {R}, {tap}, Discard a card: Put a +0/+1 counter or a +1/+0 counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DwarvenArmorerEffect(), new ManaCostsImpl("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DwarvenArmorer(final DwarvenArmorer card) {
        super(card);
    }

    @Override
    public DwarvenArmorer copy() {
        return new DwarvenArmorer(this);
    }
}

class DwarvenArmorerEffect extends OneShotEffect {

    private static final Set<String> choices = new HashSet<>();

    static {
        choices.add("+0/+1");
        choices.add("+1/+0");
    }

    public DwarvenArmorerEffect() {
        super(Outcome.Benefit);
        staticText = "Put a +0/+1 counter or a +1/+0 counter on target creature.";
    }

    public DwarvenArmorerEffect(final DwarvenArmorerEffect effect) {
        super(effect);
    }

    @Override
    public DwarvenArmorerEffect copy() {
        return new DwarvenArmorerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose type of counter to add");
            choice.setChoices(choices);
            if (controller.choose(outcome, choice, game)) {
                Counter counter = choice.getChoice().equals("+0/+1") ? CounterType.P0P1.createInstance() : CounterType.P1P0.createInstance();
                Effect effect = new AddCountersTargetEffect(counter);
                effect.setTargetPointer(new FixedTarget(this.getTargetPointer().getFirst(game, source), game));
                return effect.apply(game, source);
            }
        }
        return false;
    }
}
