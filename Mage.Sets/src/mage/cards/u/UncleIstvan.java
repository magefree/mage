package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceByPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author MarcoMarin
 */
public final class UncleIstvan extends CardImpl {

    public UncleIstvan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Prevent all damage that would be dealt to Uncle Istvan by creatures.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToSourceByPermanentsEffect(StaticFilters.FILTER_PERMANENT_CREATURES)));
    }

    private UncleIstvan(final UncleIstvan card) {
        super(card);
    }

    @Override
    public UncleIstvan copy() {
        return new UncleIstvan(this);
    }
}
