package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author noxx
 */
public final class HavengulSkaab extends CardImpl {

    public HavengulSkaab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever Havengul Skaab attacks, return another creature you control to its owner's hand.
        this.addAbility(new HavengulSkaabAbility());
    }

    private HavengulSkaab(final HavengulSkaab card) {
        super(card);
    }

    @Override
    public HavengulSkaab copy() {
        return new HavengulSkaab(this);
    }
}

class HavengulSkaabAbility extends TriggeredAbilityImpl {

    public HavengulSkaabAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect());
    }

    public HavengulSkaabAbility(final HavengulSkaabAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL, true);
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, return another creature you control to its owner's hand.";
    }

    @Override
    public HavengulSkaabAbility copy() {
        return new HavengulSkaabAbility(this);
    }
}
