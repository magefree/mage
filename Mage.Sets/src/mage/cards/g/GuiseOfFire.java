
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author noxx
 */
public final class GuiseOfFire extends CardImpl {

    public GuiseOfFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/-1 and attacks each turn if able.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, -1, Duration.WhileOnBattlefield));
        Effect effect = new AttacksIfAbleAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.AURA);
        effect.setText("and attacks each combat if able");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private GuiseOfFire(final GuiseOfFire card) {
        super(card);
    }

    @Override
    public GuiseOfFire copy() {
        return new GuiseOfFire(this);
    }
}
