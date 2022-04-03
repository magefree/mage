
package mage.cards.s;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class StensiaMasquerade extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AttackingPredicate.instance);
    }

    public StensiaMasquerade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // Attacking creatures you control have first strike.
        Effect effect = new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("Attacking creatures you control have first strike");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Whenever a Vampire you control deals combat damage to a player, put a +1/+1 counter on it.
        this.addAbility(new StensiaMasqueradeTriggeredAbility());

        // Madness {2}{R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{2}{R}")));
    }

    private StensiaMasquerade(final StensiaMasquerade card) {
        super(card);
    }

    @Override
    public StensiaMasquerade copy() {
        return new StensiaMasquerade(this);
    }
}

class StensiaMasqueradeTriggeredAbility extends TriggeredAbilityImpl {

    public StensiaMasqueradeTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public StensiaMasqueradeTriggeredAbility(final StensiaMasqueradeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StensiaMasqueradeTriggeredAbility copy() {
        return new StensiaMasqueradeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (damageEvent.isCombatDamage() && permanent != null
                && permanent.hasSubtype(SubType.VAMPIRE, game) && permanent.isControlledBy(controllerId)) {
            this.getEffects().clear();
            AddCountersTargetEffect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
            effect.setTargetPointer(new FixedTarget(permanent, game));
            this.addEffect(effect);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Vampire you control deals combat damage to a player, put a +1/+1 counter on it.";
    }
}
