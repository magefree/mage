package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormwildCapridor extends CardImpl {

    public StormwildCapridor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.GOAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If noncombat damage would be dealt to Stormwild Capridor, prevent that damage. 
        // Put a +1/+1 counter on Stormwild Capridor for each 1 damage prevented this way.
        this.addAbility(new SimpleStaticAbility(new StormwildCapridorEffect()));
    }

    private StormwildCapridor(final StormwildCapridor card) {
        super(card);
    }

    @Override
    public StormwildCapridor copy() {
        return new StormwildCapridor(this);
    }
}

class StormwildCapridorEffect extends PreventionEffectImpl {

    StormwildCapridorEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "If noncombat damage would be dealt to {this}, prevent that damage. "
                + "Put a +1/+1 counter on {this} for each 1 damage prevented this way";
    }

    private StormwildCapridorEffect(final StormwildCapridorEffect effect) {
        super(effect);
    }

    @Override
    public StormwildCapridorEffect copy() {
        return new StormwildCapridorEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = preventDamageAction(event, source, game);
        if (preventionEffectData.getPreventedDamage() > 0) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(preventionEffectData.getPreventedDamage()), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getTargetId().equals(source.getSourceId())
                || !super.applies(event, source, game)) {
            return false;
        }
        DamageEvent damageEvent = (DamageEvent) event;
        return !damageEvent.isCombatDamage();
    }

}
