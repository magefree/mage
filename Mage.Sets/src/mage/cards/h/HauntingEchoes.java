
package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class HauntingEchoes extends CardImpl {

    public HauntingEchoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new HauntingEchoesEffect());
    }

    public HauntingEchoes(final HauntingEchoes card) {
        super(card);
    }

    @Override
    public HauntingEchoes copy() {
        return new HauntingEchoes(this);
    }
}

class HauntingEchoesEffect extends OneShotEffect {

    public HauntingEchoesEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all cards from target player's graveyard other than basic land cards. For each card exiled this way, search that player's library for all cards with the same name as that card and exile them. Then that player shuffles their library";
    }

    public HauntingEchoesEffect(final HauntingEchoesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null && player != null) {
            for (Card card : targetPlayer.getGraveyard().getCards(game)) {
                if (!StaticFilters.FILTER_CARD_BASIC_LAND.match(card, game)) {
                    card.moveToExile(null, "", source.getSourceId(), game);

                    FilterCard filterCard = new FilterCard("cards named " + card.getName());
                    String nameToSearch = card.isSplitCard() ? ((SplitCard) card).getLeftHalfCard().getName() : card.getName();
                    filterCard.add(new NamePredicate(nameToSearch));
                    int count = targetPlayer.getLibrary().count(filterCard, game);
                    TargetCardInLibrary target = new TargetCardInLibrary(count, count, filterCard);

                    player.searchLibrary(target, source, game, targetPlayer.getId());
                    List<UUID> targets = target.getTargets();
                    for (UUID cardId : targets) {
                        Card libraryCard = game.getCard(cardId);
                        if (libraryCard != null) {
                            libraryCard.moveToExile(null, "", source.getSourceId(), game);
                        }
                    }
                }
            }
            targetPlayer.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public HauntingEchoesEffect copy() {
        return new HauntingEchoesEffect(this);
    }

}
