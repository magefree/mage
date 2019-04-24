
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BrainiacToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class TheBigIdea extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.BRAINIAC, "Brainiac creatures");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public TheBigIdea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BRAINIAC);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // 2{BR}{BR}, T: Roll a six-sided dice. Create a number of 1/1 red Brainiac creature tokens equal to the result. 
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TheBigIdeaEffect(), new ManaCostsImpl("{2}{B/R}{B/R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Tap three untapped Brainiacs you control: The next time you would roll a six-sided die, instead roll two six-sided dice and use the total of those results.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TheBigIdeaReplacementEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(3, 3, filter, true))));
    }

    public TheBigIdea(final TheBigIdea card) {
        super(card);
    }

    @Override
    public TheBigIdea copy() {
        return new TheBigIdea(this);
    }
}

class TheBigIdeaReplacementEffect extends ReplacementEffectImpl {

    TheBigIdeaReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "The next time you would roll a six-sided die, instead roll two six-sided dice and use the total of those results";
    }

    TheBigIdeaReplacementEffect(final TheBigIdeaReplacementEffect effect) {
        super(effect);
    }

    @Override
    public TheBigIdeaReplacementEffect copy() {
        return new TheBigIdeaReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (event.getData() != null) {
            String data = event.getData();
            int numSides = Integer.parseInt(data);
            if (numSides != 6) {
                return false;
            }
        }

        if (controller != null) {
            discard();
            int amount = controller.rollDice(game, 6);
            event.setAmount(event.getAmount() + amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DICE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used) {
            return source.isControlledBy(event.getPlayerId());
        }
        return false;
    }
}

class TheBigIdeaEffect extends OneShotEffect {

    public TheBigIdeaEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Roll a six-sided dice. Create a number of 1/1 red Brainiac creature tokens equal to the result";
    }

    public TheBigIdeaEffect(final TheBigIdeaEffect effect) {
        super(effect);
    }

    @Override
    public TheBigIdeaEffect copy() {
        return new TheBigIdeaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(game, 6);
            return new CreateTokenEffect(new BrainiacToken(), amount).apply(game, source);
        }
        return false;
    }
}
