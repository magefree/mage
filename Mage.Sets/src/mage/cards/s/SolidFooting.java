package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SolidFooting extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new AbilityPredicate(VigilanceAbility.class));
    }

    private static final Condition condition = new AttachedToMatchesFilterCondition(filter);

    public SolidFooting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(1, 1)));

        // As long as enchanted creature has vigilance, it assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(new CombatDamageByToughnessAttachedEffect(
                condition, "as long as enchanted creature has vigilance, " +
                "it assigns combat damage equal to its toughness rather than its power"
        )));
    }

    private SolidFooting(final SolidFooting card) {
        super(card);
    }

    @Override
    public SolidFooting copy() {
        return new SolidFooting(this);
    }
}
