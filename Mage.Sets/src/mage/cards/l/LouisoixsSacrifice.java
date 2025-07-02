package mage.cards.l;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LouisoixsSacrifice extends CardImpl {

    private static final FilterStackObject filter
            = new FilterStackObject("activated ability, triggered ability, or noncreature spell");

    static {
        filter.add(LouisoixsSacrificePredicate.instance);
    }

    public LouisoixsSacrifice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // As an additional cost to cast this spell, sacrifice a legendary creature or pay {2}.
        this.getSpellAbility().addCost(new OrCost(
                "sacrifice a legendary creature or pay {2}",
                new SacrificeTargetCost(StaticFilters.FILTER_CREATURE_LEGENDARY),
                new GenericManaCost(2)
        ));

        // Counter target activated ability, triggered ability, or noncreature spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetStackObject(filter));
    }

    private LouisoixsSacrifice(final LouisoixsSacrifice card) {
        super(card);
    }

    @Override
    public LouisoixsSacrifice copy() {
        return new LouisoixsSacrifice(this);
    }
}

enum LouisoixsSacrificePredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return !(input instanceof Spell) || !input.isCreature(game);
    }
}
