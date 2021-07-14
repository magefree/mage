
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DeathtouchSnakeToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class HapatraVizierOfPoisons extends CardImpl {

    public HapatraVizierOfPoisons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Hapatra, Vizier of Poisons deals combat damage to a player, you may put a -1/-1 counter on target creature.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance()), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever you put one or more -1/-1 counters on a creature, create a 1/1 green Snake creature token with deathtouch.
        this.addAbility(new HapatraVizierOfPoisonsTriggeredAbility());
    }

    private HapatraVizierOfPoisons(final HapatraVizierOfPoisons card) {
        super(card);
    }

    @Override
    public HapatraVizierOfPoisons copy() {
        return new HapatraVizierOfPoisons(this);
    }
}

class HapatraVizierOfPoisonsTriggeredAbility extends TriggeredAbilityImpl {

    HapatraVizierOfPoisonsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new DeathtouchSnakeToken()), false);
    }

    private HapatraVizierOfPoisonsTriggeredAbility(HapatraVizierOfPoisonsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getData().equals(CounterType.M1M1.getName())
                || !isControlledBy(event.getPlayerId())) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null && permanent.isCreature(game);

    }

    @Override
    public HapatraVizierOfPoisonsTriggeredAbility copy() {
        return new HapatraVizierOfPoisonsTriggeredAbility(this);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you put one or more -1/-1 counters on a creature, " ;
    }
}
