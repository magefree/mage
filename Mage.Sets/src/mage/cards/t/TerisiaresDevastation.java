package mage.cards.t;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TerisiaresDevastation extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledArtifactPermanent(), -1);

    public TerisiaresDevastation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{2}{B}{B}");

        // You lose X life and create X tapped Powerstone tokens. Then all creatures get -1/-1 until end of turn for each artifact you control.
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PowerstoneToken(), ManacostVariableValue.REGULAR, true, false).concatBy("and"));
        this.getSpellAbility().addEffect(new BoostAllEffect(
                xValue, xValue, Duration.EndOfTurn)
                .concatBy("Then"));
    }

    private TerisiaresDevastation(final TerisiaresDevastation card) {
        super(card);
    }

    @Override
    public TerisiaresDevastation copy() {
        return new TerisiaresDevastation(this);
    }
}
