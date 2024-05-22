package mage.cards.f;

import mage.MageInt;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrogmyrEnforcer extends CardImpl {

    public FrogmyrEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Prototype {3}{R} -- 2/2
        this.addAbility(new PrototypeAbility(this, "{3}{R}", 2, 2));

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());
    }

    private FrogmyrEnforcer(final FrogmyrEnforcer card) {
        super(card);
    }

    @Override
    public FrogmyrEnforcer copy() {
        return new FrogmyrEnforcer(this);
    }
}
