
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class AdaptiveSnapjaw extends CardImpl {

    public AdaptiveSnapjaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(2);

        // Evolve
        this.addAbility(new EvolveAbility());
    }

    private AdaptiveSnapjaw(final AdaptiveSnapjaw card) {
        super(card);
    }

    @Override
    public AdaptiveSnapjaw copy() {
        return new AdaptiveSnapjaw(this);
    }
}
