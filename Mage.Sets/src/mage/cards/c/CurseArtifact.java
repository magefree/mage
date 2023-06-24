
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeAttachedCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LoneFox & L_J
 */
public final class CurseArtifact extends CardImpl {

    public CurseArtifact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of the upkeep of enchanted artifact's controller, Curse Artifact deals 2 damage to that player unless they sacrifice that artifact.
        Cost cost = new SacrificeAttachedCost();
        cost.setText("sacrifice attached artifact");
        Effect effect = new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new DamageTargetEffect(2), cost, "Sacrifice enchanted artifact? (otherwise {this} deals 2 damage to you)");
        effect.setText("{this} deals 2 damage to that player unless they sacrifice that artifact");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.CONTROLLER_ATTACHED_TO, false, true, "At the beginning of the upkeep of enchanted artifact's controller, "));
    }

    private CurseArtifact(final CurseArtifact card) {
        super(card);
    }

    @Override
    public CurseArtifact copy() {
        return new CurseArtifact(this);
    }
}
