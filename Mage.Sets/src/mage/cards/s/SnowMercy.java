
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class SnowMercy extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with globe counters on them");

    static {
        filter.add(CounterType.GLOBE.getPredicate());
    }

    public SnowMercy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        this.supertype.add(SuperType.SNOW);

        // Whenever a creature deals damage to you, put a globe counter on it.
        this.addAbility(new AddGlobeCountersAbility());

        // {t},{q},{t},{q},{t}: Tap all creatures with globe counters on them.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapAllEffect(filter), new SnowMercyCost());
        this.addAbility(ability);
    }

    private SnowMercy(final SnowMercy card) {
        super(card);
    }

    @Override
    public SnowMercy copy() {
        return new SnowMercy(this);
    }
}

class AddGlobeCountersAbility extends TriggeredAbilityImpl {

    public AddGlobeCountersAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.GLOBE.createInstance()));
    }

    public AddGlobeCountersAbility(final AddGlobeCountersAbility ability) {
        super(ability);
    }

    @Override
    public AddGlobeCountersAbility copy() {
        return new AddGlobeCountersAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.isCreature(game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals damage to you, put a globe counter on it.";
    }

}

class SnowMercyCost extends CostImpl {

    SnowMercyCost() {
        this.text = "{t}, {q}, {t}, {q}, {t}";
    }

    SnowMercyCost(final SnowMercyCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            // Tap, Untap, Tap, Untap, Tap:
            if (!permanent.isTapped() && permanent.tap(source, game)) {
                if (permanent.isTapped() && permanent.untap(game)) {
                    if (!permanent.isTapped() && permanent.tap(source, game)) {
                        if (permanent.isTapped() && permanent.untap(game)) {
                            if (!permanent.isTapped() && permanent.tap(source, game)) {
                                paid = true;
                            }
                        }
                    }
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return !permanent.isTapped();
        }
        return false;
    }

    @Override
    public SnowMercyCost copy() {
        return new SnowMercyCost(this);
    }
}
