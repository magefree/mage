package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.ArtifactLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SojournersCompanion extends CardImpl {

    public SojournersCompanion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.SALAMANDER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Artifact landcycling {2}
        this.addAbility(new ArtifactLandcyclingAbility(new GenericManaCost(2)));
    }

    private SojournersCompanion(final SojournersCompanion card) {
        super(card);
    }

    @Override
    public SojournersCompanion copy() {
        return new SojournersCompanion(this);
    }
}
