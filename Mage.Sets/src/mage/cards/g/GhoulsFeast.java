
package mage.cards.g;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class GhoulsFeast extends CardImpl {

    public GhoulsFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gets +X/+0 until end of turn, where X is the number of creature cards in your graveyard.
        DynamicValue xValue = new CardsInControllerGraveyardCount(new FilterCreatureCard("creature card"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, StaticValue.get(0), Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GhoulsFeast(final GhoulsFeast card) {
        super(card);
    }

    @Override
    public GhoulsFeast copy() {
        return new GhoulsFeast(this);
    }
}
