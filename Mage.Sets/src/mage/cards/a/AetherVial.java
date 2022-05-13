
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author North
 */
public final class AetherVial extends CardImpl {

    public AetherVial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // At the beginning of your upkeep, you may put a charge counter on Aether Vial.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(), true), TargetController.YOU, true));
        // {tap}: You may put a creature card with converted mana cost equal to the number of charge counters on Aether Vial from your hand onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AetherVialEffect(), new TapSourceCost()));
    }

    private AetherVial(final AetherVial card) {
        super(card);
    }

    @Override
    public AetherVial copy() {
        return new AetherVial(this);
    }
}

class AetherVialEffect extends OneShotEffect {

    public AetherVialEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "You may put a creature card with mana value equal to the number of charge counters on {this} from your hand onto the battlefield";
    }

    public AetherVialEffect(final AetherVialEffect effect) {
        super(effect);
    }

    @Override
    public AetherVialEffect copy() {
        return new AetherVialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            if (permanent == null) {
                return false;
            }
        }
        int count = permanent.getCounters(game).getCount(CounterType.CHARGE);

        FilterCreatureCard filter = new FilterCreatureCard("creature card with mana value equal to " + count);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, count));
        String choiceText = "Put a " + filter.getMessage() + " from your hand onto the battlefield?";

        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.getHand().count(filter, game) == 0
                || !controller.chooseUse(this.outcome, choiceText, source, game)) {
            return true;
        }

        TargetCardInHand target = new TargetCardInHand(filter);
        if (controller.choose(this.outcome, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
        }
        return false;
    }
}
