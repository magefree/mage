
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author emerald000
 */
public final class LeylineOfLifeforce extends CardImpl {

    public LeylineOfLifeforce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");


        // If Leyline of Lifeforce is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());
        
        // Creature spells can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LeylineOfLifeforceEffect()));
    }

    private LeylineOfLifeforce(final LeylineOfLifeforce card) {
        super(card);
    }

    @Override
    public LeylineOfLifeforce copy() {
        return new LeylineOfLifeforce(this);
    }
}

class LeylineOfLifeforceEffect extends ContinuousRuleModifyingEffectImpl {

    LeylineOfLifeforceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creature spells can't be countered";
    }

    private LeylineOfLifeforceEffect(final LeylineOfLifeforceEffect effect) {
        super(effect);
    }

    @Override
    public LeylineOfLifeforceEffect copy() {
        return new LeylineOfLifeforceEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.isCreature(game)) {
            return true;
        }
        return false;
    }
}
