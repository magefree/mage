
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public final class PsychicSurgery extends CardImpl {

    public PsychicSurgery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever an opponent shuffles their library, you may look at the top two cards of that library.
        // You may exile one of those cards. Then put the rest on top of that library in any order.
        this.addAbility(new PsychicSurgeryTriggeredAbility());
    }

    private PsychicSurgery(final PsychicSurgery card) {
        super(card);
    }

    @Override
    public PsychicSurgery copy() {
        return new PsychicSurgery(this);
    }
}

class PsychicSurgeryTriggeredAbility extends TriggeredAbilityImpl {

    public PsychicSurgeryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PsychicSurgeryEffect(), true);
        setTriggerPhrase("Whenever an opponent shuffles their library, ");
    }

    public PsychicSurgeryTriggeredAbility(final PsychicSurgeryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PsychicSurgeryTriggeredAbility copy() {
        return new PsychicSurgeryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LIBRARY_SHUFFLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            this.getEffects().get(0).setValue("PsychicSurgeryOpponent", event.getPlayerId());
            return true;
        }
        return false;
    }
}

class PsychicSurgeryEffect extends OneShotEffect {

    public PsychicSurgeryEffect() {
        super(Outcome.Exile);
        this.staticText = "you may look at the top two cards of that library. You may exile one of those cards. Then put the rest on top of that library in any order";
    }

    public PsychicSurgeryEffect(final PsychicSurgeryEffect effect) {
        super(effect);
    }

    @Override
    public PsychicSurgeryEffect copy() {
        return new PsychicSurgeryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID opponentId = (UUID) this.getValue("PsychicSurgeryOpponent");
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(opponentId);

        if (controller != null && opponent != null) {
            Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, 2));
            controller.lookAtCards(source, null, cards, game);
            if (!cards.isEmpty() && controller.chooseUse(Outcome.Exile, "Exile a card?", source, game)) {
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to exile"));
                if (controller.choose(Outcome.Exile, cards, target, source, game)) {
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.EXILED, source, game);
                        cards.remove(card);
                    }
                }
            }
            controller.putCardsOnTopOfLibrary(cards, game, source, true);
            return true;
        }
        return false;
    }
}
