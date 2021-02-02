
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FadingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class Blastoderm extends CardImpl {

    public Blastoderm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());
        // Fading 3
        this.addAbility(new FadingAbility(3, this));
    }

    private Blastoderm(final Blastoderm card) {
        super(card);
    }

    @Override
    public Blastoderm copy() {
        return new Blastoderm(this);
    }
}
