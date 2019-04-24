
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MyrToken;

/**
 *
 * @author Plopman
 */
public final class GenesisChamber extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public GenesisChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever a nontoken creature enters the battlefield, if Genesis Chamber is untapped, that creature's controller creates a 1/1 colorless Myr artifact creature token.
        TriggeredAbility ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new GenesisChamberEffect(), filter, false, SetTargetPointer.PERMANENT, "");
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability,
                SourceUntappedCondition.instance,
                "Whenever a nontoken creature enters the battlefield, "
                + "if {this} is untapped, "
                + "that creature's controller creates a 1/1 colorless Myr artifact creature token"));
    }

    public GenesisChamber(final GenesisChamber card) {
        super(card);
    }

    @Override
    public GenesisChamber copy() {
        return new GenesisChamber(this);
    }
}

enum SourceUntappedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent != null) {
            return !permanent.isTapped();
        }
        return false;
    }

    @Override
    public String toString() {
        return "if {this} is untapped";
    }
}

class GenesisChamberEffect extends OneShotEffect {

    public GenesisChamberEffect() {
        super(Outcome.Benefit);
        this.staticText = "that creature's controller creates a 1/1 colorless Myr artifact creature token";
    }

    public GenesisChamberEffect(final GenesisChamberEffect effect) {
        super(effect);
    }

    @Override
    public GenesisChamberEffect copy() {
        return new GenesisChamberEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(targetPointer.getFirst(game, source));
        if (permanent != null) {
            MyrToken token = new MyrToken();
            token.putOntoBattlefield(1, game, source.getSourceId(), permanent.getControllerId());
        }
        return true;
    }
}
