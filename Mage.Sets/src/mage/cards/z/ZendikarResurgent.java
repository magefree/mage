
package mage.cards.z;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class ZendikarResurgent extends CardImpl {

    public ZendikarResurgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{G}{G}");

        // Whenever you tap a land for mana, add one mana of any type that land produced. (<i>The types of mana are white, blue, black, red, green, and colorless.)</i>
        AddManaOfAnyTypeProducedEffect effect = new AddManaOfAnyTypeProducedEffect();
        effect.setText("add one mana of any type that land produced");
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                effect,
                new FilterControlledLandPermanent("you tap a land"),
                SetTargetPointer.PERMANENT));

        // Whenever you cast a creature spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_SPELL_A_CREATURE, false));
    }

    private ZendikarResurgent(final ZendikarResurgent card) {
        super(card);
    }

    @Override
    public ZendikarResurgent copy() {
        return new ZendikarResurgent(this);
    }
}
