
package mage.cards.q;

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
public final class Qumulox extends CardImpl {

    public Qumulox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{U}{U}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());
        this.addAbility(FlyingAbility.getInstance());
    }

    private Qumulox(final Qumulox card) {
        super(card);
    }

    @Override
    public Qumulox copy() {
        return new Qumulox(this);
    }
}
