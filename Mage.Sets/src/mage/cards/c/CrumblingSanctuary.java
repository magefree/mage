
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LoneFox

 */
public final class CrumblingSanctuary extends CardImpl {

    public CrumblingSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // If damage would be dealt to a player, that player exiles that many cards from the top of their library instead.
        this.addAbility(new SimpleStaticAbility(new CrumblingSanctuaryEffect()));
    }

    private CrumblingSanctuary(final CrumblingSanctuary card) {
        super(card);
    }

    @Override
    public CrumblingSanctuary copy() {
        return new CrumblingSanctuary(this);
    }
}

class CrumblingSanctuaryEffect extends ReplacementEffectImpl {

    CrumblingSanctuaryEffect() {
        super(Duration.WhileOnBattlefield, Outcome.PreventDamage);
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
            player.moveCards(player.getLibrary().getTopCards(game, amount), Zone.EXILED, source, game);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}
