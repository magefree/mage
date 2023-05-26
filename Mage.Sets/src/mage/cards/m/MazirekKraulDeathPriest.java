
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public final class MazirekKraulDeathPriest extends CardImpl {

    public MazirekKraulDeathPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a player sacrifices another permanent, put a +1/+1 counter on each creature you control.
        this.addAbility(new PlayerSacrificesPermanentTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent()), false));
    }

    private MazirekKraulDeathPriest(final MazirekKraulDeathPriest card) {
        super(card);
    }

    @Override
    public MazirekKraulDeathPriest copy() {
        return new MazirekKraulDeathPriest(this);
    }
}

class PlayerSacrificesPermanentTriggeredAbility extends TriggeredAbilityImpl {

    public PlayerSacrificesPermanentTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever a player sacrifices another permanent, ");
    }

    public PlayerSacrificesPermanentTriggeredAbility(final PlayerSacrificesPermanentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
        return mageObject != null && !event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public PlayerSacrificesPermanentTriggeredAbility copy() {
        return new PlayerSacrificesPermanentTriggeredAbility(this);
    }
}
