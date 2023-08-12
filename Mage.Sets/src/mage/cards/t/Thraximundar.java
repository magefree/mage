
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class Thraximundar extends CardImpl {

    public Thraximundar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Thraximundar attacks, defending player sacrifices a creature.
        this.addAbility(new ThraximundarTriggeredAbility());

        // Whenever a player sacrifices a creature, you may put a +1/+1 counter on Thraximundar.
        this.addAbility(new PlayerSacrificesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true));

    }

    private Thraximundar(final Thraximundar card) {
        super(card);
    }

    @Override
    public Thraximundar copy() {
        return new Thraximundar(this);
    }
}

class ThraximundarTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledPermanent filter;

    static {
        filter = new FilterControlledPermanent(" a creature");
        filter.add(CardType.CREATURE.getPredicate());
    }

    public ThraximundarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(filter, 1, "defending player"));
    }

    public ThraximundarTriggeredAbility(final ThraximundarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThraximundarTriggeredAbility copy() {
        return new ThraximundarTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId() != null
                && event.getSourceId().equals(this.getSourceId())) {
            UUID defender = game.getCombat().getDefendingPlayerId(this.getSourceId(), game);
            this.getEffects().get(0).setTargetPointer(new FixedTarget(defender));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, defending player sacrifices a creature.";
    }
}

class PlayerSacrificesCreatureTriggeredAbility extends TriggeredAbilityImpl {

    public PlayerSacrificesCreatureTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever a player sacrifices a creature, ");
    }

    public PlayerSacrificesCreatureTriggeredAbility(final PlayerSacrificesCreatureTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
        return mageObject != null && mageObject.isCreature(game);
    }

    @Override
    public PlayerSacrificesCreatureTriggeredAbility copy() {
        return new PlayerSacrificesCreatureTriggeredAbility(this);
    }
}
