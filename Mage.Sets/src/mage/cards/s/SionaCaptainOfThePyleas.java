package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SionaCaptainOfThePyleas extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Aura card");

    static {
        filter.add(SubType.AURA.getPredicate());
    }

    public SionaCaptainOfThePyleas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Siona, Captain of the Pyleas enters the battlefield, look at the top seven cards of your library. You may reveal an Aura card among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(7, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM)));

        // Whenever an Aura you control becomes attached to a creature you control, create a 1/1 white Human Soldier creature token.
        this.addAbility(new SionaCaptainOfThePyleasAbility());
    }

    private SionaCaptainOfThePyleas(final SionaCaptainOfThePyleas card) {
        super(card);
    }

    @Override
    public SionaCaptainOfThePyleas copy() {
        return new SionaCaptainOfThePyleas(this);
    }
}

class SionaCaptainOfThePyleasAbility extends TriggeredAbilityImpl {

    SionaCaptainOfThePyleasAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new HumanSoldierToken()), false);
    }

    private SionaCaptainOfThePyleasAbility(final SionaCaptainOfThePyleasAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        Permanent attachment = game.getPermanent(event.getSourceId());
        return permanent != null
                && attachment != null
                && permanent.isControlledBy(getControllerId())
                && permanent.isCreature(game)
                && attachment.isControlledBy(getControllerId())
                && attachment.hasSubtype(SubType.AURA, game);
    }

    @Override
    public String getRule() {
        return "Whenever an Aura you control becomes attached to a creature you control, " +
                "create a 1/1 white Human Soldier creature token.";
    }

    @Override
    public SionaCaptainOfThePyleasAbility copy() {
        return new SionaCaptainOfThePyleasAbility(this);
    }
}
