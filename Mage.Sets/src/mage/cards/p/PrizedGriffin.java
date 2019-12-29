package mage.cards.p;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrizedGriffin extends CardImpl {

    public PrizedGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private PrizedGriffin(final PrizedGriffin card) {
        super(card);
    }

    @Override
    public PrizedGriffin copy() {
        return new PrizedGriffin(this);
    }
}
