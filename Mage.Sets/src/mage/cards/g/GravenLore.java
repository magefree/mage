package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GravenLore extends CardImpl {

    public GravenLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        this.supertype.add(SuperType.SNOW);

        // Scry X, where is the amount of {S} spent to cast this spell, then draw three cards.
        this.getSpellAbility().addEffect(new GravenLoreEffect());
    }

    private GravenLore(final GravenLore card) {
        super(card);
    }

    @Override
    public GravenLore copy() {
        return new GravenLore(this);
    }
}

class GravenLoreEffect extends OneShotEffect {

    GravenLoreEffect() {
        super(Outcome.Benefit);
        staticText = "scry X, where X is the amount of {S} spent to cast this spell, then draw three cards";
    }

    private GravenLoreEffect(final GravenLoreEffect effect) {
        super(effect);
    }

    @Override
    public GravenLoreEffect copy() {
        return new GravenLoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int snow = ManaPaidSourceWatcher.getSnowPaid(source.getId(), game);
        if (snow > 0) {
            player.scry(snow, source, game);
        }
        player.drawCards(3, source, game);
        return true;
    }
}
