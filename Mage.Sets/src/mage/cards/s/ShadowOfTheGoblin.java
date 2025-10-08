package mage.cards.s;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class ShadowOfTheGoblin extends CardImpl {

    public ShadowOfTheGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        

        // Unreliable Visions -- At the beginning of your first main phase, discard a card. If you do, draw a card.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()), false)
                .withFlavorWord("Unreliable Visions")
        );

        // Undying Vengeance -- Whenever you play a land or cast a spell from anywhere other than your hand, this enchantment deals 1 damage to each opponent.
        this.addAbility(new ShadowOfTheGoblinTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT))
                .withFlavorWord("Undying Vengeance"));

    }

    private ShadowOfTheGoblin(final ShadowOfTheGoblin card) {
        super(card);
    }

    @Override
    public ShadowOfTheGoblin copy() {
        return new ShadowOfTheGoblin(this);
    }
}

class ShadowOfTheGoblinTriggeredAbility extends TriggeredAbilityImpl {

    ShadowOfTheGoblinTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever you play a land or cast a spell from anywhere other than your hand, ");
    }

    protected ShadowOfTheGoblinTriggeredAbility(final ShadowOfTheGoblinTriggeredAbility triggeredAbility) {
        super(triggeredAbility);
    }

    @Override
    public ShadowOfTheGoblinTriggeredAbility copy() {
        return new ShadowOfTheGoblinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND ||
                event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId()) && event.getZone() != Zone.HAND;
    }
}
