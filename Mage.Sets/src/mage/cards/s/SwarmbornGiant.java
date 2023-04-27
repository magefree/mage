
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author LevelX2
 */
public final class SwarmbornGiant extends CardImpl {

    public SwarmbornGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever you're dealt combat damage, sacrifice Swarmborn Giant.
        this.addAbility(new SwarmbornGiantTriggeredAbility());

        // {4}{G}{G}: Monstrosity 2.
        this.addAbility(new MonstrosityAbility("{4}{G}{G}", 2));

        // As long as Swarmborn Giant is monstrous, it has reach.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(ReachAbility.getInstance(), Duration.WhileOnBattlefield),
                        MonstrousCondition.instance,
                        "As long as {this} is monstrous, it has reach"));
        this.addAbility(ability);
    }

    private SwarmbornGiant(final SwarmbornGiant card) {
        super(card);
    }

    @Override
    public SwarmbornGiant copy() {
        return new SwarmbornGiant(this);
    }
}

class SwarmbornGiantTriggeredAbility extends TriggeredAbilityImpl {

    public SwarmbornGiantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect(), false);
        setTriggerPhrase("When you're dealt combat damage, ");
    }

    public SwarmbornGiantTriggeredAbility(final SwarmbornGiantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SwarmbornGiantTriggeredAbility copy() {
        return new SwarmbornGiantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getControllerId())) {
            DamagedEvent damagedEvent = (DamagedEvent) event;
            return damagedEvent.isCombatDamage();
        }
        return false;
    }
}
