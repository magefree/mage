
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TurnPhase;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.AngelToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class AngelicFavor extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("If you control a Plains");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }
    private static final FilterControlledCreaturePermanent untappedCreatureYouControl = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        untappedCreatureYouControl.add(TappedPredicate.UNTAPPED);
    }

    public AngelicFavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // If you control a Plains, you may tap an untapped creature you control rather than pay Angelic Favor's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new TapTargetCost(new TargetControlledPermanent(untappedCreatureYouControl)), new PermanentsOnTheBattlefieldCondition(filter)));

        // Cast Angelic Favor only during combat.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT));

        // Put a 4/4 white Angel creature token with flying onto the battlefield. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new AngelicFavorEffect());

    }

    private AngelicFavor(final AngelicFavor card) {
        super(card);
    }

    @Override
    public AngelicFavor copy() {
        return new AngelicFavor(this);
    }
}

class AngelicFavorEffect extends OneShotEffect {

    public AngelicFavorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 4/4 white Angel creature token with flying. Exile it at the beginning of the next end step";
    }

    private AngelicFavorEffect(final AngelicFavorEffect effect) {
        super(effect);
    }

    @Override
    public AngelicFavorEffect copy() {
        return new AngelicFavorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new AngelToken());
        if (effect.apply(game, source)) {
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }
        return false;
    }
}
