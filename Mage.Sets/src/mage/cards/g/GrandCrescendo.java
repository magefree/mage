package mage.cards.g;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.CitizenGreenWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrandCrescendo extends CardImpl {

    public GrandCrescendo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}{W}");

        // Create X 1/1 green and white Citizen creature tokens. Creatures you control gain indestructible until end of turn.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new CitizenGreenWhiteToken(), ManacostVariableValue.REGULAR
        ));
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ));
    }

    private GrandCrescendo(final GrandCrescendo card) {
        super(card);
    }

    @Override
    public GrandCrescendo copy() {
        return new GrandCrescendo(this);
    }
}
