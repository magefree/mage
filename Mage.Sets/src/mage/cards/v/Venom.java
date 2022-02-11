package mage.cards.v;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Backfir3
 */
public final class Venom extends CardImpl {

    public Venom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature blocks or becomes blocked by a non-Wall creature, destroy the other creature at end of combat.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect()), true);
        effect.setText("destroy that creature at end of combat");
        this.addAbility(new VenomTriggeredAbility(effect));
    }

    private Venom(final Venom card) {
        super(card);
    }

    @Override
    public Venom copy() {
        return new Venom(this);
    }
}

class VenomTriggeredAbility extends TriggeredAbilityImpl {

    VenomTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    VenomTriggeredAbility(final VenomTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VenomTriggeredAbility copy() {
        return new VenomTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent blocker = game.getPermanent(event.getSourceId());
        Permanent blocked = game.getPermanent(event.getTargetId());
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent enchantedCreature = game.getPermanent(enchantment.getAttachedTo());
            if (enchantedCreature != null) {
                if (blocker != null
                        && !Objects.equals(blocker, enchantedCreature)
                        && !blocker.hasSubtype(SubType.WALL, game)
                        && Objects.equals(blocked, enchantedCreature)) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(blocker.getId(), game));
                    return true;
                }
                if (blocker != null
                        && Objects.equals(blocker, enchantedCreature)
                        && !blocked.hasSubtype(SubType.WALL, game)) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(blocked.getId(), game));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature blocks or becomes blocked by a non-Wall creature, destroy that creature at end of combat.";
    }
}
