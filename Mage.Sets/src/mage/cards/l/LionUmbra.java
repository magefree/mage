package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TotemArmorAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LionUmbra extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("modified creature");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public LionUmbra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant modified creature
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +3/+3 and has vigilance and reach.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(3, 3));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.AURA
        ).setText("and has vigilance"));
        ability.addEffect(new GainAbilityAttachedEffect(
                ReachAbility.getInstance(), AttachmentType.AURA
        ).setText("and reach"));
        this.addAbility(ability);

        // Totem armor
        this.addAbility(new TotemArmorAbility());
    }

    private LionUmbra(final LionUmbra card) {
        super(card);
    }

    @Override
    public LionUmbra copy() {
        return new LionUmbra(this);
    }
}
