package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FontOfAgonies extends CardImpl {

    public FontOfAgonies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // Whenever you pay life, put that many blood counters on Font of Agonies.
        this.addAbility(new FontOfAgoniesTriggeredAbility());

        // {1}{B}, Remove four blood counters from Font of Agonies: Destroy target creature.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.BLOOD.createInstance(4)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FontOfAgonies(final FontOfAgonies card) {
        super(card);
    }

    @Override
    public FontOfAgonies copy() {
        return new FontOfAgonies(this);
    }
}

class FontOfAgoniesTriggeredAbility extends TriggeredAbilityImpl {

    FontOfAgoniesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.BLOOD.createInstance()), false);
    }

    private FontOfAgoniesTriggeredAbility(final FontOfAgoniesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FontOfAgoniesTriggeredAbility copy() {
        return new FontOfAgoniesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.LIFE_PAID);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(controllerId) && event.getAmount() > 0) {
            this.getEffects().clear();
            if (event.getAmount() > 0) {
                this.addEffect(new AddCountersSourceEffect(CounterType.BLOOD.createInstance(event.getAmount())));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you pay life, put that many blood counters on {this}.";
    }
}