package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GolemToken;
import mage.target.TargetPermanent;
import mage.util.functions.EmptyApplyToPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MasterfulReplication extends CardImpl {

    public MasterfulReplication(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{U}");

        // Choose one —
        // • Create two 3/3 colorless Golem artifact creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GolemToken(), 2));

        // • Choose target artifact you control. Each other artifact you control becomes a copy of that artifact until end of turn.
        Mode mode = new Mode(new MasterfulReplicationEffect());
        mode.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        this.getSpellAbility().addMode(mode);
    }

    private MasterfulReplication(final MasterfulReplication card) {
        super(card);
    }

    @Override
    public MasterfulReplication copy() {
        return new MasterfulReplication(this);
    }
}

class MasterfulReplicationEffect extends OneShotEffect {

    MasterfulReplicationEffect() {
        super(Outcome.Copy);
        this.staticText = "Choose target artifact you control. Each other artifact you control " +
                "becomes a copy of that artifact until end of turn.";
    }

    private MasterfulReplicationEffect(final MasterfulReplicationEffect effect) {
        super(effect);
    }

    @Override
    public MasterfulReplicationEffect copy() {
        return new MasterfulReplicationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent copyFromArtifact = game.getPermanent(source.getFirstTarget());
        if (copyFromArtifact == null) {
            return false;
        }
        for (Permanent copyToArtifact : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (copyToArtifact.isArtifact() && !copyToArtifact.getId().equals(copyFromArtifact.getId())) {
                game.copyPermanent(Duration.EndOfTurn, copyFromArtifact, copyToArtifact.getId(), source, new EmptyApplyToPermanent());
            }
        }
        return true;
    }
}
