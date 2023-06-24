
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.ReplacementEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.PreventAllDamageByAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ArmamentOfNyx extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("enchantment");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public ArmamentOfNyx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        Condition condition = new AttachedToMatchesFilterCondition(filter);
        // Enchanted creature has double strike as long as it's an enchantment. Otherwise, prevent all damage that would be dealt by enchanted creature
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(DoubleStrikeAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield),
                condition, "Enchanted creature has double strike as long as it's an enchantment"));
        ReplacementEffect effect = new PreventAllDamageByAttachedEffect(Duration.WhileOnBattlefield, "enchanted creature", false);
        effect.setText("Otherwise, prevent all damage that would be dealt by enchanted creature");
        ability.addEffect(new ConditionalReplacementEffect(effect, new InvertCondition(condition)));
        this.addAbility(ability);
    }

    private ArmamentOfNyx(final ArmamentOfNyx card) {
        super(card);
    }

    @Override
    public ArmamentOfNyx copy() {
        return new ArmamentOfNyx(this);
    }
}
