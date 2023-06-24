

package mage.cards.n;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class NimbusWings extends CardImpl {

    public NimbusWings (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));
        // Enchanted creature gets +1/+2 and has flying.
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 2, Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA).setText("and has flying"));
        this.addAbility(ability);
    }

    public NimbusWings (final NimbusWings card) {
        super(card);
    }

    @Override
    public NimbusWings copy() {
        return new NimbusWings(this);
    }
}
