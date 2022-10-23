
package mage.cards.a;

import java.util.UUID;
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
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

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

    AbilityActivatedTriggeredAbility(final AbilityActivatedTriggeredAbility ability) {
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
        UUID tappedPermanent = null;
        if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
            tappedPermanent = event.getSourceId();
        }
        if (event.getType() == GameEvent.EventType.TAPPED) {
            tappedPermanent = event.getTargetId();
        }

        Permanent aura = game.getPermanent(this.getSourceId());
        return aura != null && aura.isAttachedTo(tappedPermanent);
    }

    @Override
    public String getRule() {
        return "Whenever enchanted artifact becomes tapped or a player activates an ability of enchanted artifact without {tap} in its activation cost, Artifact Possession deals 2 damage to that artifact's controller.";
    }
}
