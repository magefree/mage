package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Grath
 */
public final class CaitCageBrawler extends CardImpl {

    public CaitCageBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // During your turn, Cait, Cage Brawler has indestructible.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance()),
                MyTurnCondition.instance, "during your turn, {this} has indestructible"
        )));

        // Whenever Cait attacks, you and defending player each draw a card, then discard a card. Put two +1/+1 counters on Cait if you discarded the card with the highest mana value among those cards or tied for highest.
        this.addAbility(new AttacksTriggeredAbility(new CaitCageBrawlerEffect(), false, null, SetTargetPointer.PLAYER));
    }

    private CaitCageBrawler(final CaitCageBrawler card) {
        super(card);
    }

    @Override
    public CaitCageBrawler copy() {
        return new CaitCageBrawler(this);
    }
}

class CaitCageBrawlerEffect extends OneShotEffect {

    CaitCageBrawlerEffect() {
        super(Outcome.Benefit);
        this.staticText = "you and defending player each draw a card, then discard a card. Put two +1/+1 counters on " +
                "{this} if you discarded the card with the greatest mana value among those cards or tied for greatest";
    }

    protected CaitCageBrawlerEffect(final CaitCageBrawlerEffect effect) {
        super(effect);
    }

    @Override
    public CaitCageBrawlerEffect copy() {
        return new CaitCageBrawlerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null
                || sourceObject == null) {
            return false;
        }

        controller.drawCards(1, source, game);

        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            opponent.drawCards(1, source, game);
        }
        int mvController = Integer.MIN_VALUE;
        Card cardController = null;
        int mvOpponent = Integer.MIN_VALUE;
        Card cardOpponent = null;

        TargetCard controllerTarget = new TargetCard(Zone.HAND, new FilterCard());
        if (controller.choose(Outcome.Discard, controller.getHand(), controllerTarget, source, game)) {
            Card card = controller.getHand().get(controllerTarget.getFirstTarget(), game);
            if (card != null) {
                cardController = card;
                mvController = card.getManaValue();
            }
        }
        TargetCard opponentTarget = new TargetCard(Zone.HAND, new FilterCard());
        if (opponent != null && opponent.choose(Outcome.Discard, opponent.getHand(), opponentTarget, source, game)) {
            Card card = opponent.getHand().get(opponentTarget.getFirstTarget(), game);
            if (card != null) {
                cardOpponent = card;
                mvOpponent = card.getManaValue();
            }
        }

        if (cardOpponent != null) {
            opponent.discard(cardOpponent, false, source, game);
        }
        if (cardController != null) {
            controller.discard(cardController, false, source, game);
            if (mvController > mvOpponent) {
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)).apply(game, source);
            }
        }
        return true;
    }
}
