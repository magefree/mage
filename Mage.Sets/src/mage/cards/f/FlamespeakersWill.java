
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class FlamespeakersWill extends CardImpl {

    public FlamespeakersWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}");
        this.subtype.add(SubType.AURA);


        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1,1, Duration.WhileOnBattlefield)));
        // Whenever enchanted creature deals combat damage to a player, you may sacrifice Flamespeaker's Will. If you do, destroy target artifact.
        ability = new DealsDamageToAPlayerAttachedTriggeredAbility(
                new DoIfCostPaid(new DestroyTargetEffect(), new SacrificeSourceCost()), "enchanted creature", false, false, true, TargetController.ANY);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private FlamespeakersWill(final FlamespeakersWill card) {
        super(card);
    }

    @Override
    public FlamespeakersWill copy() {
        return new FlamespeakersWill(this);
    }
}
