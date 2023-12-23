
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public final class LaboratoryManiac extends CardImpl {

    public LaboratoryManiac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // If you would draw a card while your library has no cards in it, you win the game instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LaboratoryManiacEffect()));

    }

    private LaboratoryManiac(final LaboratoryManiac card) {
        super(card);
    }

    @Override
    public LaboratoryManiac copy() {
        return new LaboratoryManiac(this);
    }
}

class LaboratoryManiacEffect extends ReplacementEffectImpl {

    public LaboratoryManiacEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card while your library has no cards in it, you win the game instead";
    }

    private LaboratoryManiacEffect(final LaboratoryManiacEffect effect) {
        super(effect);
    }

    @Override
    public LaboratoryManiacEffect copy() {
        return new LaboratoryManiacEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            player.won(game);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Player player = game.getPlayer(event.getPlayerId());
            if (player != null && !player.hasLost() && !player.getLibrary().hasCards()) {
                return true;
            }
        }
        return false;
    }

}
