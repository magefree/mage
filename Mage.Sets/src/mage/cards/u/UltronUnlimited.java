package mage.cards.u;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

public final class UltronUnlimited extends CardImpl {

    public UltronUnlimited(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT, SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new AttacksTriggeredAbility(new ConniveSourceEffect("he")));
        this.addAbility(new UltronUnlimitedConniveTriggeredAbility());
    }

    private UltronUnlimited(final UltronUnlimited card) {
        super(card);
    }

    @Override
    public UltronUnlimited copy() {
        return new UltronUnlimited(this);
    }
}

class UltronUnlimitedConniveTriggeredAbility extends TriggeredAbilityImpl {

    UltronUnlimitedConniveTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(
                new CreateTokenEffect(new UltronUnlimitedRobotToken()),
                new GenericManaCost(1), "pay {1} to create a Robot token?"
        ), false);
        setTriggerPhrase("Whenever a creature you control connives,");
    }

    private UltronUnlimitedConniveTriggeredAbility(final UltronUnlimitedConniveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CONNIVE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return permanent != null && permanent.isCreature(game) && permanent.isControlledBy(getControllerId());
    }

    @Override
    public UltronUnlimitedConniveTriggeredAbility copy() {
        return new UltronUnlimitedConniveTriggeredAbility(this);
    }
}

class UltronUnlimitedRobotToken extends TokenImpl {

    UltronUnlimitedRobotToken() {
        super("Robot", "2/2 colorless Robot Villain artifact creature");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ROBOT, SubType.VILLAIN);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private UltronUnlimitedRobotToken(final UltronUnlimitedRobotToken token) {
        super(token);
    }

    @Override
    public UltronUnlimitedRobotToken copy() {
        return new UltronUnlimitedRobotToken(this);
    }
}
