
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class GemOfBecoming extends CardImpl {

    public GemOfBecoming(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {3}, {tap}, Sacrifice Gem of Becoming: Search your library for an Island card, a Swamp card, and a Mountain card.
        // Reveal those cards and put them into your hand. Then shuffle your library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GemOfBecomingEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public GemOfBecoming(final GemOfBecoming card) {
        super(card);
    }

    @Override
    public GemOfBecoming copy() {
        return new GemOfBecoming(this);
    }
}

class GemOfBecomingEffect extends OneShotEffect {

    public GemOfBecomingEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for an Island card, a Swamp card, and a Mountain card. Reveal those cards and put them into your hand. Then shuffle your library";
    }

    public GemOfBecomingEffect(final GemOfBecomingEffect effect) {
        super(effect);
    }

    @Override
    public GemOfBecomingEffect copy() {
        return new GemOfBecomingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl();

        searchLand(player, source, game, cards, "Island");
        searchLand(player, source, game, cards, "Swamp");
        searchLand(player, source, game, cards, "Mountain");

        player.revealCards("Gem of Becoming", cards, game);
        player.shuffleLibrary(source, game);

        return false;
    }

    private void searchLand(Player player, Ability source, Game game, Cards cards, String subtype) {
        FilterLandCard filter = new FilterLandCard(subtype);
        filter.add(new SubtypePredicate(SubType.byDescription(subtype)));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player.searchLibrary(target, source, game)) {
            Card card = player.getLibrary().remove(target.getFirstTarget(), game);
            if (card != null) {
                card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                cards.add(card);
            }
        }
    }
}
