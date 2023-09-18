package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class WildResearch extends CardImpl {

    private static final FilterCard filterEnchantment = new FilterEnchantmentCard();
    private static final FilterCard filterInstant = new FilterCard("instant card");

    static {
        filterInstant.add(CardType.INSTANT.getPredicate());
    }

    public WildResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // {1}{W}: Search your library for an enchantment card and reveal that card. Put it into your hand, then discard a card at random. Then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(
                new WildResearchEffect(filterEnchantment), new ManaCostsImpl<>("{1}{W}")
        ));

        // {1}{U}: Search your library for an instant card and reveal that card. Put it into your hand, then discard a card at random. Then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(
                new WildResearchEffect(filterInstant), new ManaCostsImpl<>("{1}{U}")
        ));
    }

    private WildResearch(final WildResearch card) {
        super(card);
    }

    @Override
    public WildResearch copy() {
        return new WildResearch(this);
    }
}

class WildResearchEffect extends OneShotEffect {

    protected final FilterCard filter;

    WildResearchEffect(FilterCard filter) {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for an " + filter.getMessage() + " and reveal that card. " +
                "Put it into your hand, then discard a card at random. Then shuffle.";
        this.filter = filter;
    }

    private WildResearchEffect(final WildResearchEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public WildResearchEffect copy() {
        return new WildResearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        controller.searchLibrary(target, source, game);
        Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            controller.revealCards(source, new CardsImpl(card), game);
            controller.moveCards(card, Zone.HAND, source, game);
        }
        controller.discardOne(true, false, source, game);
        controller.shuffleLibrary(source, game);
        return true;
    }
}
