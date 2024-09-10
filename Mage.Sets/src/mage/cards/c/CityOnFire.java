package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CityOnFire extends CardImpl {

    public CityOnFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}{R}{R}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // If a source you control would deal damage to a permanent or player, it deals triple that damage instead.
        this.addAbility(new SimpleStaticAbility(new CityOnFireEffect()));
    }

    private CityOnFire(final CityOnFire card) {
        super(card);
    }

    @Override
    public CityOnFire copy() {
        return new CityOnFire(this);
    }
}

class CityOnFireEffect extends ReplacementEffectImpl {

    CityOnFireEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source you control would deal damage " +
                "to a permanent or player, it deals triple that damage instead";
    }

    private CityOnFireEffect(final CityOnFireEffect effect) {
        super(effect);
    }

    @Override
    public CityOnFireEffect copy() {
        return new CityOnFireEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER)
                || event.getType().equals(GameEvent.EventType.DAMAGE_PERMANENT);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getControllerId(event.getSourceId()).equals(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 3));
        return false;
    }
}
