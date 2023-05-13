package mage.cards.t;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 * @author rscoates
 */
public final class TalesEnd extends CardImpl {

    private static final FilterStackObject filter
            = new FilterStackObject("activated ability, triggered ability, or legendary spell");

    static {
        filter.add(TalesEndPredicate.instance);
    }

    public TalesEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target activated ability, triggered ability, or legendary spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetStackObject(filter));
    }

    private TalesEnd(final TalesEnd card) {
        super(card);
    }

    @Override
    public TalesEnd copy() {
        return new TalesEnd(this);
    }
}

enum TalesEndPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return !(input instanceof Spell) || input.isLegendary(game);
    }
}
