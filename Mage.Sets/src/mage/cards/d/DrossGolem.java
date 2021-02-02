
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.AffinityForLandTypeAbility;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class DrossGolem extends CardImpl {

    public DrossGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Affinity for Swamps
        this.addAbility(new AffinityForLandTypeAbility(SubType.SWAMP, "Swamps"));
        
        // Fear
        this.addAbility(FearAbility.getInstance());
    }

    private DrossGolem(final DrossGolem card) {
        super(card);
    }

    @Override
    public DrossGolem copy() {
        return new DrossGolem(this);
    }
}
