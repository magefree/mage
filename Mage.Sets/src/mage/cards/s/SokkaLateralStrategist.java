package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SokkaLateralStrategist extends CardImpl {

    public SokkaLateralStrategist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/U}{W/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Sokka and at least one other creature attack, draw a card.
        this.addAbility(new SokkaLateralStrategistTriggeredAbility());
    }

    private SokkaLateralStrategist(final SokkaLateralStrategist card) {
        super(card);
    }

    @Override
    public SokkaLateralStrategist copy() {
        return new SokkaLateralStrategist(this);
    }
}

class SokkaLateralStrategistTriggeredAbility extends TriggeredAbilityImpl {

    SokkaLateralStrategistTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.setTriggerPhrase("Whenever {this} and at least one other creature attack, ");
    }

    private SokkaLateralStrategistTriggeredAbility(final SokkaLateralStrategistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SokkaLateralStrategistTriggeredAbility copy() {
        return new SokkaLateralStrategistTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= 2 && game.getCombat().getAttackers().contains(getSourceId());
    }
}
