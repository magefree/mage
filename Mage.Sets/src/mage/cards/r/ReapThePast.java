package mage.cards.r;

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
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReapThePast extends CardImpl {

    public ReapThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{G}");

        // Return X cards at random from your graveyard to your hand. Exile Reap the Past.
        this.getSpellAbility().addEffect(new ReapThePastEffect());
    }

    private ReapThePast(final ReapThePast card) {
        super(card);
    }

    @Override
    public ReapThePast copy() {
        return new ReapThePast(this);
    }
}

class ReapThePastEffect extends OneShotEffect {

    ReapThePastEffect() {
        super(Outcome.Benefit);
        staticText = "Return X cards at random from your graveyard to your hand. Exile {this}.";
    }

    private ReapThePastEffect(final ReapThePastEffect effect) {
        super(effect);
    }

    @Override
    public ReapThePastEffect copy() {
        return new ReapThePastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        Cards cards = new CardsImpl(player.getGraveyard());
        while (cards.size() > xValue) {
            cards.remove(cards.getRandom(game));
        }
        player.moveCards(cards, Zone.HAND, source, game);
        return new ExileSpellEffect().apply(game, source);
    }
}