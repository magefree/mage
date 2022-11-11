
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public final class CurseOfStalkedPrey extends CardImpl {

    public CurseOfStalkedPrey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        this.subtype.add(SubType.AURA, SubType.CURSE);


        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever a creature deals combat damage to enchanted player, put a +1/+1 counter on that creature.
        this.addAbility(new CurseOfStalkedPreyTriggeredAbility());

    }

    private CurseOfStalkedPrey(final CurseOfStalkedPrey card) {
        super(card);
    }

    @Override
    public CurseOfStalkedPrey copy() {
        return new CurseOfStalkedPrey(this);
    }
}

class CurseOfStalkedPreyTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfStalkedPreyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
    }

    public CurseOfStalkedPreyTriggeredAbility(final CurseOfStalkedPreyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfStalkedPreyTriggeredAbility copy() {
        return new CurseOfStalkedPreyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent enchantment = game.getPermanent(this.sourceId);
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Player player = game.getPlayer(enchantment.getAttachedTo());
                if (player != null && event.getTargetId().equals(player.getId())) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(event.getSourceId(), game));
                       return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to enchanted player, put a +1/+1 counter on that creature";
    }

}
