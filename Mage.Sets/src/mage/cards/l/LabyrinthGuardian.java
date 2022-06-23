
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.EmbalmAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class LabyrinthGuardian extends CardImpl {

    public LabyrinthGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Labyrinth Guardian becomes the target of a spell, sacrifice it.
        this.addAbility(new LabyrinthGuardianTriggeredAbility());

        // Embalm {3}{U}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{3}{U}"), this));

    }

    private LabyrinthGuardian(final LabyrinthGuardian card) {
        super(card);
    }

    @Override
    public LabyrinthGuardian copy() {
        return new LabyrinthGuardian(this);
    }
}

class LabyrinthGuardianTriggeredAbility extends TriggeredAbilityImpl {

    public LabyrinthGuardianTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect(), false);
    }

    public LabyrinthGuardianTriggeredAbility(final LabyrinthGuardianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LabyrinthGuardianTriggeredAbility copy() {
        return new LabyrinthGuardianTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject eventSourceObject = game.getObject(event.getSourceId());
        if (event.getTargetId().equals(this.getSourceId()) && eventSourceObject instanceof Spell) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} becomes the target of a spell, sacrifice it.";
    }

}
