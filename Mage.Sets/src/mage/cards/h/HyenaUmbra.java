package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TotemArmorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HyenaUmbra extends CardImpl {

    public HyenaUmbra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature gets +1/+1 and has first strike.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(
                1, 1, Duration.WhileOnBattlefield
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.AURA
        ).setText("and has first strike"));
        this.addAbility(ability);

        // Totem armor
        this.addAbility(new TotemArmorAbility());
    }

    private HyenaUmbra(final HyenaUmbra card) {
        super(card);
    }

    @Override
    public HyenaUmbra copy() {
        return new HyenaUmbra(this);
    }
}
