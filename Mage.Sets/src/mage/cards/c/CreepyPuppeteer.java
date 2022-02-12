package mage.cards.c;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CreepyPuppeteer extends CardImpl {

    public CreepyPuppeteer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Creepy Puppeteer attacks, if you attacked with exactly one other creature this combat, you may have that creature's base power and toughness become 4/3 until end of turn.
        this.addAbility(new CreepyPuppeteerTriggeredAbility());
    }

    private CreepyPuppeteer(final CreepyPuppeteer card) {
        super(card);
    }

    @Override
    public CreepyPuppeteer copy() {
        return new CreepyPuppeteer(this);
    }
}

class CreepyPuppeteerTriggeredAbility extends TriggeredAbilityImpl {

    CreepyPuppeteerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SetPowerToughnessTargetEffect(4, 3, Duration.EndOfTurn), true);
    }

    private CreepyPuppeteerTriggeredAbility(final CreepyPuppeteerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CreepyPuppeteerTriggeredAbility copy() {
        return new CreepyPuppeteerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(game.getCombat().getAttackingPlayerId())
                || !game.getCombat().getAttackers().contains(getSourceId())
                || game.getCombat().getAttackers().size() != 2) {
            return false;
        }
        UUID otherAttacker = game
                .getCombat()
                .getAttackers()
                .stream()
                .filter(uuid -> !getSourceId().equals(uuid))
                .findFirst()
                .orElse(null);
        if (otherAttacker == null) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(otherAttacker, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, if you attacked with exactly one other creature this combat, " +
                "you may have that creature's base power and toughness become 4/3 until end of turn.";
    }
}
