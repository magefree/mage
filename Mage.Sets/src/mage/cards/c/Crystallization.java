
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class Crystallization extends CardImpl {

    public Crystallization(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G/U}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't attack or block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackBlockAttachedEffect(AttachmentType.AURA)));

        // When enchanted creature becomes the target of a spell or ability, exile that creature.
        this.addAbility(new CrystallizationTriggeredAbility());
    }

    private Crystallization(final Crystallization card) {
        super(card);
    }

    @Override
    public Crystallization copy() {
        return new Crystallization(this);
    }
}

class CrystallizationTriggeredAbility extends TriggeredAbilityImpl {

    public CrystallizationTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTargetEffect());
    }

    public CrystallizationTriggeredAbility(final CrystallizationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CrystallizationTriggeredAbility copy() {
        return new CrystallizationTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(sourceId);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            UUID enchanted = enchantment.getAttachedTo();
            if (event.getTargetId().equals(enchanted)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(enchanted, game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted creature becomes the target of a spell or ability, exile that creature.";
    }
}
