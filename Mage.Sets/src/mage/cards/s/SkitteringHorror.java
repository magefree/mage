
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author nigelzor
 */
public final class SkitteringHorror extends CardImpl {

    public SkitteringHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When you cast a creature spell, sacrifice Skittering Horror.
        this.addAbility(new SpellCastControllerTriggeredAbility(new SacrificeSourceEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false)
                .setTriggerPhrase("When you cast a creature spell, "));
    }

    private SkitteringHorror(final SkitteringHorror card) {
        super(card);
    }

    @Override
    public SkitteringHorror copy() {
        return new SkitteringHorror(this);
    }
}
