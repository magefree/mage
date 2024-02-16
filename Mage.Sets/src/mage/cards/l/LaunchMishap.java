package mage.cards.l;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LaunchMishap extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature or planeswalker spell");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public LaunchMishap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Counter target creature or planeswalker spell. Create a 1/1 colorless Thopter artifact creature token with flying.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ThopterColorlessToken()));
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private LaunchMishap(final LaunchMishap card) {
        super(card);
    }

    @Override
    public LaunchMishap copy() {
        return new LaunchMishap(this);
    }
}
