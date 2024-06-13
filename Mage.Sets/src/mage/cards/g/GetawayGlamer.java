package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class GetawayGlamer extends CardImpl {

    private static final FilterCreaturePermanent filter =
            new FilterCreaturePermanent("target nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public GetawayGlamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {1} -- Exile target nontoken creature. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ExileReturnBattlefieldNextEndStepTargetEffect().withTextThatCard(false));
        this.getSpellAbility().addTarget(
                new TargetCreaturePermanent(filter).withChooseHint("to exile")
        );
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(1));

        // + {2} -- Destroy target creature if no other creature has greater power.
        this.getSpellAbility().addMode(new Mode(new GetawayGlamerEffect())
                .addTarget(new TargetCreaturePermanent().withChooseHint("to destroy if no other creature has greater power"))
                .withCost(new GenericManaCost(2)));
    }

    private GetawayGlamer(final GetawayGlamer card) {
        super(card);
    }

    @Override
    public GetawayGlamer copy() {
        return new GetawayGlamer(this);
    }
}

class GetawayGlamerEffect extends OneShotEffect {

    GetawayGlamerEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature if no other creature has greater power";
    }

    private GetawayGlamerEffect(final GetawayGlamerEffect effect) {
        super(effect);
    }

    @Override
    public GetawayGlamerEffect copy() {
        return new GetawayGlamerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature == null) {
            return false;
        }

        int powerOfTarget = targetCreature.getPower().getValue();
        List<Permanent> creatures = game.getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game);
        for (Permanent creature : creatures) {
            // No check for other, since the check is "greater than"
            if (creature.getPower().getValue() > powerOfTarget) {
                // Found another creature with greater power. no effect.
                return false;
            }
        }

        targetCreature.destroy(source, game, false);
        return true;
    }
}
