
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class SuntouchedMyr extends CardImpl {

    public SuntouchedMyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Sunburst
        this.addAbility(new SunburstAbility(this));
    }

    private SuntouchedMyr(final SuntouchedMyr card) {
        super(card);
    }

    @Override
    public SuntouchedMyr copy() {
        return new SuntouchedMyr(this);
    }
}
