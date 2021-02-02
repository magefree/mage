package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Stravant
 */
public final class PouncingCheetah extends CardImpl {

    public PouncingCheetah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        addAbility(FlashAbility.getInstance());
    }

    private PouncingCheetah(final PouncingCheetah card) {
        super(card);
    }

    @Override
    public PouncingCheetah copy() {
        return new PouncingCheetah(this);
    }
}

