package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RhythmOfTheWild extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("Creature spells you control");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent("Nontoken creatures you control");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter2.add(Predicates.not(new TokenPredicate()));
    }

    public RhythmOfTheWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{G}");

        // Creature spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(new CantBeCounteredControlledEffect(
                filter, null, Duration.WhileOnBattlefield
        )));

        // Nontoken creatures you control have riot.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new RiotAbility(), Duration.WhileOnBattlefield, filter2
        )));
    }

    private RhythmOfTheWild(final RhythmOfTheWild card) {
        super(card);
    }

    @Override
    public RhythmOfTheWild copy() {
        return new RhythmOfTheWild(this);
    }
}
