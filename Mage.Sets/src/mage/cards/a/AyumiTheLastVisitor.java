package mage.cards.a;

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
 * @author LevelX2
 */
public final class AyumiTheLastVisitor extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Legendary land");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public AyumiTheLastVisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(7);
        this.toughness = new MageInt(3);

        // Legendary landwalk
        this.addAbility(new LandwalkAbility(filter));
    }

    private AyumiTheLastVisitor(final AyumiTheLastVisitor card) {
        super(card);
    }

    @Override
    public AyumiTheLastVisitor copy() {
        return new AyumiTheLastVisitor(this);
    }
}
