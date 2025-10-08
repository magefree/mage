package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.WebSlingingAbility;
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
 * @author TheElk801
 */
public final class SpiderSense extends CardImpl {

    private static final FilterStackObject filter
            = new FilterStackObject("instant spell, sorcery spell, or triggered ability");

    static {
        filter.add(SpiderSensePredicate.instance);
    }

    public SpiderSense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target instant spell, sorcery spell, or triggered ability.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetStackObject(filter));

        // Web-slinging {U}
        this.addAbility(new WebSlingingAbility(this, "{U}"));
    }

    private SpiderSense(final SpiderSense card) {
        super(card);
    }

    @Override
    public SpiderSense copy() {
        return new SpiderSense(this);
    }
}

enum SpiderSensePredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        if (input instanceof Spell) {
            return input.isInstantOrSorcery(game);
        }
        return input instanceof Ability && ((Ability) input).isTriggeredAbility();
    }
}
