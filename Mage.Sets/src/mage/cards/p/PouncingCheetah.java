package mage.cards.p;

import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Stravant
 */
public class PouncingCheetah extends CardImpl {

    public PouncingCheetah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add("Cat");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        addAbility(FlashAbility.getInstance());
    }

    public PouncingCheetah(final PouncingCheetah card) {
        super(card);
    }

    @Override
    public PouncingCheetah copy() {
        return new PouncingCheetah(this);
    }
}

