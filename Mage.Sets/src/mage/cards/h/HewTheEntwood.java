package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HewTheEntwood extends CardImpl {

    public HewTheEntwood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Sacrifice any number of lands. Reveal the top X cards of your library, where X is the number of lands sacrificed this way. Choose any number of artifact and/or land cards revealed this way. Put all nonland cards chosen this way onto the battlefield, then put all land cards chosen this way onto the battlefield tapped, then put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new HewTheEntwoodEffect());
    }

    private HewTheEntwood(final HewTheEntwood card) {
        super(card);
    }

    @Override
    public HewTheEntwood copy() {
        return new HewTheEntwood(this);
    }
}

class HewTheEntwoodEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("artifact and/or land cards");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    HewTheEntwoodEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice any number of lands. Reveal the top X cards of your library, " +
                "where X is the number of lands sacrificed this way. Choose any number of artifact and/or " +
                "land cards revealed this way. Put all nonland cards chosen this way onto the battlefield, " +
                "then put all land cards chosen this way onto the battlefield tapped, " +
                "then put the rest on the bottom of your library in a random order";
    }

    private HewTheEntwoodEffect(final HewTheEntwoodEffect effect) {
        super(effect);
    }

    @Override
    public HewTheEntwoodEffect copy() {
        return new HewTheEntwoodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT, true
        );
        player.choose(outcome, target, source, game);
        int count = 0;
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null && permanent.sacrifice(source, game)) {
                count++;
            }
        }
        if (count < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, count));
        player.revealCards(source, cards, game);
        TargetCard targetCard = new TargetCardInLibrary(0, Integer.MAX_VALUE, filter);
        player.choose(outcome, cards, targetCard, source, game);
        Cards toPlay = new CardsImpl(targetCard.getTargets());
        player.moveCards(
                toPlay.getCards(StaticFilters.FILTER_CARD_NON_LAND, game),
                Zone.BATTLEFIELD, source, game
        );
        player.moveCards(
                toPlay.getCards(StaticFilters.FILTER_CARD_LAND, game),
                Zone.BATTLEFIELD, source, game, true, false, false, null
        );
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
