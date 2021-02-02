
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class NessianAsp extends CardImpl {

    public NessianAsp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.SNAKE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // {6}{G}: Monstrosity 4.
        this.addAbility(new MonstrosityAbility("{6}{G}",4));
    }

    private NessianAsp(final NessianAsp card) {
        super(card);
    }

    @Override
    public NessianAsp copy() {
        return new NessianAsp(this);
    }
}
