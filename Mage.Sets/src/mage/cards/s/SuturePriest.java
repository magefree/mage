
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public final class SuturePriest extends CardImpl {

    public SuturePriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another creature enters the battlefield under your control, you may gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(1),
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true));

        // Whenever a creature enters the battlefield under an opponent's control, you may have that player lose 1 life.
        this.addAbility(new SuturePriestSecondTriggeredAbility());

    }

    private SuturePriest(final SuturePriest card) {
        super(card);
    }

    @Override
    public SuturePriest copy() {
        return new SuturePriest(this);
    }
}

class SuturePriestSecondTriggeredAbility extends TriggeredAbilityImpl {

    SuturePriestSecondTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), true);
    }

    private SuturePriestSecondTriggeredAbility(final SuturePriestSecondTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SuturePriestSecondTriggeredAbility copy() {
        return new SuturePriestSecondTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            EntersTheBattlefieldEvent zEvent = (EntersTheBattlefieldEvent) event;
            Card card = zEvent.getTarget();
            if (card != null && card.isCreature(game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield under an opponent's control, you may have that player lose 1 life.";
    }
}
