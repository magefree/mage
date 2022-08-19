package mage.cards.g;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class GrowthCycle extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Growth Cycle");
    static {
        filter.add(new NamePredicate("Growth Cycle"));
    }
    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter, 2);
    private static final String rule = "it gets an additional +2/+2 until end of turn for each " + xValue.getMessage();

    public GrowthCycle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        // It gets an additional +2/+2 until end of turn for each card named Growth Cycle in your graveyard.
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn).setText(rule));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GrowthCycle(final GrowthCycle card) {
        super(card);
    }

    @Override
    public GrowthCycle copy() {
        return new GrowthCycle(this);
    }
}
