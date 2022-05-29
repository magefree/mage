

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;



public final class CarnageGladiator extends CardImpl {

    public CarnageGladiator (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{R}");
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever a creature blocks, that creature's controller loses 1 life.
        this.addAbility(new CarnageGladiatorTriggeredAbility());

        // {1}{B}{R}: Renegerate Carnage Gladiator.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(),new ManaCostsImpl<>("{1}{B}{R}")));


    }

    public CarnageGladiator (final CarnageGladiator card) {
        super(card);
    }

    @Override
    public CarnageGladiator copy() {
        return new CarnageGladiator(this);
    }

}

class CarnageGladiatorTriggeredAbility extends TriggeredAbilityImpl {

    public CarnageGladiatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1));
    }

    public CarnageGladiatorTriggeredAbility(final CarnageGladiatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CarnageGladiatorTriggeredAbility copy() {
        return new CarnageGladiatorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent blocker = game.getPermanent(event.getSourceId());
        if (blocker != null) {
            getEffects().get(0).setTargetPointer(new FixedTarget(blocker.getControllerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature blocks, that creature's controller loses 1 life.";
    }
}