package mage.cards.b;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class BorgScoutSphere extends CardImpl {

    public BorgScoutSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Station
        this.addAbility(new StationAbility());

        // STATION 6+
        // Artifact creatures you control have flying.
        // 5/5
        this.addAbility(new StationLevelAbility(6).withLevelAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            FlyingAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        ))).withPT(5, 5));
    }

    private BorgScoutSphere(final BorgScoutSphere card) {
        super(card);
    }

    @Override
    public BorgScoutSphere copy() {
        return new BorgScoutSphere(this);
    }
}
