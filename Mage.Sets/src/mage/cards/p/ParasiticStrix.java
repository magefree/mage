
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPlayer;

/**
 * @author mluds
 */
public final class ParasiticStrix extends CardImpl {

    public ParasiticStrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
	
        // When Parasitic Strix enters the battlefield, if you control a black permanent, target player loses 2 life and you gain 2 life.
        this.addAbility(new ParasiticStrixTriggeredAbility());
    }

    private ParasiticStrix(final ParasiticStrix card) {
        super(card);
    }

    @Override
    public ParasiticStrix copy() {
        return new ParasiticStrix(this);
    }
}

class ParasiticStrixTriggeredAbility extends TriggeredAbilityImpl {

    public ParasiticStrixTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2));
        this.addEffect(new GainLifeEffect(2));
        this.addTarget(new TargetPlayer());
    }

    private ParasiticStrixTriggeredAbility(final ParasiticStrixTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ParasiticStrixTriggeredAbility copy() {
        return new ParasiticStrixTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        FilterPermanent filter = new FilterPermanent();
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        if (game.getBattlefield().countAll(filter, this.controllerId, game) >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When Parasitic Strix enters the battlefield, if you control a black permanent, target player loses 2 life and you gain 2 life.";
    }
}
