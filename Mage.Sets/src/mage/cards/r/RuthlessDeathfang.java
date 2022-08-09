
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class RuthlessDeathfang extends CardImpl {

    public RuthlessDeathfang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{B}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you sacrifice a creature, target opponent sacrifices a creature.
        Ability ability = new RuthlessDeathfangTriggeredAbility();
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private RuthlessDeathfang(final RuthlessDeathfang card) {
        super(card);
    }

    @Override
    public RuthlessDeathfang copy() {
        return new RuthlessDeathfang(this);
    }
}

class RuthlessDeathfangTriggeredAbility extends TriggeredAbilityImpl {

    public RuthlessDeathfangTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "target opponent"), false);
        setLeavesTheBattlefieldTrigger(true);
        setTriggerPhrase("Whenever you sacrifice a creature, ");
    }

    public RuthlessDeathfangTriggeredAbility(final RuthlessDeathfangTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RuthlessDeathfangTriggeredAbility copy() {
        return new RuthlessDeathfangTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).isCreature(game);
    }
}
