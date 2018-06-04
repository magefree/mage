
package mage.cards.m;

import java.util.Iterator;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author HCrescent
 */
public final class MagusOfTheJar extends CardImpl {

    public MagusOfTheJar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {tap}, Sacrifice Magus of the Jar: Each player exiles all cards from their hand face down and draws seven cards. At the beginning of the next end step, each player discards their hand and returns to their hand each card he or she exiled this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MagusoftheJarEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    
    }

    public MagusOfTheJar(final MagusOfTheJar card) {
        super(card);
    }

    @Override
    public MagusOfTheJar copy() {
        return new MagusOfTheJar(this);
    }
}

class MagusoftheJarEffect extends OneShotEffect {

    public MagusoftheJarEffect() {
        super(Outcome.DrawCard);
        staticText = "Each player exiles all cards from their hand face down and draws seven cards. At the beginning of the next end step, each player discards their hand and returns to their hand each card he or she exiled this way.";
    }

    public MagusoftheJarEffect(final MagusoftheJarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = new CardsImpl();
        //Exile hand
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                Cards hand = player.getHand();
                while (!hand.isEmpty()) {
                    Card card = hand.get(hand.iterator().next(), game);
                    if (card != null) {
                        card.moveToExile(getId(), "Magus of the Jar", source.getSourceId(), game);
                        card.setFaceDown(true, game);
                        cards.add(card);
                    }
                }
            }
        }
        //Draw 7 cards
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.drawCards(7, game);
            }
        }
        //Delayed ability
        Effect effect = new MagusoftheJarDelayedEffect();
        effect.setValue("MagusoftheJarCards", cards);
        game.addDelayedTriggeredAbility(new MagusoftheJarDelayedTriggeredAbility(effect), source);
        return true;
    }

    @Override
    public MagusoftheJarEffect copy() {
        return new MagusoftheJarEffect(this);
    }
}

class MagusoftheJarDelayedEffect extends OneShotEffect {

    public MagusoftheJarDelayedEffect() {
        super(Outcome.DrawCard);
        staticText = "At the beginning of the next end step, each player discards their hand and returns to their hand each card he or she exiled this way";
    }

    public MagusoftheJarDelayedEffect(final MagusoftheJarDelayedEffect effect) {
        super(effect);
    }

    @Override
    public MagusoftheJarDelayedEffect copy() {
        return new MagusoftheJarDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = (Cards) this.getValue("MagusoftheJarCards");

        if (cards != null) {
            //Discard
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.discard(player.getHand().size(), false, source, game);
                }
            }
            //Return to hand
            for (Iterator<Card> it = cards.getCards(game).iterator(); it.hasNext();) {
                Card card = it.next();
                card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
            }
            return true;
        }
        return false;
    }

}

class MagusoftheJarDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public MagusoftheJarDelayedTriggeredAbility(Effect effect) {
        super(effect);
    }

    public MagusoftheJarDelayedTriggeredAbility(final MagusoftheJarDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MagusoftheJarDelayedTriggeredAbility copy() {
        return new MagusoftheJarDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

}
