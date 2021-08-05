package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;

/**
 *
 * @author weirddan455
 */
public final class SmashingSuccess extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or land");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.LAND.getPredicate()));
    }

    public SmashingSuccess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Destroy target artifact or land. If an artifact is destroyed this way, create a Treasure token.
        this.getSpellAbility().addEffect(new SmashingSuccessEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private SmashingSuccess(final SmashingSuccess card) {
        super(card);
    }

    @Override
    public SmashingSuccess copy() {
        return new SmashingSuccess(this);
    }
}

class SmashingSuccessEffect extends OneShotEffect {

    public SmashingSuccessEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target artifact or land. If an artifact is destroyed this way, create a Treasure token.";
    }

    private SmashingSuccessEffect(final SmashingSuccessEffect effect) {
        super (effect);
    }

    @Override
    public SmashingSuccessEffect copy() {
        return new SmashingSuccessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null && permanent.isPhasedIn() && !permanent.isPhasedOutIndirectly()) {
            if (permanent.isArtifact(game)) {
                if (permanent.destroy(source, game, false)) {
                    Token token = new TreasureToken();
                    token.putOntoBattlefield(1, game, source, source.getControllerId());
                    return true;
                }
            } else {
                return permanent.destroy(source, game, false);
            }
        }
        return false;
    }
}
