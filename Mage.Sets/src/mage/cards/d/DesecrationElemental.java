
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author Plopman
 */
public final class DesecrationElemental extends CardImpl {

    public DesecrationElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Fear
        this.addAbility(FearAbility.getInstance());
        // Whenever a player casts a spell, sacrifice a creature.
        this.addAbility(new SpellCastAllTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, ""), false));
    }

    private DesecrationElemental(final DesecrationElemental card) {
        super(card);
    }

    @Override
    public DesecrationElemental copy() {
        return new DesecrationElemental(this);
    }
}
