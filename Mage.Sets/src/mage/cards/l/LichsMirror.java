
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class LichsMirror extends CardImpl {

    public LichsMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // If you would lose the game, instead shuffle your hand, your graveyard, and all permanents you own into your library, then draw seven cards and your life total becomes 20.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LichsMirrorEffect()));
    }

    public LichsMirror(final LichsMirror card) {
        super(card);
    }

    @Override
    public LichsMirror copy() {
        return new LichsMirror(this);
    }
}

class LichsMirrorEffect extends ReplacementEffectImpl {

    public LichsMirrorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would lose the game, instead shuffle your hand, your graveyard, and all permanents you own into your library, then draw seven cards and your life total becomes 20";
    }

    public LichsMirrorEffect(final LichsMirrorEffect effect) {
        super(effect);
    }

    @Override
    public LichsMirrorEffect copy() {
        return new LichsMirrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            FilterControlledPermanent filter = new FilterControlledPermanent();
            filter.add(new OwnerIdPredicate(player.getId()));
            for (UUID uuid : player.getHand().copy()) {
                Card card = game.getCard(uuid);
                if (card != null) {
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
            }
        
            for (UUID uuid : player.getGraveyard().copy()) {
                Card card = game.getCard(uuid);
                if (card != null) {
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
            }
            
            for(Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)){
                permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            }
            player.shuffleLibrary(source, game);
            
            player.drawCards(7, game);
            
            player.setLife(20, game, source);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }

}