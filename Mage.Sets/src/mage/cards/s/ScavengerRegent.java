package mage.cards.s;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.OmenCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class ScavengerRegent extends OmenCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each non-Dragon creature");
    private static final DynamicValue xValue = new SignInversionDynamicValue(GetXValue.instance);

    static {
        filter.add(Predicates.not(SubType.DRAGON.getPredicate()));
    }

    public ScavengerRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{3}{B}",
                "Exude Toxin",
                new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Scavenger Regent
        this.getLeftHalfCard().setPT(4, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Ward--Discard a card.
        this.getLeftHalfCard().addAbility(new WardAbility(new DiscardCardCost(), false));

        // Exude Toxin
        // Each non-Dragon creature gets -X/-X until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new BoostAllEffect(xValue, xValue, Duration.EndOfTurn, filter, false));

        finalizeCard();
    }

    private ScavengerRegent(final ScavengerRegent card) {
        super(card);
    }

    @Override
    public ScavengerRegent copy() {
        return new ScavengerRegent(this);
    }
}
