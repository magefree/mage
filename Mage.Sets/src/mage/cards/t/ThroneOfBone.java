
package mage.cards.t;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;

/**
 *
 * @author KholdFuzion

 */
public final class ThroneOfBone extends CardImpl {

    public ThroneOfBone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // Whenever a player casts a black spell, you may pay {1}. If you do, you gain 1 life.
        this.addAbility(new ThroneOfBoneAbility());
    }

    private ThroneOfBone(final ThroneOfBone card) {
        super(card);
    }

    @Override
    public ThroneOfBone copy() {
        return new ThroneOfBone(this);
    }
}

class ThroneOfBoneAbility extends TriggeredAbilityImpl {

    public ThroneOfBoneAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new GainLifeEffect(1), new GenericManaCost(1)), false);
    }

    private ThroneOfBoneAbility(final ThroneOfBoneAbility ability) {
        super(ability);
    }

    @Override
    public ThroneOfBoneAbility copy() {
        return new ThroneOfBoneAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.getColor(game).isBlack();
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a black spell, you may pay {1}. If you do, you gain 1 life.";
    }

}