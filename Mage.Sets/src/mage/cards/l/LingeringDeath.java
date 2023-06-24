package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class LingeringDeath extends CardImpl {

    public LingeringDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.DestroyPermanent));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of the end step of enchanted creature's controller, that player sacrifices that creature.
        this.addAbility(new LingeringDeathAbility());
    }

    private LingeringDeath(final LingeringDeath card) {
        super(card);
    }

    @Override
    public LingeringDeath copy() {
        return new LingeringDeath(this);
    }
}

class LingeringDeathAbility extends TriggeredAbilityImpl {

    public LingeringDeathAbility() {
        super(Zone.BATTLEFIELD, new SacrificeTargetEffect());
    }

    public LingeringDeathAbility(final LingeringDeathAbility ability) {
        super(ability);
    }

    @Override
    public LingeringDeathAbility copy() {
        return new LingeringDeathAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(this.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent enchantedCreature = game.getPermanent(enchantment.getAttachedTo());
            if (enchantedCreature != null) {
                if (event.getPlayerId().equals(enchantedCreature.getControllerId())) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(enchantment.getAttachedTo(), game));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of the end step of enchanted creature's controller, that player sacrifices that creature.";
    }
}
