package mage.cards.i;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAttachedControllerEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class ImpendingDoom extends CardImpl {

    public ImpendingDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +3/+3 and attacks each combat if able.
        Effect effect = new BoostEnchantedEffect(3, 3, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +3/+3");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new AttacksIfAbleAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.AURA);
        effect.setText("and attacks each combat if able");
        ability.addEffect(effect);
        this.addAbility(ability);

        // When enchanted creature dies, Impending Doom deals 3 damage to that creature's controller.
        this.addAbility(new DiesAttachedTriggeredAbility(
                new DamageAttachedControllerEffect(3), "enchanted creature"));
    }

    private ImpendingDoom(final ImpendingDoom card) {
        super(card);
    }

    @Override
    public ImpendingDoom copy() {
        return new ImpendingDoom(this);
    }
}
