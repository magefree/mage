
package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyAttachedToEffect;
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
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox & L_J
 */
public final class Erosion extends CardImpl {

    public Erosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of the upkeep of enchanted land's controller, destroy that land unless that player pays {1} or 1 life.
        Effect effect = new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new DestroyAttachedToEffect("enchanted land"), new OrCost("{1} or 1 life", new ManaCostsImpl<>("{1}"), new PayLifeCost(1)));
        effect.setText("destroy that land unless that player pays {1} or 1 life");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.CONTROLLER_ATTACHED_TO, false, true, "At the beginning of the upkeep of enchanted land's controller, "));
    }

    private Erosion(final Erosion card) {
        super(card);
    }

    @Override
    public Erosion copy() {
        return new Erosion(this);
    }
}
