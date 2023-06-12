package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public final class MercadianLift extends CardImpl {

    public MercadianLift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {1}, {tap}: Put a winch counter on Mercadian Lift.
        Ability ability = new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.WINCH.createInstance()), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {tap}, Remove X winch counters from Mercadian Lift: You may put a creature card with converted mana cost X from your hand onto the battlefield.
        Ability ability2 = new SimpleActivatedAbility(new MercadianLiftEffect(), new TapSourceCost());
        ability2.addCost(new RemoveVariableCountersSourceCost(CounterType.WINCH));
        this.addAbility(ability2);

    }

    private MercadianLift(final MercadianLift card) {
        super(card);
    }

    @Override
    public MercadianLift copy() {
        return new MercadianLift(this);
    }
}

class MercadianLiftEffect extends OneShotEffect {

    public MercadianLiftEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "You may put a creature card with mana value X from your hand onto the battlefield";
    }

    public MercadianLiftEffect(final MercadianLiftEffect effect) {
        super(effect);
    }

    @Override
    public MercadianLiftEffect copy() {
        return new MercadianLiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int numberOfCounters = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof RemoveVariableCountersSourceCost) {
                    numberOfCounters = ((RemoveVariableCountersSourceCost) cost).getAmount();
                }
            }
            System.out.println("The number is " + numberOfCounters);
            FilterCreatureCard filter = new FilterCreatureCard();
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, numberOfCounters));
            filter.setMessage("creature card with mana value " + numberOfCounters);
            TargetCardInHand target = new TargetCardInHand(filter);
            if (target.canChoose(controller.getId(), source, game)
                    && controller.chooseUse(Outcome.PutCardInPlay, "Put " + filter.getMessage() + " from your hand onto the battlefield?", source, game)
                    && controller.choose(Outcome.PutCardInPlay, target, source, game)) {
                target.setRequired(false);
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
        }
        return false;
    }
}
