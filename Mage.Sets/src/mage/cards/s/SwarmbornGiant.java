package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
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
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePlayerEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;

import java.util.UUID;

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

class SwarmbornGiantTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

    SwarmbornGiantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect(), false);
        setTriggerPhrase("When you're dealt combat damage, ");
    }

    private SwarmbornGiantTriggeredAbility(final SwarmbornGiantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SwarmbornGiantTriggeredAbility copy() {
        return new SwarmbornGiantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // all events in the batch are always relevant
        if (isControlledBy(event.getTargetId()) && ((DamagedBatchForOnePlayerEvent) event).isCombatDamage()) {
            this.getAllEffects().setValue("damage", event.getAmount());
            return true;
        }
        return false;
    }
}
