package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScorchSpitter extends CardImpl {

    public ScorchSpitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Scorch Spitter attacks, it deals 1 damage to the player or planeswalker it's attacking.
        this.addAbility(new ScorchSpitterTriggeredAbility());
    }

    private ScorchSpitter(final ScorchSpitter card) {
        super(card);
    }

    @Override
    public ScorchSpitter copy() {
        return new ScorchSpitter(this);
    }
}

class ScorchSpitterTriggeredAbility extends TriggeredAbilityImpl {

    ScorchSpitterTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private ScorchSpitterTriggeredAbility(final ScorchSpitterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScorchSpitterTriggeredAbility copy() {
        return new ScorchSpitterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (this.getSourceId().equals(event.getSourceId())) {
            this.getEffects().clear();
            Effect effect = new DamageTargetEffect(1);
            effect.setTargetPointer(new FixedTarget(game.getCombat().getDefenderId(event.getSourceId()), game));
            this.addEffect(effect);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, it deals 1 damage to the player or planeswalker it's attacking.";
    }
}
