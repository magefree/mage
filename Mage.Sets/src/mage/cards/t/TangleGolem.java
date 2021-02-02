
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.AffinityForLandTypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class TangleGolem extends CardImpl {

    public TangleGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Affinity for Forests
        this.addAbility(new AffinityForLandTypeAbility(SubType.FOREST, "Forests"));
    }

    private TangleGolem(final TangleGolem card) {
        super(card);
    }

    @Override
    public TangleGolem copy() {
        return new TangleGolem(this);
    }
}
