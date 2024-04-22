
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class AvenMindcensor extends CardImpl {

    public AvenMindcensor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // If an opponent would search a library, that player searches the top four cards of that library instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AvenMindcensorEffect()));

    }

    private AvenMindcensor(final AvenMindcensor card) {
        super(card);
    }

    @Override
    public AvenMindcensor copy() {
        return new AvenMindcensor(this);
    }
}

class AvenMindcensorEffect extends ReplacementEffectImpl {
    AvenMindcensorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an opponent would search a library, that player searches the top four cards of that library instead";
    }

    private AvenMindcensorEffect(final AvenMindcensorEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(4);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SEARCH_LIBRARY;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && game.isOpponent(controller, event.getPlayerId());
    }

    @Override
    public AvenMindcensorEffect copy() {
        return new AvenMindcensorEffect(this);
    }
}
