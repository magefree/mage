package mage.cards.l;

import mage.MageInt;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledLandPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LegionsOfLimDul extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("snow swamp");

    static {
        filter.add(SuperType.SNOW.getPredicate());
        filter.add(SubType.SWAMP.getPredicate());
    }

    public LegionsOfLimDul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Snow swampwalk
        this.addAbility(new LandwalkAbility(filter));
    }

    private LegionsOfLimDul(final LegionsOfLimDul card) {
        super(card);
    }

    @Override
    public LegionsOfLimDul copy() {
        return new LegionsOfLimDul(this);
    }
}
