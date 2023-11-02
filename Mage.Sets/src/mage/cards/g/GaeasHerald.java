
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author Plopman
 */
public final class GaeasHerald extends CardImpl {

    public GaeasHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Creature spells can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantCounterEffect()));
    }

    private GaeasHerald(final GaeasHerald card) {
        super(card);
    }

    @Override
    public GaeasHerald copy() {
        return new GaeasHerald(this);
    }
}


class CantCounterEffect extends ContinuousRuleModifyingEffectImpl {

    public CantCounterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creature spells can't be countered";
    }


    private CantCounterEffect(final CantCounterEffect effect) {
        super(effect);
    }

    @Override
    public CantCounterEffect copy() {
        return new CantCounterEffect(this);
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
