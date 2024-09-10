package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirkwoodBats extends CardImpl {

    public MirkwoodBats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.BAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you create or sacrifice a token, each opponent loses 1 life.
        this.addAbility(new MirkwoodBatsTriggeredAbility());
    }

    private MirkwoodBats(final MirkwoodBats card) {
        super(card);
    }

    @Override
    public MirkwoodBats copy() {
        return new MirkwoodBats(this);
    }
}

class MirkwoodBatsTriggeredAbility extends TriggeredAbilityImpl {

    MirkwoodBatsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeOpponentsEffect(1));
        this.setTriggerPhrase("Whenever you create or sacrifice a token, ");
    }

    private MirkwoodBatsTriggeredAbility(final MirkwoodBatsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MirkwoodBatsTriggeredAbility copy() {
        return new MirkwoodBatsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATED_TOKEN
                || event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        switch (event.getType()) {
            case CREATED_TOKEN:
                return true;
            case SACRIFICED_PERMANENT:
                Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
                return permanent instanceof PermanentToken;
            default:
                return false;
        }
    }
}
