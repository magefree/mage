
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class RuricTharTheUnbowed extends CardImpl {

    public RuricTharTheUnbowed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        
        // Reach
        this.addAbility(ReachAbility.getInstance());
        
        // Ruric Thar, the Unbowed attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
        
        // Whenever a player casts a noncreature spell, Ruric Thar deals 6 damage to that player.
        this.addAbility(new RuricTharTheUnbowedAbility());
    }

    private RuricTharTheUnbowed(final RuricTharTheUnbowed card) {
        super(card);
    }

    @Override
    public RuricTharTheUnbowed copy() {
        return new RuricTharTheUnbowed(this);
    }
}

class RuricTharTheUnbowedAbility extends TriggeredAbilityImpl {

    public RuricTharTheUnbowedAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(6), false);
    }

    public RuricTharTheUnbowedAbility(final RuricTharTheUnbowedAbility ability) {
        super(ability);
    }

    @Override
    public RuricTharTheUnbowedAbility copy() {
        return new RuricTharTheUnbowedAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && !spell.isCreature(game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a noncreature spell, Ruric Thar deals 6 damage to that player.";
    }
}
