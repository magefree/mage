
package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author TheElk801
 */
public final class TahngarthsRage extends CardImpl {

    public TahngarthsRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +3/+0 as long as it's attacking. Otherwise, it gets -2/-1.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostEnchantedEffect(3, 0, Duration.WhileOnBattlefield),
                        new BoostEnchantedEffect(-2, -1, Duration.WhileOnBattlefield),
                        new AttachedToMatchesFilterCondition(new FilterAttackingCreature()),
                        "Enchanted creature gets +3/+0 as long as it's attacking. Otherwise, it gets -2/-1."
                )
        ));
    }

    private TahngarthsRage(final TahngarthsRage card) {
        super(card);
    }

    @Override
    public TahngarthsRage copy() {
        return new TahngarthsRage(this);
    }
}
