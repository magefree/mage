package mage.cards.s;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenControllerTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.SwanSongBirdToken;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StrixSerenade extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("artifact, creature, or planeswalker spell");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public StrixSerenade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target artifact, creature, or planeswalker spell. Its controller creates a 2/2 blue Bird creature token with flying.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetEffect(
                new SwanSongBirdToken(), CreateTokenControllerTargetEffect.TargetKind.SPELL
        ));
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private StrixSerenade(final StrixSerenade card) {
        super(card);
    }

    @Override
    public StrixSerenade copy() {
        return new StrixSerenade(this);
    }
}
