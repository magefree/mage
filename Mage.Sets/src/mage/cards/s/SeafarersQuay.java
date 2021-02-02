package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.BandsWithOtherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author L_J
 */
public final class SeafarersQuay extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Blue legendary creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public SeafarersQuay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Blue legendary creatures you control have "bands with other legendary creatures."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(
                new BandsWithOtherAbility(SuperType.LEGENDARY), Duration.WhileOnBattlefield, filter)
                .withForceQuotes()
        ));
    }

    private SeafarersQuay(final SeafarersQuay card) {
        super(card);
    }

    @Override
    public SeafarersQuay copy() {
        return new SeafarersQuay(this);
    }
}
