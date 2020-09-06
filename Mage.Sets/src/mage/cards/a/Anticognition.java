package mage.cards.a;

import java.util.UUID;

import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;

/**
 * @author TheElk801
 */
public final class Anticognition extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature or planeswalker spell");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public Anticognition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target creature or planeswalker spell unless its controller pays {2}. If an opponent has eight or more cards in their graveyard, instead counter that spell, then scry 2.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CounterTargetEffect(), new CounterUnlessPaysEffect(new GenericManaCost(2)),
                CardsInOpponentGraveyardCondition.EIGHT, "Counter target creature or planeswalker spell " +
                "unless its controller pays {2}. If an opponent has eight or more cards in their graveyard, " +
                "instead counter that spell"
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ScryEffect(2), CardsInOpponentGraveyardCondition.EIGHT, ", then scry 2"
        ));
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint());
    }

    private Anticognition(final Anticognition card) {
        super(card);
    }

    @Override
    public Anticognition copy() {
        return new Anticognition(this);
    }
}
