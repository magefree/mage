package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.MonocoloredPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author TheElk801
 */
public final class EmergentUltimatum extends CardImpl {

    public EmergentUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}{G}{G}{G}{U}{U}");

        // Search your library for up to three monocolored cards with different names and exile them. An opponent chooses one of those cards. Shuffle that card into your library. You may cast the other cards without paying their mana costs. Exile Emergent Ultimatum.
        this.getSpellAbility().addEffect(new EmergentUltimatumEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private EmergentUltimatum(final EmergentUltimatum card) {
        super(card);
    }

    @Override
    public EmergentUltimatum copy() {
        return new EmergentUltimatum(this);
    }
}

class EmergentUltimatumEffect extends OneShotEffect {

    EmergentUltimatumEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for up to three monocolored cards with different names and exile them. " +
                "An opponent chooses one of those cards. Shuffle that card into your library. " +
                "You may cast the other cards without paying their mana costs";
    }

    private EmergentUltimatumEffect(final EmergentUltimatumEffect effect) {
        super(effect);
    }

    @Override
    public EmergentUltimatumEffect copy() {
        return new EmergentUltimatumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary targetCardInLibrary = new EmergentUltimatumTarget();
        targetCardInLibrary.setNotTarget(true);
        boolean searched = player.searchLibrary(targetCardInLibrary, source, game);
        Cards cards = new CardsImpl(targetCardInLibrary.getTargets());
        player.moveCards(cards, Zone.EXILED, source, game);
        if (cards.isEmpty()) {
            if (searched) {
                player.shuffleLibrary(source, game);
            }
            return false;
        }
        TargetOpponent targetOpponent = new TargetOpponent();
        targetOpponent.setNotTarget(true);
        player.choose(outcome, targetOpponent, source.getSourceId(), game);
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent == null) {
            if (searched) {
                player.shuffleLibrary(source, game);
            }
            return false;
        }
        TargetCardInExile targetCardInExile = new TargetCardInExile(StaticFilters.FILTER_CARD);
        targetCardInExile.setNotTarget(true);
        opponent.choose(outcome, cards, targetCardInExile, game);
        Card toShuffle = game.getCard(targetCardInExile.getFirstTarget());
        if (toShuffle != null) {
            player.putCardsOnBottomOfLibrary(toShuffle, game, source, false);
            player.shuffleLibrary(source, game);
            cards.remove(toShuffle);
        }
        while (!cards.isEmpty()) {
            if (!player.chooseUse(Outcome.PlayForFree, "Cast an exiled card without paying its mana cost?", source, game)) {
                break;
            }
            targetCardInExile.clearChosen();
            if (!player.choose(Outcome.PlayForFree, cards, targetCardInExile, game)) {
                continue;
            }
            Card card = game.getCard(targetCardInExile.getFirstTarget());
            if (card == null) {
                continue;
            }
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            Boolean cardWasCast = player.cast(player.chooseAbilityForCast(card, game, true),
                    game, true, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
            cards.remove(card); // remove on non cast too (infinite freeze fix)
            if (cardWasCast) {
                cards.remove(card);
            } else {
                game.informPlayer(player, "You're not able to cast "
                        + card.getIdName() + " or you canceled the casting.");
            }
        }
        return true;
    }
}

class EmergentUltimatumTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("monocolored cards with different names");

    static {
        filter.add(MonocoloredPredicate.instance);
    }

    EmergentUltimatumTarget() {
        super(0, 3, filter);
    }

    private EmergentUltimatumTarget(final EmergentUltimatumTarget target) {
        super(target);
    }

    @Override
    public EmergentUltimatumTarget copy() {
        return new EmergentUltimatumTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null
                && this.getTargets()
                .stream()
                .map(game::getCard)
                .map(MageObject::getName)
                .noneMatch(card.getName()::equals);
    }
}