package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FrenziedFugue extends CardImpl {

    public FrenziedFugue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // When Frenzied Fugue enters the battlefield or at the beginning of your upkeep, gain control of enchanted permanent until end of turn. Untap that permanent. It gains haste until end of turn.
        this.addAbility(new FrenziedFugueTriggeredAbility());
    }

    private FrenziedFugue(final FrenziedFugue card) {
        super(card);
    }

    @Override
    public FrenziedFugue copy() {
        return new FrenziedFugue(this);
    }
}

class FrenziedFugueTriggeredAbility extends TriggeredAbilityImpl {

    public FrenziedFugueTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public FrenziedFugueTriggeredAbility(final FrenziedFugueTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FrenziedFugueTriggeredAbility copy() {
        return new FrenziedFugueTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD || event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        this.getEffects().clear();
        boolean result;
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            result = event.getTargetId().equals(this.getSourceId());
        } else {
            result = event.getPlayerId().equals(this.getControllerId());
        }
        if (result) {
            Permanent enchantment = game.getPermanentOrLKIBattlefield(getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Effect effect = new GainControlTargetEffect(Duration.EndOfTurn, true);
                effect.setTargetPointer(new FixedTarget(enchantment.getAttachedTo(), game));
                this.getEffects().add(effect);
                effect = new UntapTargetEffect();
                effect.setTargetPointer(new FixedTarget(enchantment.getAttachedTo(), game));
                this.getEffects().add(effect);
                effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(enchantment.getAttachedTo(), game));
                this.getEffects().add(effect);
            } else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield or at the beginning of your upkeep, gain control of enchanted permanent until end of turn. Untap that permanent. It gains haste until end of turn.";
    }
}
