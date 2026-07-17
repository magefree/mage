package mage.cards.h;

import mage.MageInt;
import mage.abilities.keyword.IncrementAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HungryGraffalon extends CardImpl {

    public HungryGraffalon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.GIRAFFE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Increment
        this.addAbility(new IncrementAbility());
    }

    private HungryGraffalon(final HungryGraffalon card) {
        super(card);
    }

    @Override
    public HungryGraffalon copy() {
        return new HungryGraffalon(this);
    }
}
