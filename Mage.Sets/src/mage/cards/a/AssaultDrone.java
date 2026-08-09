package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class AssaultDrone extends CardImpl {

    public AssaultDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Artifact creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
            1, 0, Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));
    }

    private AssaultDrone(final AssaultDrone card) {
        super(card);
    }

    @Override
    public AssaultDrone copy() {
        return new AssaultDrone(this);
    }
}
