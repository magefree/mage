package mage.cards.d;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.SetBaseToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DaxosBlessedByTheSun extends CardImpl {

    public DaxosBlessedByTheSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMIGOD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(0);

        // Daxos's toughness is equal to your devotion to white.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBaseToughnessSourceEffect(DevotionCount.W, Duration.EndOfGame)
                        .setText("{this}'s toughness is equal to your devotion to white")
        ).addHint(DevotionCount.W.getHint()));

        // Whenever another creature you control enters the battlefield or dies, you gain 1 life.
        this.addAbility(new DaxosBlessedByTheSunAbility());
    }

    private DaxosBlessedByTheSun(final DaxosBlessedByTheSun card) {
        super(card);
    }

    @Override
    public DaxosBlessedByTheSun copy() {
        return new DaxosBlessedByTheSun(this);
    }
}

class DaxosBlessedByTheSunAbility extends TriggeredAbilityImpl {

    DaxosBlessedByTheSunAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1));
    }

    private DaxosBlessedByTheSunAbility(DaxosBlessedByTheSunAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD)
                || (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).isDiesEvent());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        Permanent creature = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return creature != null
                && creature.isCreature(game)
                && creature.isControlledBy(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever another creature you control enters the battlefield or dies, you gain 1 life.";
    }

    @Override
    public DaxosBlessedByTheSunAbility copy() {
        return new DaxosBlessedByTheSunAbility(this);
    }
}