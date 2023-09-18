package mage.cards.r;

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
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiversRebuke extends CardImpl {

    public RiversRebuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Return all nonland permanents target player controls to their owner's hand.
        this.getSpellAbility().addEffect(new RiversRebukeReturnToHandEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private RiversRebuke(final RiversRebuke card) {
        super(card);
    }

    @Override
    public RiversRebuke copy() {
        return new RiversRebuke(this);
    }
}

class RiversRebukeReturnToHandEffect extends OneShotEffect {

    RiversRebukeReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return all nonland permanents target player controls to their owner's hand";
    }

    private RiversRebukeReturnToHandEffect(final RiversRebukeReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND,
                source.getFirstTarget(), source, game
        ).stream().forEach(cards::add);
        return player.moveCards(cards, Zone.HAND, source, game);
    }

    @Override
    public RiversRebukeReturnToHandEffect copy() {
        return new RiversRebukeReturnToHandEffect(this);
    }
}
