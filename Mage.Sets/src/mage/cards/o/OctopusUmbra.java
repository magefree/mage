package mage.cards.o;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessEnchantedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TotemArmorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author TheElk801
 */
public final class OctopusUmbra extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 8 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 9));
    }

    public OctopusUmbra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature has base power and toughness 8/8 and has "Whenever this creature attacks, you may tap target creature with power 8 or less."
        Ability abilityToAdd = new AttacksTriggeredAbility(new TapTargetEffect(), true);
        abilityToAdd.addTarget(new TargetCreaturePermanent(filter));
        ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new SetBasePowerToughnessEnchantedEffect(8, 8)
        );
        ability.addEffect(new GainAbilityAttachedEffect(
                abilityToAdd, AttachmentType.AURA
        ).setText("and has \"Whenever this creature attacks, "
                + "you may tap target creature with power 8 or less.\""));
        this.addAbility(ability);

        // Totem armor
        this.addAbility(new TotemArmorAbility());

    }

    private OctopusUmbra(final OctopusUmbra card) {
        super(card);
    }

    @Override
    public OctopusUmbra copy() {
        return new OctopusUmbra(this);
    }
}
