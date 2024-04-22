package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class SatyrEnchanter extends CardImpl {

    public SatyrEnchanter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an enchantment spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_SPELL_AN_ENCHANTMENT, false
        ));
    }

    private SatyrEnchanter(final SatyrEnchanter card) {
        super(card);
    }

    @Override
    public SatyrEnchanter copy() {
        return new SatyrEnchanter(this);
    }
}
