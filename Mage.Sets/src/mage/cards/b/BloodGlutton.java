package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodGlutton extends CardImpl {

    public BloodGlutton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private BloodGlutton(final BloodGlutton card) {
        super(card);
    }

    @Override
    public BloodGlutton copy() {
        return new BloodGlutton(this);
    }
}
