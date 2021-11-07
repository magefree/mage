package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShieldMare extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("red creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public ShieldMare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Shield Mare can't be blocked by red creatures.
        this.addAbility(new SimpleStaticAbility(
                new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)
        ));

        // When Shield Mare enters the battlefield or becomes the target of a spell or ability and opponent controls, you gain 3 life.
        this.addAbility(new ShieldMareTriggeredAbility());
    }

    private ShieldMare(final ShieldMare card) {
        super(card);
    }

    @Override
    public ShieldMare copy() {
        return new ShieldMare(this);
    }
}

class ShieldMareTriggeredAbility extends TriggeredAbilityImpl {

    public ShieldMareTriggeredAbility() {
        super(Zone.ALL, new GainLifeEffect(3));
    }

    public ShieldMareTriggeredAbility(final ShieldMareTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public ShieldMareTriggeredAbility copy() {
        return new ShieldMareTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                return event.getTargetId().equals(getSourceId());
            case TARGETED:
                break;
            default:
                return false;
        }
        return event.getTargetId().equals(this.getSourceId())
                && game.getOpponents(this.getControllerId()).contains(game.getControllerId(event.getSourceId()));
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield or becomes the target "
                + "of a spell or ability an opponent controls, you gain 3 life";
    }
}
