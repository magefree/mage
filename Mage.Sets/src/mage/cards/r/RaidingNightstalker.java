
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class RaidingNightstalker extends CardImpl {

    public RaidingNightstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.NIGHTSTALKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());
    }

    private RaidingNightstalker(final RaidingNightstalker card) {
        super(card);
    }

    @Override
    public RaidingNightstalker copy() {
        return new RaidingNightstalker(this);
    }
}
