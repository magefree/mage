
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DragonGrip extends CardImpl {

    public DragonGrip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");
        this.subtype.add(SubType.AURA);

        // Ferocious - If you control a creature with power 4 or greater, you may cast Dragon Grip as though it had flash.
        AsThoughEffect effect = new CastAsThoughItHadFlashSourceEffect(Duration.EndOfGame);
        effect.setText("<i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, you may cast Dragon Grip as though it had flash");
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ConditionalAsThoughEffect(effect,
                FerociousCondition.instance)));

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +2/+0 and has first strike.
        Effect effect2 = new BoostEnchantedEffect(2, 0, Duration.WhileOnBattlefield);
        effect2.setText("Enchanted creature gets +2/+0");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect2);
        effect2 = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA);
        effect2.setText("and has first strike");
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    public DragonGrip(final DragonGrip card) {
        super(card);
    }

    @Override
    public DragonGrip copy() {
        return new DragonGrip(this);
    }
}
