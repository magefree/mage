package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MartyrForTheCause extends CardImpl {

    public MartyrForTheCause(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Martyr for the Cause dies, proliferate. (Choose any number of permanents and/or players, then give each another counter of each kind already there.)
        this.addAbility(new DiesSourceTriggeredAbility(new ProliferateEffect()));
    }

    private MartyrForTheCause(final MartyrForTheCause card) {
        super(card);
    }

    @Override
    public MartyrForTheCause copy() {
        return new MartyrForTheCause(this);
    }
}
