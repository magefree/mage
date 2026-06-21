package mage.cards.p;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class PhyrexianTyranny extends CardImpl {

    public PhyrexianTyranny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{B}{R}");

        // Whenever a player draws a card, that player loses 2 life unless they pay {2}.
        this.addAbility(new PhyrexianTyrannyTriggeredAbility());
    }

    private PhyrexianTyranny(final PhyrexianTyranny card) {
        super(card);
    }

    @Override
    public PhyrexianTyranny copy() {
        return new PhyrexianTyranny(this);
    }
}

class PhyrexianTyrannyTriggeredAbility extends TriggeredAbilityImpl {

    PhyrexianTyrannyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new LoseLifeTargetEffect(2), new ManaCostsImpl<>("{2}")).withTheyText(), false);
    }

    private PhyrexianTyrannyTriggeredAbility(final PhyrexianTyrannyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PhyrexianTyrannyTriggeredAbility copy() {
        return new PhyrexianTyrannyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
        }
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a player draws a card, that player loses 2 life unless they pay {2}.";
    }
}
