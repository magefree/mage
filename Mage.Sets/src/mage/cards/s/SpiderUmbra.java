package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ReachAbility;
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
public final class SpiderUmbra extends CardImpl {

    public SpiderUmbra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature gets +1/+1 and has reach.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(
                1, 1, Duration.WhileOnBattlefield
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                ReachAbility.getInstance(), AttachmentType.AURA
        ).setText("and has reach"));
        this.addAbility(ability);
        this.addAbility(new TotemArmorAbility());
    }

    private SpiderUmbra(final SpiderUmbra card) {
        super(card);
    }

    @Override
    public SpiderUmbra copy() {
        return new SpiderUmbra(this);
    }
}
