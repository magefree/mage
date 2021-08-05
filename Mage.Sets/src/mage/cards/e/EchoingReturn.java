package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EchoingReturn extends CardImpl {

    public EchoingReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Return target creature card and all other cards with the same name as that card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new EchoingReturnEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private EchoingReturn(final EchoingReturn card) {
        super(card);
    }

    @Override
    public EchoingReturn copy() {
        return new EchoingReturn(this);
    }
}

class EchoingReturnEffect extends OneShotEffect {

    EchoingReturnEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature card and all other cards " +
                "with the same name as that card from your graveyard to your hand";
    }

    private EchoingReturnEffect(final EchoingReturnEffect effect) {
        super(effect);
    }

    @Override
    public EchoingReturnEffect copy() {
        return new EchoingReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        Cards cards = new CardsImpl(card);
        player.getGraveyard()
                .getCards(game)
                .stream()
                .filter(c -> CardUtil.haveSameNames(c.getName(), card.getName()))
                .forEach(cards::add);
        return player.moveCards(cards, Zone.HAND, source, game);
    }
}
