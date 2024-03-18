package mage.cards.c;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author tiera3 - modified from Growth Cycle
 */
public final class CompoundFracture extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Compound Fracture");
    static {
        filter.add(new NamePredicate("Compound Fracture"));
    }
    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter, -1);
    private static final String rule = "It gets an additional -1/-1 until end of turn for each " + xValue.getMessage();
    private static final Hint hint = new ValueHint(
            "Cards named Compound Fracture in your graveyard", new CardsInControllerGraveyardCount(filter)
    );

    public CompoundFracture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-1, -1, Duration.EndOfTurn));
        // It gets an additional -1/-1 until end of turn for each card named Compound Fracture in your graveyard.
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn).setText(rule));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);
    }

    private CompoundFracture(final CompoundFracture card) {
        super(card);
    }

    @Override
    public CompoundFracture copy() {
        return new CompoundFracture(this);
    }
}
