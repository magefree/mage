package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class HauntingEchoes extends CardImpl {

    public HauntingEchoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        this.getSpellAbility().addEffect(new HauntingEchoesEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private HauntingEchoes(final HauntingEchoes card) {
        super(card);
    }

    @Override
    public HauntingEchoes copy() {
        return new HauntingEchoes(this);
    }
}

class HauntingEchoesEffect extends OneShotEffect {

    HauntingEchoesEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all cards from target player's graveyard other than basic land cards. " +
                "For each card exiled this way, search that player's library for all cards " +
                "with the same name as that card and exile them. Then that player shuffles";
    }

    private HauntingEchoesEffect(final HauntingEchoesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        player.getGraveyard()
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> !card.isBasic(game) || !card.isLand(game))
                .forEach(cards::add);
        controller.moveCards(cards, Zone.EXILED, source, game);
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.EXILED);
        FilterCard filter = new FilterCard("cards with the same name as a card exiled this way");
        filter.add(new HauntingEchoesPredicate(cards));
        TargetCardInLibrary target = new TargetCardInLibrary(0, Integer.MAX_VALUE, filter);
        controller.searchLibrary(target, source, game, player.getId());
        cards.clear();
        cards.addAll(target.getTargets());
        controller.moveCards(cards, Zone.EXILED, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public HauntingEchoesEffect copy() {
        return new HauntingEchoesEffect(this);
    }
}

class HauntingEchoesPredicate implements Predicate<Card> {
    private final Cards cards = new CardsImpl();

    HauntingEchoesPredicate(Cards cards) {
        this.cards.addAll(cards);
    }

    @Override
    public boolean apply(Card input, Game game) {
        return cards.getCards(game).stream().anyMatch(card -> CardUtil.haveSameNames(input, card));
    }
}
