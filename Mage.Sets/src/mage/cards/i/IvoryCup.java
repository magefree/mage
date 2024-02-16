
package mage.cards.i;

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
public final class IvoryCup extends CardImpl {

    public IvoryCup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // Whenever a player casts a white spell, you may pay {1}. If you do, you gain 1 life.
        this.addAbility(new IvoryCupAbility());
    }

    private IvoryCup(final IvoryCup card) {
        super(card);
    }

    @Override
    public IvoryCup copy() {
        return new IvoryCup(this);
    }
}
class IvoryCupAbility extends TriggeredAbilityImpl {

    public IvoryCupAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new GainLifeEffect(1), new GenericManaCost(1)), false);
    }

    private IvoryCupAbility(final IvoryCupAbility ability) {
        super(ability);
    }

    @Override
    public IvoryCupAbility copy() {
        return new IvoryCupAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.getColor(game).isWhite();
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a white spell, you may pay {1}. If you do, you gain 1 life.";
    }

}