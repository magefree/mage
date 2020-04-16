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
import mage.filter.predicate.mageobject.ConvertedManaCostParityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GyrudaDoomOfDepths extends CardImpl {

    public GyrudaDoomOfDepths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U/B}{U/B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Companion — Your starting deck contains only cards with even converted mana costs.
        this.addAbility(new CompanionAbility(OboshThePreypiercerCompanionCondition.instance));

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

enum OboshThePreypiercerCompanionCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "Your starting deck contains only cards with odd converted mana costs and land cards.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int startingSize) {
        return deck
                .stream()
                .filter(card -> !card.isLand())
                .mapToInt(MageObject::getConvertedManaCost)
                .map(i -> i % 2)
                .allMatch(i -> i == 0);
    }
}

class GyrudaDoomOfDepthsEffect extends OneShotEffect {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with an even converted mana cost");

    static {
        filter.add(ConvertedManaCostParityPredicate.EVEN);
    }

    GyrudaDoomOfDepthsEffect() {
        super(Outcome.Benefit);
        staticText = "each player puts the top four cards of the library into their graveyard. " +
                "Put a creature card with an even converted mana cost from among those cards " +
                "onto the battlefield under your control.";
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
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .map(Player::getLibrary)
                .map(library -> library.getTopCards(game, 4))
                .flatMap(Collection::stream)
                .forEach(cards::add);
        controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        cards.removeIf(cardId -> game.getState().getZone(cardId) != Zone.GRAVEYARD);
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard targetCard = new TargetCardInGraveyard(0, 1, filter);
        targetCard.setNotTarget(true);
        controller.choose(outcome, cards, targetCard, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        return card != null && controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
