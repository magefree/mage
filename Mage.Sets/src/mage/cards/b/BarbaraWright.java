package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarbaraWright extends CardImpl {

    public BarbaraWright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // History Teacher -- Sagas you control have read ahead.
        this.addAbility(SagaAbility.makeGainReadAheadAbility().withFlavorWord("History Teacher"));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private BarbaraWright(final BarbaraWright card) {
        super(card);
    }

    @Override
    public BarbaraWright copy() {
        return new BarbaraWright(this);
    }
}
