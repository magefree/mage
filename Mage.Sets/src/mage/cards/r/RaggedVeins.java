

package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.DealtDamageAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.common.NumericSetToEffectValues;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RaggedVeins extends CardImpl {

    public RaggedVeins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);


        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever enchanted creature is dealt damage, its controller loses that much life.
        Effect effect = new LoseLifeTargetEffect(new NumericSetToEffectValues("that much", "damage"));
        effect.setText("its controller loses that much life");
        this.addAbility(new DealtDamageAttachedTriggeredAbility(Zone.BATTLEFIELD, effect, false, SetTargetPointer.PLAYER));
    }

    private RaggedVeins(final RaggedVeins card) {
        super(card);
    }

    @Override
    public RaggedVeins copy() {
        return new RaggedVeins(this);
    }
}
