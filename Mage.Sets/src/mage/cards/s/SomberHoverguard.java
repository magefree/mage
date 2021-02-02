
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SomberHoverguard extends CardImpl {

    public SomberHoverguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.DRONE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());
        this.addAbility(FlyingAbility.getInstance());
    }

    private SomberHoverguard(final SomberHoverguard card) {
        super(card);
    }

    @Override
    public SomberHoverguard copy() {
        return new SomberHoverguard(this);
    }
}
