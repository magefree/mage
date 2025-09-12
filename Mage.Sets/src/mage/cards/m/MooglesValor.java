package mage.cards.m;

import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.MoogleToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MooglesValor extends CardImpl {

    public MooglesValor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}{W}");

        // For each creature you control, create a 1/2 white Moogle creature token with lifelink. Then creatures you control gain indestructible until end of turn.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new MoogleToken(), CreaturesYouControlCount.PLURAL
        ).setText("for each creature you control, create a 1/2 white Moogle creature token with lifelink"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).concatBy("Then"));
    }

    private MooglesValor(final MooglesValor card) {
        super(card);
    }

    @Override
    public MooglesValor copy() {
        return new MooglesValor(this);
    }
}
