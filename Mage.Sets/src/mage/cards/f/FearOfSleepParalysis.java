package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Grath
 */
public final class FearOfSleepParalysis extends CardImpl {

    public FearOfSleepParalysis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{5}{U}");
        
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Eerie -- Whenever Fear of Sleep Paralysis or another enchantment you control enters and whenever you fully unlock a Room, tap up to one target creature and put a stun counter on it.
        Ability ability = new  EerieAbility(new TapTargetEffect()).setTriggerPhrase("Whenever this creature or another enchantment you control enters and whenever you fully unlock a Room, ");
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("and put a stun counter on it"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Stun counters can't be removed from permanents your opponents control.
        this.addAbility(new SimpleStaticAbility(new FearOfSleepParalysisEffect()));
    }

    private FearOfSleepParalysis(final FearOfSleepParalysis card) {
        super(card);
    }

    @Override
    public FearOfSleepParalysis copy() {
        return new FearOfSleepParalysis(this);
    }
}

class FearOfSleepParalysisEffect extends ReplacementEffectImpl {

    FearOfSleepParalysisEffect() {
        super(Duration.WhileOnBattlefield, Outcome.PreventDamage);
        staticText = "Stun counters can't be removed from permanents your opponents control";
    }

    private FearOfSleepParalysisEffect(final FearOfSleepParalysisEffect effect) {
        super(effect);
    }

    @Override
    public FearOfSleepParalysisEffect copy() {
        return new FearOfSleepParalysisEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.REMOVE_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent target = game.getPermanent(event.getTargetId());
        return target != null && event.getData().equals(CounterType.STUN.getName()) && !target.getControllerId().equals(source.getControllerId());
    }

}
