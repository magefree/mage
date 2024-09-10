package mage.cards.p;

import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlatedOnslaught extends CardImpl {

    public PlatedOnslaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}{W}");

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Creatures you control get +2/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn));
    }

    private PlatedOnslaught(final PlatedOnslaught card) {
        super(card);
    }

    @Override
    public PlatedOnslaught copy() {
        return new PlatedOnslaught(this);
    }
}
