
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.card.OwnerIdPredicate;
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

    private LichsMirror(final LichsMirror card) {
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
            Cards toLib = new CardsImpl();
            FilterControlledPermanent filter = new FilterControlledPermanent();
            filter.add(new OwnerIdPredicate(player.getId()));
            toLib.addAll(player.getHand());
            toLib.addAll(player.getGraveyard());
            for(Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)){
                toLib.add(permanent);
            }            
            player.shuffleCardsToLibrary(toLib, game, source);
            game.getState().processAction(game);
            player.drawCards(7, source, game); // original event is not a draw event, so skip it in params
            player.setLife(20, game, source);            
        }
        return true; // replace the loses event
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

}