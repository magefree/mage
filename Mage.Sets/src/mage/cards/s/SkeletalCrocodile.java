
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class SkeletalCrocodile extends CardImpl {

    public SkeletalCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.CROCODILE);
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(1);
    }

    private SkeletalCrocodile(final SkeletalCrocodile card) {
        super(card);
    }

    @Override
    public SkeletalCrocodile copy() {
        return new SkeletalCrocodile(this);
    }
}
