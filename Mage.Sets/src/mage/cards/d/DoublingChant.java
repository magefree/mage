package mage.cards.d;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardWithSameNameAsPermanents;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author North
 */
public final class DoublingChant extends CardImpl {

    public DoublingChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // For each creature you control, you may search your library for a creature card with the same name as that creature.
        // Put those cards onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new DoublingChantEffect());
    }

    private DoublingChant(final DoublingChant card) {
        super(card);
    }

    @Override
    public DoublingChant copy() {
        return new DoublingChant(this);
    }
}

class DoublingChantEffect extends OneShotEffect {

    DoublingChantEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "For each creature you control, " +
                "you may search your library for a creature card with the same name as that creature. " +
                "Put those cards onto the battlefield, then shuffle";
    }

    private DoublingChantEffect(final DoublingChantEffect effect) {
        super(effect);
    }

    @Override
    public DoublingChantEffect copy() {
        return new DoublingChantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<UUID> set = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .map(MageItem::getId)
                .collect(Collectors.toSet());
        TargetCardInLibrary target = new TargetCardWithSameNameAsPermanents(set);
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        player.moveCards(cards.getCards(game), Zone.BATTLEFIELD, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
