
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class ShadowRider extends CardImpl {

    public ShadowRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flanking
        this.addAbility(new FlankingAbility());
    }

    private ShadowRider(final ShadowRider card) {
        super(card);
    }

    @Override
    public ShadowRider copy() {
        return new ShadowRider(this);
    }
}
