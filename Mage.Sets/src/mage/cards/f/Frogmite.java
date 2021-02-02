
package mage.cards.f;

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
public final class Frogmite extends CardImpl {

    public Frogmite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.FROG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());
    }

    private Frogmite(final Frogmite card) {
        super(card);
    }

    @Override
    public Frogmite copy() {
        return new Frogmite(this);
    }
}
