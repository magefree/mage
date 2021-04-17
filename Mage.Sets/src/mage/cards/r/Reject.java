package mage.cards.r;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Reject extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature or planeswalker spell");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public Reject(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target creature or planeswalker spell unless its controller pays {3}. If that spell is countered this way, exile it instead of putting into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3), true));
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private Reject(final Reject card) {
        super(card);
    }

    @Override
    public Reject copy() {
        return new Reject(this);
    }
}
