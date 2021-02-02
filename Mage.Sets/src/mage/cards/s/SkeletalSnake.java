
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
public final class SkeletalSnake extends CardImpl {

    public SkeletalSnake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private SkeletalSnake(final SkeletalSnake card) {
        super(card);
    }

    @Override
    public SkeletalSnake copy() {
        return new SkeletalSnake(this);
    }
}
