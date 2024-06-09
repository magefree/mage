package mage.cards.s;

import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SubjugateTheHobbits extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each noncommander creature with mana value 3 or less");

    static {
        filter.add(Predicates.not(CommanderPredicate.instance));
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public SubjugateTheHobbits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}{U}");

        // Gain control of each noncommander creature with mana value 3 or less.
        this.getSpellAbility().addEffect(new GainControlAllEffect(Duration.Custom, filter)
                .setText("gain control of each noncommander creature with mana value 3 or less"));

    }

    private SubjugateTheHobbits(final SubjugateTheHobbits card) {
        super(card);
    }

    @Override
    public SubjugateTheHobbits copy() {
        return new SubjugateTheHobbits(this);
    }
}
