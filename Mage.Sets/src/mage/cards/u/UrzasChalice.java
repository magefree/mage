
package mage.cards.u;

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
import mage.game.stack.Spell;

/**
 *
 * @author MarcoMarin
 */
public final class UrzasChalice extends CardImpl {

    public UrzasChalice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // Whenever a player casts an artifact spell, you may pay {1}. If you do, you gain 1 life.
        this.addAbility(new UrzasChaliceAbility());
    }

    private UrzasChalice(final UrzasChalice card) {
        super(card);
    }

    @Override
    public UrzasChalice copy() {
        return new UrzasChalice(this);
    }
}
class UrzasChaliceAbility extends TriggeredAbilityImpl {

    public UrzasChaliceAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new GainLifeEffect(1), new GenericManaCost(1)), false);
    }

    private UrzasChaliceAbility(final UrzasChaliceAbility ability) {
        super(ability);
    }

    @Override
    public UrzasChaliceAbility copy() {
        return new UrzasChaliceAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.isArtifact(game);
    }

    @Override
    public String getRule() {
        return "Whenever a player casts an artifact spell, you may pay {1}. If you do, you gain 1 life.";
    }

}