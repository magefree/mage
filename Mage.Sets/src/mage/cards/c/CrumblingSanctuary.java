
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LoneFox

 */
public final class CrumblingSanctuary extends CardImpl {

    public CrumblingSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // If damage would be dealt to a player, that player exiles that many cards from the top of their library instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CrumblingSanctuaryEffect()));
    }

    private CrumblingSanctuary(final CrumblingSanctuary card) {
        super(card);
    }

    @Override
    public CrumblingSanctuary copy() {
        return new CrumblingSanctuary(this);
    }
}

class CrumblingSanctuaryEffect extends PreventionEffectImpl {

    public CrumblingSanctuaryEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        staticText = "If damage would be dealt to a player, that player exiles that many cards from the top of their library instead.";
    }

    private CrumblingSanctuaryEffect(final CrumblingSanctuaryEffect effect) {
        super(effect);
    }

    @Override
    public CrumblingSanctuaryEffect copy() {
        return new CrumblingSanctuaryEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = event.getAmount();
        Player player = game.getPlayer(event.getTargetId());
        if(player != null) {
            preventDamageAction(event, source, game);
            player.moveCards(player.getLibrary().getTopCards(game, amount), Zone.EXILED, source, game);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && (game.getPlayer(event.getTargetId()) != null);
    }

}
