package mage.cards.b;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Blightbeetle extends CardImpl {

    public Blightbeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from green
        this.addAbility(ProtectionAbility.from(ObjectColor.GREEN));

        // Creatures your opponents control can't have +1/+1 counters put on them.
        this.addAbility(new SimpleStaticAbility(new BlightbeetleEffect()));
    }

    private Blightbeetle(final Blightbeetle card) {
        super(card);
    }

    @Override
    public Blightbeetle copy() {
        return new Blightbeetle(this);
    }
}

class BlightbeetleEffect extends ContinuousRuleModifyingEffectImpl {

    BlightbeetleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Creatures your opponents control can't have +1/+1 counters put on them";
    }

    private BlightbeetleEffect(final BlightbeetleEffect effect) {
        super(effect);
    }

    @Override
    public BlightbeetleEffect copy() {
        return new BlightbeetleEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getData().equals(CounterType.P1P1.getName())) {
            return false;
        }
        Permanent permanent = game.getPermanentEntering(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanent(event.getTargetId());
        }
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        return player != null && player.hasOpponent(source.getControllerId(), game);
    }
}
