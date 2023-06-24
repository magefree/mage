
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
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
 *
 * @author LevelX2
 */
public final class SeasClaim extends CardImpl {

    public SeasClaim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted land is an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesBasicLandEnchantedEffect(SubType.ISLAND)));
    }

    private SeasClaim(final SeasClaim card) {
        super(card);
    }

    @Override
    public SeasClaim copy() {
        return new SeasClaim(this);
    }
}
