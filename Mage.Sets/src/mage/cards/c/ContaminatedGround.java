
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseLifeControllerAttachedEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ContaminatedGround extends CardImpl {

    public ContaminatedGround(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted land is a Swamp.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesBasicLandEnchantedEffect(SubType.SWAMP)));

        // Whenever enchanted land becomes tapped, its controller loses 2 life.
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(new LoseLifeControllerAttachedEffect(2), "enchanted land"));
    }

    private ContaminatedGround(final ContaminatedGround card) {
        super(card);
    }

    @Override
    public ContaminatedGround copy() {
        return new ContaminatedGround(this);
    }
}
