
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ShahOfNaarIsle extends CardImpl {

    public ShahOfNaarIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.EFREET);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Echo {0}
        this.addAbility(new EchoAbility("{0}"));

        // When Shah of Naar Isle's echo cost is paid, each opponent may draw up to three cards.
        this.addAbility(new ShahOfNaarIsleTriggeredAbility());
    }

    private ShahOfNaarIsle(final ShahOfNaarIsle card) {
        super(card);
    }

    @Override
    public ShahOfNaarIsle copy() {
        return new ShahOfNaarIsle(this);
    }
}

class ShahOfNaarIsleTriggeredAbility extends TriggeredAbilityImpl {

    public ShahOfNaarIsleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ShahOfNaarIsleEffect(), false);
        setTriggerPhrase("When {this}'s echo cost is paid, ");
    }

    public ShahOfNaarIsleTriggeredAbility(final ShahOfNaarIsleTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ECHO_PAID;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return getSourceId().equals(event.getSourceId());
    }

    @Override
    public ShahOfNaarIsleTriggeredAbility copy() {
        return new ShahOfNaarIsleTriggeredAbility(this);
    }
}

class ShahOfNaarIsleEffect extends OneShotEffect {

    public ShahOfNaarIsleEffect() {
        super(Outcome.DrawCard);
        this.staticText = "each opponent may draw up to three cards";
    }

    public ShahOfNaarIsleEffect(final ShahOfNaarIsleEffect effect) {
        super(effect);
    }

    @Override
    public ShahOfNaarIsleEffect copy() {
        return new ShahOfNaarIsleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    int number = opponent.getAmount(0, 3, "Draw how many cards?", game);
                    opponent.drawCards(number, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
