package mage.cards.p;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PridefulFeastling extends CardImpl {

    public PridefulFeastling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private PridefulFeastling(final PridefulFeastling card) {
        super(card);
    }

    @Override
    public PridefulFeastling copy() {
        return new PridefulFeastling(this);
    }
}
