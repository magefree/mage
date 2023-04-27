package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WindsOfAbandon extends CardImpl {

    public WindsOfAbandon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Exile target creature you don't control. For each creature exiled this way, its controller searches their library for a basic land card. Those players put those cards onto the battlefield tapped, then shuffle their libraries.
        this.getSpellAbility().addEffect(new WindsOfAbandonEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));

        // Overload {4}{W}{W}
        this.addAbility(new OverloadAbility(
                this, new WindsOfAbandonOverloadEffect(), new ManaCostsImpl<>("{4}{W}{W}")
        ));
    }

    private WindsOfAbandon(final WindsOfAbandon card) {
        super(card);
    }

    @Override
    public WindsOfAbandon copy() {
        return new WindsOfAbandon(this);
    }
}

class WindsOfAbandonEffect extends OneShotEffect {

    WindsOfAbandonEffect() {
        super(Outcome.Exile);
        staticText = "Exile target creature you don't control. For each creature exiled this way, " +
                "its controller searches their library for a basic land card. " +
                "Those players put those cards onto the battlefield tapped, then shuffle.";
    }

    private WindsOfAbandonEffect(final WindsOfAbandonEffect effect) {
        super(effect);
    }

    @Override
    public WindsOfAbandonEffect copy() {
        return new WindsOfAbandonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        // if the zone change to exile gets replaced does not prevent the target controller to be able to search
        if (!controller.moveCards(permanent, Zone.EXILED, source, game)) {
            return true;
        }
        game.getState().processAction(game);
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        if (player.searchLibrary(target, source, game)) {
            Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
            }
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}

class WindsOfAbandonOverloadEffect extends OneShotEffect {

    WindsOfAbandonOverloadEffect() {
        super(Outcome.Exile);
        staticText = "Exile each creature you don't control. For each creature exiled this way, " +
                "its controller searches their library for a basic land card. " +
                "Those players put those cards onto the battlefield tapped, then shuffle.";
    }

    private WindsOfAbandonOverloadEffect(final WindsOfAbandonOverloadEffect effect) {
        super(effect);
    }

    @Override
    public WindsOfAbandonOverloadEffect copy() {
        return new WindsOfAbandonOverloadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Map<UUID, Integer> playerAmount = new HashMap<>();
        CardsImpl cards = new CardsImpl();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, source.getControllerId(), source, game)) {
            playerAmount.merge(permanent.getControllerId(), 1, Integer::sum);
            cards.add(permanent);
        }
        if (!controller.moveCards(cards, Zone.EXILED, source, game)) {
            return true;
        }
        game.getState().processAction(game);
        for (Map.Entry<UUID, Integer> entry : playerAmount.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player == null) {
                continue;
            }
            TargetCardInLibrary target = new TargetCardInLibrary(0, entry.getValue(),
                    entry.getValue() > 1 ? StaticFilters.FILTER_CARD_BASIC_LANDS : StaticFilters.FILTER_CARD_BASIC_LAND);
            if (player.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    player.moveCards(new CardsImpl(target.getTargets()).getCards(game),
                            Zone.BATTLEFIELD, source, game, true, false, false, null);
                }
            }
            player.shuffleLibrary(source, game);
        }
        return true;
    }
}
