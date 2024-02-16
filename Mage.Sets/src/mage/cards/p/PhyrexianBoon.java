
package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedCreatureColorCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class PhyrexianBoon extends CardImpl {

    public PhyrexianBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+1 as long as it's black. Otherwise, it gets -1/-2.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostEnchantedEffect(2, 1),
                        new BoostEnchantedEffect(-1, -2),
                        new EnchantedCreatureColorCondition(ObjectColor.BLACK),
                        "Enchanted creature gets +2/+1 as long as it's black. Otherwise, it gets -1/-2."
                )
        ));
    }

    private PhyrexianBoon(final PhyrexianBoon card) {
        super(card);
    }

    @Override
    public PhyrexianBoon copy() {
        return new PhyrexianBoon(this);
    }
}
