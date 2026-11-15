
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAttachedControllerEffect;
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
import mage.game.stack.StackObject;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 *
 * @author MarcoMarin
 */
public final class ArtifactPossession extends CardImpl {

    public ArtifactPossession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Whenever enchanted artifact becomes tapped or a player activates an ability of enchanted artifact without {tap} in its activation cost, Artifact Possession deals 2 damage to that artifact's controller.
        this.addAbility(new AbilityActivatedTriggeredAbility());

    }

    private ArtifactPossession(final ArtifactPossession card) {
        super(card);
    }

    @Override
    public ArtifactPossession copy() {
        return new ArtifactPossession(this);
    }
}

class AbilityActivatedTriggeredAbility extends TriggeredAbilityImpl {

    AbilityActivatedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageAttachedControllerEffect(2));
    }

    private AbilityActivatedTriggeredAbility(final AbilityActivatedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AbilityActivatedTriggeredAbility copy() {
        return new AbilityActivatedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY ||
               event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID artifactId;
        if (event.getType() == GameEvent.EventType.TAPPED) {
            artifactId = event.getTargetId();
        } else {
            // abilities with {T} trigger by the tapped event, mana abilities have no stack object (605.3b)
            StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
            Ability ability = stackObject != null
                    ? stackObject.getStackAbility()
                    : game.getAbility(event.getTargetId(), event.getSourceId()).orElse(null);
            if (ability == null || ability.hasTapCost()) {
                return false;
            }
            artifactId = event.getSourceId();
        }

        Permanent aura = game.getPermanent(this.getSourceId());
        return aura != null && aura.isAttachedTo(artifactId);
    }

    @Override
    public String getRule() {
        return "Whenever enchanted artifact becomes tapped or a player activates an ability of enchanted artifact without {T} in its activation cost, {this} deals 2 damage to that artifact's controller.";
    }
}
