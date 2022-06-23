package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.RollDieEvent;
import mage.game.permanent.token.BrainiacToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class TheBigIdea extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.BRAINIAC, "Brainiac creatures");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public TheBigIdea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BRAINIAC);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}{B/R}{B/R}, {T}: Roll a six-sided dice. Create a number of 1/1 red Brainiac creature tokens equal to the result.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TheBigIdeaEffect(), new ManaCostsImpl<>("{2}{B/R}{B/R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Tap three untapped Brainiacs you control: The next time you would roll a six-sided die, instead roll two six-sided dice and use the total of those results.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TheBigIdeaReplacementEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(3, 3, filter, true))));
    }

    private TheBigIdea(final TheBigIdea card) {
        super(card);
    }

    @Override
    public TheBigIdea copy() {
        return new TheBigIdea(this);
    }
}

class TheBigIdeaReplacementEffect extends ReplacementEffectImpl {

    TheBigIdeaReplacementEffect() {
        super(Duration.OneUse, Outcome.Damage);
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
        ((RollDieEvent) event).incBigIdeaRollsAmount();
        discard();
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DIE
                && ((RollDieEvent) event).getRollDieType() == RollDieType.NUMERICAL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !this.used && source.isControlledBy(event.getPlayerId()) && ((RollDieEvent) event).getSides() == 6;
    }
}

class TheBigIdeaEffect extends OneShotEffect {

    public TheBigIdeaEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Roll a six-sided die. Create a number of 1/1 red Brainiac creature tokens equal to the result";
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
        if (controller == null) {
            return false;
        }
        int amount = controller.rollDice(outcome, source, game, 6);
        return new BrainiacToken().putOntoBattlefield(amount, game, source, source.getControllerId());
    }
}
