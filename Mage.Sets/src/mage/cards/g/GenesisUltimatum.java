package mage.cards.g;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GenesisUltimatum extends CardImpl {

    public GenesisUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}{U}{U}{U}{R}{R}");

        // Look at the top five cards of your library. Put any number of permanent cards from among them onto the battlefield and the rest into your hand. Exile Genesis Ultimatum.
        this.getSpellAbility().addEffect(new GenesisUltimatumEffect());
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    private GenesisUltimatum(final GenesisUltimatum card) {
        super(card);
    }

    @Override
    public GenesisUltimatum copy() {
        return new GenesisUltimatum(this);
    }
}

class GenesisUltimatumEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterPermanentCard();

    GenesisUltimatumEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top five cards of your library. Put any number of permanent cards " +
                "from among them onto the battlefield and the rest into your hand.";
    }

    private GenesisUltimatumEffect(final GenesisUltimatumEffect effect) {
        super(effect);
    }

    @Override
    public GenesisUltimatumEffect copy() {
        return new GenesisUltimatumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards toHand = new CardsImpl(player.getLibrary().getTopCards(game, 5));
        player.lookAtCards("", toHand, game);
        TargetCard targetCard = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
        player.choose(outcome, toHand, targetCard, game);
        Cards toBattlefield = new CardsImpl(targetCard.getTargets());
        if (player.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game)) {
            toBattlefield
                    .stream()
                    .map(game::getPermanent)
                    .map(MageItem::getId)
                    .forEach(toHand::remove);
        }
        player.moveCards(toHand, Zone.HAND, source, game);
        return true;
    }
}
