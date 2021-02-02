
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class GluttonousCyclops extends CardImpl {

    public GluttonousCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.CYCLOPS);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // {5}{R}{R}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{5}{R}{R}", 3));

    }

    private GluttonousCyclops(final GluttonousCyclops card) {
        super(card);
    }

    @Override
    public GluttonousCyclops copy() {
        return new GluttonousCyclops(this);
    }
}
