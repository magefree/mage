
package mage.cards.f;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class FlashConscription extends CardImpl {

    public FlashConscription(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{R}");

        // Untap target creature and gain control of it until end of turn. That creature gains haste until end of turn. If {W} was spent to cast Flash Conscription, the creature gains "Whenever this creature deals combat damage, you gain that much life" until end of turn.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn).setText("and gain control of it until end of turn"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("That creature gains haste until end of turn"));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(new FlashConscriptionTriggeredAbility(), Duration.EndOfTurn),
                ManaWasSpentCondition.WHITE,
                "If {W} was spent to cast this spell, the creature gains "
                + "\"Whenever this creature deals combat damage, you gain that much life\" until end of turn"
        ));

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FlashConscription(final FlashConscription card) {
        super(card);
    }

    @Override
    public FlashConscription copy() {
        return new FlashConscription(this);
    }
}

class FlashConscriptionTriggeredAbility extends TriggeredAbilityImpl {

    public FlashConscriptionTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public FlashConscriptionTriggeredAbility(final FlashConscriptionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FlashConscriptionTriggeredAbility copy() {
        return new FlashConscriptionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedEvent damageEvent = (DamagedEvent) event;
        if (damageEvent.isCombatDamage()) {
            if (event.getSourceId().equals(this.sourceId)) {
                this.getEffects().clear();
                this.getEffects().add(new GainLifeEffect(damageEvent.getAmount()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage, you gain that much life.";
    }
}
