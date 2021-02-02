
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class SkyshroudRidgeback extends CardImpl {

    public SkyshroudRidgeback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Fading 2
        this.addAbility(new FadingAbility(2, this));
    }

    private SkyshroudRidgeback(final SkyshroudRidgeback card) {
        super(card);
    }

    @Override
    public SkyshroudRidgeback copy() {
        return new SkyshroudRidgeback(this);
    }
}
