package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HulkBruceBanner extends CardImpl {

    public HulkBruceBanner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private HulkBruceBanner(final HulkBruceBanner card) {
        super(card);
    }

    @Override
    public HulkBruceBanner copy() {
        return new HulkBruceBanner(this);
    }
}
