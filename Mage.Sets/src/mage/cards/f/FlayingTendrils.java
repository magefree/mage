
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class FlayingTendrils extends CardImpl {

    public FlayingTendrils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // All creatures get -2/-2 until end of turn. If a creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new FlayingTendrilsReplacementEffect());
    }

    private FlayingTendrils(final FlayingTendrils card) {
        super(card);
    }

    @Override
    public FlayingTendrils copy() {
        return new FlayingTendrils(this);
    }
}

class FlayingTendrilsReplacementEffect extends ReplacementEffectImpl {

    public FlayingTendrilsReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "If a creature would die this turn, exile it instead";
    }

    private FlayingTendrilsReplacementEffect(final FlayingTendrilsReplacementEffect effect) {
        super(effect);
    }

    @Override
    public FlayingTendrilsReplacementEffect copy() {
        return new FlayingTendrilsReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null) {
            return controller.moveCards(permanent, Zone.EXILED, source, game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        return zce.isDiesEvent() && zce.getTarget().isCreature(game);
    }

}
