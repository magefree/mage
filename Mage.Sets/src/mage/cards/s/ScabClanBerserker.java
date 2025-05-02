
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.RenownedSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class ScabClanBerserker extends CardImpl {

    public ScabClanBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Renown 1
        this.addAbility(new RenownAbility(1));
        
        // Whenever an opponent casts a noncreature spell, if Scab-Clan Berserker is renowned, Scab-Clan Berserker deals 2 damage to that player.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new ScabClanBerserkerTriggeredAbility(),
                RenownedSourceCondition.instance,
                "Whenever an opponent casts a noncreature spell, if Scab-Clan Berserker is renowned, Scab-Clan Berserker deals 2 damage to that player"));
    }

    private ScabClanBerserker(final ScabClanBerserker card) {
        super(card);
    }

    @Override
    public ScabClanBerserker copy() {
        return new ScabClanBerserker(this);
    }
}

class ScabClanBerserkerTriggeredAbility extends TriggeredAbilityImpl {


    public ScabClanBerserkerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2, true, "that player"));
    }


    private ScabClanBerserkerTriggeredAbility(final ScabClanBerserkerTriggeredAbility abiltity) {
        super(abiltity);
    }

    @Override
    public ScabClanBerserkerTriggeredAbility copy() {
        return new ScabClanBerserkerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && !spell.isCreature(game)){
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a noncreature spell, if Scab-Clan Berserker is renowned, Scab-Clan Berserker deals 2 damage to that player";
    }
}