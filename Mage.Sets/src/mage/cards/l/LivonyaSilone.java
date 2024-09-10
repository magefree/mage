package mage.cards.l;

import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledLandPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class LivonyaSilone extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Legendary land");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public LivonyaSilone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // legendary landwalk
        this.addAbility(new LandwalkAbility(filter));
    }

    private LivonyaSilone(final LivonyaSilone card) {
        super(card);
    }

    @Override
    public LivonyaSilone copy() {
        return new LivonyaSilone(this);
    }
}
