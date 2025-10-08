package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.ReplicateAbility;
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
 * @author Susucr
 */
public final class ConsignToMemory extends CardImpl {

    private static final FilterStackObject filter
            = new FilterStackObject("triggered ability or colorless spell");

    static {
        filter.add(ConsignToMemoryPredicate.instance);
    }

    public ConsignToMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Replicate {1}
        this.addAbility(new ReplicateAbility("{1}"));

        // Counter target triggered ability or colorless spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetStackObject(filter));
    }

    private ConsignToMemory(final ConsignToMemory card) {
        super(card);
    }

    @Override
    public ConsignToMemory copy() {
        return new ConsignToMemory(this);
    }
}

enum ConsignToMemoryPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        if (input instanceof Spell) {
            return input.getColor(game).isColorless();
        }
        return input instanceof Ability && ((Ability) input).isTriggeredAbility();
    }
}
