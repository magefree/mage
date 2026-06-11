package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class PantherRobot extends CardImpl {

    public PantherRobot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{10}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private PantherRobot(final PantherRobot card) {
        super(card);
    }

    @Override
    public PantherRobot copy() {
        return new PantherRobot(this);
    }
}
