package mage.cards.a;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ClueArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AirNomadLegacy extends CardImpl {

    public AirNomadLegacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}");

        // When this enchantment enters, create a Clue token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ClueArtifactToken())));

        // Creatures you control with flying get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_FLYING
        ).setText("creatures you control with flying get +1/+1")));
    }

    private AirNomadLegacy(final AirNomadLegacy card) {
        super(card);
    }

    @Override
    public AirNomadLegacy copy() {
        return new AirNomadLegacy(this);
    }
}
