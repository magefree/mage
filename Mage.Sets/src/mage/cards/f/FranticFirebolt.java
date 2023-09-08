package mage.cards.f;

import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AdventurePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class FranticFirebolt extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards in your graveyard that are instant cards, sorcery cards, and/or have an Adventure");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                AdventurePredicate.instance
        ));
    }

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            StaticValue.get(2),
            new CardsInControllerGraveyardCount(filter)
    );

    private static final Hint hint = new ValueHint("Value of X", xValue);

    public FranticFirebolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Frantic Firebolt deals X damage to target creature, where X is 2 plus the number of cards in your graveyard that are instant cards, sorcery cards, and/or have an Adventure.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals X damage to target creature," +
                        " where X is 2 plus the number of cards in your graveyard that are" +
                        " instant cards, sorcery cards, and/or have an Adventure"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);

    }

    private FranticFirebolt(final FranticFirebolt card) {
        super(card);
    }

    @Override
    public FranticFirebolt copy() {
        return new FranticFirebolt(this);
    }
}
