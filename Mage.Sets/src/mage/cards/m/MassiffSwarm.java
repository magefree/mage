
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class MassiffSwarm extends CardImpl {

    public MassiffSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.CROCODILE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {6}{G}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{6}{G}", 3));

    }

    private MassiffSwarm(final MassiffSwarm card) {
        super(card);
    }

    @Override
    public MassiffSwarm copy() {
        return new MassiffSwarm(this);
    }
}
