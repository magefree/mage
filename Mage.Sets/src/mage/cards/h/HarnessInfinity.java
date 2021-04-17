package mage.cards.h;

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
public final class HarnessInfinity extends CardImpl {

    public HarnessInfinity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{B}{B}{G}{G}{G}");

        // Exchange your hand and graveyard.
        this.getSpellAbility().addEffect(new HarnessInfinityEffect());

        // Exile Harness Infinity.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private HarnessInfinity(final HarnessInfinity card) {
        super(card);
    }

    @Override
    public HarnessInfinity copy() {
        return new HarnessInfinity(this);
    }
}

class HarnessInfinityEffect extends OneShotEffect {

    HarnessInfinityEffect() {
        super(Outcome.Benefit);
        staticText = "Exchange your hand and graveyard.<br>";
    }

    private HarnessInfinityEffect(final HarnessInfinityEffect effect) {
        super(effect);
    }

    @Override
    public HarnessInfinityEffect copy() {
        return new HarnessInfinityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards hand = new CardsImpl(player.getHand());
        Cards graveyard = new CardsImpl(player.getGraveyard());
        player.moveCards(hand, Zone.GRAVEYARD, source, game);
        player.moveCards(graveyard, Zone.HAND, source, game);
        return true;
    }
}
