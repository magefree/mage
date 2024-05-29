package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SupportAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AerieAuxiliary extends CardImpl {

    public AerieAuxiliary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Aerie Auxiliary enters the battlefield, support 2.
        this.addAbility(new SupportAbility(this, 2));
    }

    private AerieAuxiliary(final AerieAuxiliary card) {
        super(card);
    }

    @Override
    public AerieAuxiliary copy() {
        return new AerieAuxiliary(this);
    }
}
