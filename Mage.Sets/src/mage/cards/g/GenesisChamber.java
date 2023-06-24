package mage.cards.g;

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
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MyrToken;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class GenesisChamber extends CardImpl {

    public GenesisChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever a nontoken creature enters the battlefield, if Genesis Chamber is untapped, that creature's controller creates a 1/1 colorless Myr artifact creature token.
        TriggeredAbility ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new GenesisChamberEffect(), StaticFilters.FILTER_CREATURE_NON_TOKEN, false, SetTargetPointer.PERMANENT, "");
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability,
                SourceUntappedCondition.instance,
                "Whenever a nontoken creature enters the battlefield, "
                        + "if {this} is untapped, "
                        + "that creature's controller creates a 1/1 colorless Myr artifact creature token"));
    }

    private GenesisChamber(final GenesisChamber card) {
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
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            MyrToken token = new MyrToken();
            token.putOntoBattlefield(1, game, source, permanent.getControllerId());
        }
        return true;
    }
}
