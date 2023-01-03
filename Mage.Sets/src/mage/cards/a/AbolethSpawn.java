package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.WardAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.stack.StackAbility;

import java.util.UUID;

/**
 * @author Rowan-Gudmundsson
 */
public final class AbolethSpawn extends CardImpl {

  public AbolethSpawn(UUID ownerId, CardSetInfo setInfo) {
    super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{2}{U}");

    this.subtype.add(SubType.FISH);
    this.subtype.add(SubType.HORROR);

    this.power = new MageInt(2);
    this.toughness = new MageInt(3);

    // Flash
    this.addAbility(FlashAbility.getInstance());
    // Ward {2}
    this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

    // Whenever a creature entering the battlefield under an opponent’s control
    // causes a triggered ability of that creature to trigger, you may copy that
    // ability. You may choose new targets for the copy.
    this.addAbility(new AbolethSpawnTriggeredAbility());
  }

  private AbolethSpawn(final AbolethSpawn card) {
    super(card);
  }

  @Override
  public AbolethSpawn copy() {
    return new AbolethSpawn(this);
  }
}

class AbolethSpawnTriggeredAbility extends TriggeredAbilityImpl {

  AbolethSpawnTriggeredAbility() {
    super(Zone.BATTLEFIELD, new CopyStackObjectEffect(), true);
  }

  private AbolethSpawnTriggeredAbility(final AbolethSpawnTriggeredAbility ability) {
    super(ability);
  }

  @Override
  public boolean checkEventType(GameEvent event, Game game) {
    return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
  }

  @Override
  public boolean checkTrigger(GameEvent event, Game game) {
    if (event instanceof NumberOfTriggersEvent) {
      NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
      // Only triggers if not the controller of Aboleth Spawn
      if (!event.getPlayerId().equals(controllerId)) {
        GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
        // Only EtB triggers
        if (sourceEvent != null
            && sourceEvent.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
            && sourceEvent instanceof EntersTheBattlefieldEvent) {
          EntersTheBattlefieldEvent entersTheBattlefieldEvent = (EntersTheBattlefieldEvent) sourceEvent;
          // Only for entering creatures
          if (entersTheBattlefieldEvent.getTarget().isCreature(game)
              && !entersTheBattlefieldEvent.getTarget().getControllerId().equals(controllerId)) {
            // Only for triggers of permanents
            if (game.getPermanent(numberOfTriggersEvent.getSourceId()) != null) {
              StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(
                  numberOfTriggersEvent.getSourceId());
              this.getEffects().setValue("stackObject", stackAbility);
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  @Override
  public AbolethSpawnTriggeredAbility copy() {
    return new AbolethSpawnTriggeredAbility(this);
  }

  @Override
  public String getRule() {
    return "Whenever a creature entering the battlefield under an opponent’s control causes a triggered ability of that creature to trigger, you may copy that ability. You may choose new targets for the copy.";
  }
}
