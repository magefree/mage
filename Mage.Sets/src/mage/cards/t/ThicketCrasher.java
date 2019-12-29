package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThicketCrasher extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELEMENTAL, "Elementals");

    public ThicketCrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Other Elementals you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));
    }

    private ThicketCrasher(final ThicketCrasher card) {
        super(card);
    }

    @Override
    public ThicketCrasher copy() {
        return new ThicketCrasher(this);
    }
}
