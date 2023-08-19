package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HedronAlignment extends CardImpl {

    public HedronAlignment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // At the beginning of your upkeep, you may reveal your hand. If you do, you win the game if you own a card named Hedron Alignment in exile, in your hand, in your graveyard, and on the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new HedronAlignmentEffect(), TargetController.YOU, true));
        // {1}{U}: Scry 1.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1, false), new ManaCostsImpl<>("{1}{U}")));
    }

    private HedronAlignment(final HedronAlignment card) {
        super(card);
    }

    @Override
    public HedronAlignment copy() {
        return new HedronAlignment(this);
    }
}

class HedronAlignmentEffect extends OneShotEffect {

    private static final FilterPermanent filterPermanent = new FilterPermanent();
    private static final FilterCard filterCard = new FilterCard();

    static {
        filterPermanent.add(new NamePredicate("Hedron Alignment"));
        filterPermanent.add(TargetController.YOU.getOwnerPredicate());
        filterCard.add(new NamePredicate("Hedron Alignment"));
        filterCard.add(TargetController.YOU.getOwnerPredicate());
    }

    public HedronAlignmentEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may reveal your hand. If you do, you win the game if you own a card named Hedron Alignment in exile, in your hand, in your graveyard, and on the battlefield";
    }

    public HedronAlignmentEffect(final HedronAlignmentEffect effect) {
        super(effect);
    }

    @Override
    public HedronAlignmentEffect copy() {
        return new HedronAlignmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Cards cardsToReveal = new CardsImpl();
            controller.revealCards(sourceObject.getIdName(), cardsToReveal, game);
            // Check battlefield
            if (!game.getBattlefield().contains(filterPermanent, source, game, 1)) {
                return true;
            }
            if (controller.getHand().getCards(filterCard, controller.getId(), source, game).isEmpty()) {
                return true;
            }
            if (controller.getGraveyard().getCards(filterCard, controller.getId(), source, game).isEmpty()) {
                return true;
            }
            Cards cardsToCheck = new CardsImpl();
            cardsToCheck.addAllCards(game.getExile().getAllCards(game));
            if (cardsToCheck.count(filterCard, controller.getId(), source, game) == 0) {
                return true;
            }
            controller.won(game);
            return true;

        }
        return false;
    }
}
