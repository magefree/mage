package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.DroidToken;

/**
 *
 * @author NinthWorld
 */
public final class DroidUprising extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact creatures");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public DroidUprising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");
        

        // Tap all nonartifact creatures.
        this.getSpellAbility().addEffect(new TapAllEffect(filter));

        // Create two colorless 1/1 Droid artifact creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new DroidToken(), 2));
    }

    private DroidUprising(final DroidUprising card) {
        super(card);
    }

    @Override
    public DroidUprising copy() {
        return new DroidUprising(this);
    }
}
