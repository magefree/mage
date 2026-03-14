package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.DevilToken;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpikedCorridorTorturePit extends RoomCard {

    public SpikedCorridorTorturePit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{3}{R}", "{3}{R}");

        // Spiked Corridor
        // When you unlock this door, create three 1/1 red Devil creature tokens with "When this token dies, it deals 1 damage to any target."
        this.getLeftHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(
                new CreateTokenEffect(new DevilToken(), 3), false, true
        ));

        // Torture Pit
        // If a source you control would deal noncombat damage to an opponent, it deals that much damage plus 2 instead.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new TorturePitEffect()));
    }

    private SpikedCorridorTorturePit(final SpikedCorridorTorturePit card) {
        super(card);
    }

    @Override
    public SpikedCorridorTorturePit copy() {
        return new SpikedCorridorTorturePit(this);
    }
}

class TorturePitEffect extends ReplacementEffectImpl {

    TorturePitEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a source you control would deal noncombat damage to an opponent, " +
                "it deals that much damage plus 2 instead";
    }

    private TorturePitEffect(final TorturePitEffect effect) {
        super(effect);
    }

    @Override
    public TorturePitEffect copy() {
        return new TorturePitEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !((DamagePlayerEvent) event).isCombatDamage()
                && source.isControlledBy(game.getControllerId(event.getSourceId()))
                && game.getOpponents(source.getControllerId()).contains(event.getTargetId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 2));
        return false;
    }
}
