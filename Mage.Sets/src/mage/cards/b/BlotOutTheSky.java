package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.InklingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlotOutTheSky extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public BlotOutTheSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{B}");

        // Create X tapped 2/1 white and black Inkling creature tokens with flying. If X is 6 or more, destroy all noncreature, nonland permanents.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new InklingToken(), ManacostVariableValue.REGULAR, true, false
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DestroyAllEffect(filter), BlotOutTheSkyCondition.instance,
                "If X is 6 or more, destroy all noncreature, nonland permanents"
        ));
    }

    private BlotOutTheSky(final BlotOutTheSky card) {
        super(card);
    }

    @Override
    public BlotOutTheSky copy() {
        return new BlotOutTheSky(this);
    }
}

enum BlotOutTheSkyCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return source.getManaCostsToPay().getX() >= 6;
    }
}
