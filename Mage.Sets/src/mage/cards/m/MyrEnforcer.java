
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class MyrEnforcer extends CardImpl {

    public MyrEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.MYR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());
    }

    private MyrEnforcer(final MyrEnforcer card) {
        super(card);
    }

    @Override
    public MyrEnforcer copy() {
        return new MyrEnforcer(this);
    }
}
