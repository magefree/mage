
package mage.cards.r;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.PhyrexianManaCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetAnyTarget;

/**
 * @author Loki
 */
public final class RageExtractor extends CardImpl {

    public RageExtractor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}{R/P}");


        this.addAbility(new RageExtractorTriggeredAbility());
    }

    private RageExtractor(final RageExtractor card) {
        super(card);
    }

    @Override
    public RageExtractor copy() {
        return new RageExtractor(this);
    }
}

class RageExtractorTriggeredAbility extends TriggeredAbilityImpl {
    RageExtractorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(0));
        this.addTarget(new TargetAnyTarget());
    }

    RageExtractorTriggeredAbility(final RageExtractorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RageExtractorTriggeredAbility copy() {
        return new RageExtractorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.controllerId)) {
            Spell spell = (Spell) game.getStack().getStackObject(event.getTargetId());
            if (spell != null) {
                for (ManaCost cost : spell.getCard().getManaCost()) {
                    if (cost instanceof PhyrexianManaCost) {
                        ((DamageTargetEffect)getEffects().get(0)).setAmount(StaticValue.get(spell.getManaValue()));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell with {P} in its mana cost, {this} deals damage equal to that spell's mana value to any target.";
    }
}
