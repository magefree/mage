
package mage.cards.w;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public final class WildResearch extends CardImpl {

    private static final FilterCard filterEnchantment = new FilterCard("enchantment card");
    private static final FilterCard filterInstant = new FilterCard("instant card");

    static {
        filterEnchantment.add(CardType.ENCHANTMENT.getPredicate());
        filterInstant.add(CardType.INSTANT.getPredicate());
    }

    public WildResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // {1}{W}: Search your library for an enchantment card and reveal that card. Put it into your hand, then discard a card at random. Then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WildResearchEffect(filterEnchantment), new ManaCostsImpl<>("{1}{W}")));

        // {1}{U}: Search your library for an instant card and reveal that card. Put it into your hand, then discard a card at random. Then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WildResearchEffect(filterInstant), new ManaCostsImpl<>("{1}{U}")));

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
        this.staticText = "Search your library for an " + filter.getMessage() + " and reveal that card. Put it into your hand, then discard a card at random. Then shuffle.";
        this.filter = filter;
    }

    WildResearchEffect(final WildResearchEffect effect) {
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
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    Card card = controller.getLibrary().remove(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                        Cards cards = new CardsImpl(card);
                        controller.revealCards(sourceObject.getIdName(), cards, game, true);
                    }
                }
            }
            controller.discardOne(true, false, source, game);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
