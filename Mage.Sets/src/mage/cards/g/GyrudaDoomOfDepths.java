package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValueParityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GyrudaDoomOfDepths extends CardImpl {

    public GyrudaDoomOfDepths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U/B}{U/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Companion — Your starting deck contains only cards with even converted mana costs.
        this.addAbility(new CompanionAbility(GyrudaDoomOfDepthsCompanionCondition.instance));

        // When Gyruda, Doom of Depths enters the battlefield, each player puts the top four cards of the library into their graveyard. Put a creature card with an even converted mana cost from among those cards onto the battlefield under your control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GyrudaDoomOfDepthsEffect()));
    }

    private GyrudaDoomOfDepths(final GyrudaDoomOfDepths card) {
        super(card);
    }

    @Override
    public GyrudaDoomOfDepths copy() {
        return new GyrudaDoomOfDepths(this);
    }
}

enum GyrudaDoomOfDepthsCompanionCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "Your starting deck contains only cards with even mana values.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int minimumDeckSize) {
        return deck
                .stream()
                .mapToInt(MageObject::getManaValue)
                .map(i -> i % 2)
                .allMatch(i -> i == 0);
    }
}

class GyrudaDoomOfDepthsEffect extends OneShotEffect {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with an even mana value");

    static {
        filter.add(ManaValueParityPredicate.EVEN);
    }

    GyrudaDoomOfDepthsEffect() {
        super(Outcome.Benefit);
        staticText = "each player mills four cards. Put a creature card with an even mana value "
                + "from among the milled cards onto the battlefield under your control";
    }

    private GyrudaDoomOfDepthsEffect(final GyrudaDoomOfDepthsEffect effect) {
        super(effect);
    }

    @Override
    public GyrudaDoomOfDepthsEffect copy() {
        return new GyrudaDoomOfDepthsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            cards.addAll(player.millCards(4, source, game));
        }
        /*
        If a replacement effect causes a player to exile the top four cards of their library 
        instead of putting them into their graveyard as Gyruda’s triggered ability resolves, 
        the creature card you choose may be one of those cards in exile. (2020-04-17)
         */
        if (cards.isEmpty()) {
            return true;
        }
        // the creature card chosen can be in any zone, not just the graveyard
        TargetCard targetCard = new TargetCard(0, 1, Zone.ALL, filter);
        targetCard.setNotTarget(true);
        controller.choose(outcome, cards, targetCard, source, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        return card != null
                && controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
