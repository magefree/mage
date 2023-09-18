package mage.cards.s;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.BloodToken;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyphonEssence extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature or planeswalker spell");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public SyphonEssence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Counter target creature or planeswalker spell. Create a Blood token.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BloodToken()));
    }

    private SyphonEssence(final SyphonEssence card) {
        super(card);
    }

    @Override
    public SyphonEssence copy() {
        return new SyphonEssence(this);
    }
}
