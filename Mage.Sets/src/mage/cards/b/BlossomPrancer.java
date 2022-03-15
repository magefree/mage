package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlossomPrancer extends CardImpl {

    public BlossomPrancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Blossom Prancer enters the battlefield, look at the top five cards of your library. You may reveal a creature or enchantment card from among them and put it into your hand. Put the rest on the bottom of your library in a random order. If you didn't put a card into your hand this way, you gain 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BlossomPrancerEffect()));
    }

    private BlossomPrancer(final BlossomPrancer card) {
        super(card);
    }

    @Override
    public BlossomPrancer copy() {
        return new BlossomPrancer(this);
    }
}

class BlossomPrancerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature or enchantment card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    BlossomPrancerEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top five cards of your library. You may reveal a creature or enchantment card from " +
                "among them and put it into your hand. Put the rest on the bottom of your library in a random order. " +
                "If you didn't put a card into your hand this way, you gain 4 life";
    }

    private BlossomPrancerEffect(final BlossomPrancerEffect effect) {
        super(effect);
    }

    @Override
    public BlossomPrancerEffect copy() {
        return new BlossomPrancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
        TargetCard target = new TargetCardInLibrary(0, 1, filter);
        player.choose(outcome, cards, target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.revealCards(source, new CardsImpl(card), game);
            player.moveCards(card, Zone.HAND, source, game);
            cards.remove(card);
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
        } else {
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
            player.gainLife(4, game, source);
        }
        return true;
    }
}
