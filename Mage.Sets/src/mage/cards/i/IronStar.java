
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
public final class IronStar extends CardImpl {

    public IronStar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // Whenever a player casts a red spell, you may pay {1}. If you do, you gain 1 life.
        this.addAbility(new IronStarAbility());
    }

    private IronStar(final IronStar card) {
        super(card);
    }

    @Override
    public IronStar copy() {
        return new IronStar(this);
    }
}

class IronStarAbility extends TriggeredAbilityImpl {

    public IronStarAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new GainLifeEffect(1), new GenericManaCost(1)), false);
    }

    private IronStarAbility(final IronStarAbility ability) {
        super(ability);
    }

    @Override
    public IronStarAbility copy() {
        return new IronStarAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.getColor(game).isRed();
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a red spell, you may pay {1}. If you do, you gain 1 life.";
    }

}