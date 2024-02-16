
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class NyxInfusion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("enchantment");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public NyxInfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");
        this.subtype.add(SubType.AURA);



        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 as long as it's an enchantment. Otherwise, it gets -2/-2.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostEnchantedEffect(2,2,Duration.WhileOnBattlefield),
                new BoostEnchantedEffect(-2,-2,Duration.WhileOnBattlefield),
                new AttachedToMatchesFilterCondition(filter),
                "Enchanted creature gets +2/+2 as long as it's an enchantment. Otherwise, it gets -2/-2"));
        this.addAbility(ability);
    }

    private NyxInfusion(final NyxInfusion card) {
        super(card);
    }

    @Override
    public NyxInfusion copy() {
        return new NyxInfusion(this);
    }
}
