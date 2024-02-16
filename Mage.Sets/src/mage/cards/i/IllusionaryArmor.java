
package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.BecomesTargetAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class IllusionaryArmor extends CardImpl {

    public IllusionaryArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant Creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));
        // Enchanted creature gets +4/+4.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(4, 4, Duration.WhileOnBattlefield)));
        // When enchanted creature becomes the target of a spell or ability, sacrifice Illusionary Armor.
        this.addAbility(new BecomesTargetAttachedTriggeredAbility(new SacrificeSourceEffect()));
    }

    private IllusionaryArmor(final IllusionaryArmor card) {
        super(card);
    }

    @Override
    public IllusionaryArmor copy() {
        return new IllusionaryArmor(this);
    }
}
