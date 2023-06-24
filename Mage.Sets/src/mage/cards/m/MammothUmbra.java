package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TotemArmorAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class MammothUmbra extends CardImpl {

    public MammothUmbra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +3/+3 and has vigilance.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(
                3, 3, Duration.WhileOnBattlefield
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.AURA
        ).setText("and has vigilance"));
        this.addAbility(ability);

        // Totem armor
        this.addAbility(new TotemArmorAbility());
    }

    private MammothUmbra(final MammothUmbra card) {
        super(card);
    }

    @Override
    public MammothUmbra copy() {
        return new MammothUmbra(this);
    }
}
