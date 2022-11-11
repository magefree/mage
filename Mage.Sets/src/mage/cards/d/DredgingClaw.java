package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DredgingClaw extends CardImpl {

    public DredgingClaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has menace.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                new MenaceAbility(false), AttachmentType.EQUIPMENT
        ).setText("and has menace. <i>(It can't be blocked except by two or more creatures.)</i>"));
        this.addAbility(ability);

        // Whenever a creature enters the battlefield from your graveyard, you may attach Dredging Claw to it.
        this.addAbility(new DredgingClawTriggeredAbility());

        // Equip {1}{B}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{1}{B}")));
    }

    private DredgingClaw(final DredgingClaw card) {
        super(card);
    }

    @Override
    public DredgingClaw copy() {
        return new DredgingClaw(this);
    }
}

class DredgingClawTriggeredAbility extends TriggeredAbilityImpl {

    DredgingClawTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AttachEffect(Outcome.BoostCreature), true);
    }

    private DredgingClawTriggeredAbility(final DredgingClawTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DredgingClawTriggeredAbility copy() {
        return new DredgingClawTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        EntersTheBattlefieldEvent etbEvent = (EntersTheBattlefieldEvent) event;
        return etbEvent.getTarget().isCreature(game)
                && etbEvent.getFromZone() == Zone.GRAVEYARD
                && isControlledBy(etbEvent.getTarget().getOwnerId());
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield from your graveyard, you may attach {this} to it.";
    }
}
