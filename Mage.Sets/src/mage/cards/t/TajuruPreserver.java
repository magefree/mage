package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author noxx
 */
public final class TajuruPreserver extends CardImpl {

    public TajuruPreserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Spells and abilities your opponents control can't cause you to sacrifice permanents.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TajuruPreserverEffect()));
    }

    private TajuruPreserver(final TajuruPreserver card) {
        super(card);
    }

    @Override
    public TajuruPreserver copy() {
        return new TajuruPreserver(this);
    }
}

class TajuruPreserverEffect extends ReplacementEffectImpl {

    public TajuruPreserverEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Spells and abilities your opponents control can't cause you to sacrifice permanents";
    }

    private TajuruPreserverEffect(final TajuruPreserverEffect effect) {
        super(effect);
    }

    @Override
    public TajuruPreserverEffect copy() {
        return new TajuruPreserverEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID eventSourceControllerId = game.getControllerId(event.getSourceId());
        Permanent permanent = game.getPermanent(event.getTargetId());

        if (controller != null && permanent != null && permanent.getControllerId() == source.getControllerId()) {
            return game.getOpponents(source.getControllerId()).contains(eventSourceControllerId);
        }

        return false;
    }

}
