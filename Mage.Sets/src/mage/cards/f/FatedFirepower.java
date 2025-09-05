package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FatedFirepower extends CardImpl {

    public FatedFirepower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{R}{R}{R}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // This enchantment enters with X fire counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.FIRE.createInstance())));

        // If a source you control would deal damage to an opponent or a permanent an opponent controls, it deals that much damage plus an amount of damage equal to the number of fire counters on this enchantment instead.
        this.addAbility(new SimpleStaticAbility(new FatedFirepowerEffect()));
    }

    private FatedFirepower(final FatedFirepower card) {
        super(card);
    }

    @Override
    public FatedFirepower copy() {
        return new FatedFirepower(this);
    }
}

class FatedFirepowerEffect extends ReplacementEffectImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.FIRE);

    FatedFirepowerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "if a source you control would deal damage to an opponent or a permanent " +
                "an opponent controls, it deals that much damage plus an amount of damage equal to " +
                "the number of fire counters on this enchantment instead.";
    }

    private FatedFirepowerEffect(final FatedFirepowerEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), xValue.calculate(game, source, this)));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null
                && controller.hasOpponent(getControllerOrSelf(event.getTargetId(), game), game)
                && source.isControlledBy(game.getControllerId(event.getSourceId()))
                && event.getAmount() > 0;
    }

    private static UUID getControllerOrSelf(UUID id, Game game) {
        UUID outId = game.getControllerId(id);
        return outId == null ? id : outId;
    }

    @Override
    public FatedFirepowerEffect copy() {
        return new FatedFirepowerEffect(this);
    }
}
