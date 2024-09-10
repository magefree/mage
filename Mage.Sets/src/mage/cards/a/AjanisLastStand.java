package mage.cards.a;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiscardedByOpponentTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.AvatarToken2;

/**
 *
 * @author TheElk801
 */
public final class AjanisLastStand extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    public AjanisLastStand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Whenever a creature or planeswalker you control dies, you may sacrifice Ajani's Last Stand. If you do, create a 4/4 white Avatar creature token with flying.
        this.addAbility(new AjanisLastStandTriggeredAbility());

        // When a spell or ability an opponent controls causes you to discard this card, if you control a Plains, create a 4/4 white Avatar creature token with flying.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DiscardedByOpponentTriggeredAbility(new CreateTokenEffect(new AvatarToken2())),
                new PermanentsOnTheBattlefieldCondition(filter),
                "When a spell or ability an opponent controls causes you to discard this card, "
                + "if you control a Plains, create a 4/4 white Avatar creature token with flying."
        ));
    }

    private AjanisLastStand(final AjanisLastStand card) {
        super(card);
    }

    @Override
    public AjanisLastStand copy() {
        return new AjanisLastStand(this);
    }
}

class AjanisLastStandTriggeredAbility extends TriggeredAbilityImpl {

    public AjanisLastStandTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(
                new CreateTokenEffect(new AvatarToken2()),
                new SacrificeSourceCost()
        ), false);
    }

    private AjanisLastStandTriggeredAbility(final AjanisLastStandTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AjanisLastStandTriggeredAbility copy() {
        return new AjanisLastStandTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            if (zEvent.getTarget().isControlledBy(controllerId)
                    && (zEvent.getTarget().isCreature(game)
                    || zEvent.getTarget().isPlaneswalker(game))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature or planeswalker you control dies, "
                + "you may sacrifice {this}. "
                + "If you do, create a 4/4 white Avatar creature token with flying.";
    }
}
