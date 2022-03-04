
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureIfVehicleEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author Styxo
 */
public final class AerialModification extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Vehicle");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()));
    }

    public AerialModification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature or Vehicle
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // As long as enchanted permanent is a Vehicle, it's a creature in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesCreatureIfVehicleEffect()));

        // Enchanted creature gets +2/+2 and has flying.
        Effect effect = new BoostEnchantedEffect(2, 2);
        effect.setText("Enchanted creature gets +2/+2");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA);
        effect.setText(" and has flying");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private AerialModification(final AerialModification card) {
        super(card);
    }

    @Override
    public AerialModification copy() {
        return new AerialModification(this);
    }
}
