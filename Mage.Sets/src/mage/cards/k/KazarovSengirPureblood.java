
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class KazarovSengirPureblood extends CardImpl {

    public KazarovSengirPureblood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature an opponent controls is dealt damage, put a +1/+1 counter on Kazarov, Sengir Pureblood.
        this.addAbility(new KazarovSengirPurebloodTriggeredAbility());

        // {3}{R}: Kazarov deals 2 damage to target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{3}{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KazarovSengirPureblood(final KazarovSengirPureblood card) {
        super(card);
    }

    @Override
    public KazarovSengirPureblood copy() {
        return new KazarovSengirPureblood(this);
    }
}

class KazarovSengirPurebloodTriggeredAbility extends TriggeredAbilityImpl {

    public KazarovSengirPurebloodTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
    }

    public KazarovSengirPurebloodTriggeredAbility(final KazarovSengirPurebloodTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public KazarovSengirPurebloodTriggeredAbility copy() {
        return new KazarovSengirPurebloodTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return permanent!=null
                && permanent.isCreature(game)
                && game.getOpponents(permanent.getControllerId()).contains(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls is dealt damage, put a +1/+1 counter on {this}.";
    }
}
