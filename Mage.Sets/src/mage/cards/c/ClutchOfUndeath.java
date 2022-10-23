
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedCreatureSubtypeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author JRHerlehy
 */
public final class ClutchOfUndeath extends CardImpl {

    public ClutchOfUndeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +3/+3 as long as it's a Zombie. Otherwise, it gets -3/-3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostEnchantedEffect(3, 3),
                        new BoostEnchantedEffect(-3, -3),
                        new EnchantedCreatureSubtypeCondition(SubType.ZOMBIE),
                        "Enchanted creature gets +3/+3 as long as it's a Zombie. Otherwise, it gets -3/-3.")
                )
        );
    }

    private ClutchOfUndeath(final ClutchOfUndeath card) {
        super(card);
    }

    @Override
    public ClutchOfUndeath copy() {
        return new ClutchOfUndeath(this);
    }
}
