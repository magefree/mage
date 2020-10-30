package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurntimberSymbiosis extends CardImpl {

    public TurntimberSymbiosis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}{G}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.t.TurntimberSerpentineWood.class;

        // Look at the top seven cards of your library. You may put a creature card from among them onto the battlefield. If that card has converted mana cost 3 or less, it enters with three additional +1/+1 counters on it. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new TurntimberSymbiosisEffect());
    }

    private TurntimberSymbiosis(final TurntimberSymbiosis card) {
        super(card);
    }

    @Override
    public TurntimberSymbiosis copy() {
        return new TurntimberSymbiosis(this);
    }
}

class TurntimberSymbiosisEffect extends OneShotEffect {

    TurntimberSymbiosisEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top seven cards of your library. You may put a creature card " +
                "from among them onto the battlefield. If that card has converted mana cost 3 or less, " +
                "it enters with three additional +1/+1 counters on it. " +
                "Put the rest on the bottom of your library in a random order.";
    }

    private TurntimberSymbiosisEffect(final TurntimberSymbiosisEffect effect) {
        super(effect);
    }

    @Override
    public TurntimberSymbiosisEffect copy() {
        return new TurntimberSymbiosisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 7));
        TargetCard target = new TargetCardInLibrary(
                0, 1, StaticFilters.FILTER_CARD_CREATURE
        );
        player.choose(outcome, cards, target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
            return true;
        }
        boolean small = card.getConvertedManaCost() <= 3;
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null || !small) {
            return true;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(3), source, game);
        return true;
    }
}
