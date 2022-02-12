package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SoldierLifelinkToken;

/**
 *
 * @author TheElk801
 */
public final class HaazdaMarshal extends CardImpl {

    public HaazdaMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Haazda Marshal and at least two other creatures attack, create a 1/1 white Solider creature token with lifelink.
        this.addAbility(new HaazdaMarshalTriggeredAbility());
    }

    private HaazdaMarshal(final HaazdaMarshal card) {
        super(card);
    }

    @Override
    public HaazdaMarshal copy() {
        return new HaazdaMarshal(this);
    }
}

class HaazdaMarshalTriggeredAbility extends TriggeredAbilityImpl {

    public HaazdaMarshalTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new SoldierLifelinkToken()));
    }

    public HaazdaMarshalTriggeredAbility(final HaazdaMarshalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HaazdaMarshalTriggeredAbility copy() {
        return new HaazdaMarshalTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= 3
                && game.getCombat().getAttackers().contains(this.sourceId);
    }

    @Override
    public String getRule() {
        return "Whenever {this} and at least two other creatures attack, "
                + "create a 1/1 white Soldier creature token with lifelink.";
    }
}
