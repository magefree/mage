package mage.cards.j;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BorgToken;

/**
 *
 * @author muz
 */
public final class JoinTheCollective extends CardImpl {

    public JoinTheCollective(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{W}");

        // Destroy all creatures. Create X 1/1 colorless Borg artifact creature tokens, where X is the number of nonartifact creatures destroyed this way.
        this.getSpellAbility().addEffect(new JoinTheCollectiveEffect());
    }

    private JoinTheCollective(final JoinTheCollective card) {
        super(card);
    }

    @Override
    public JoinTheCollective copy() {
        return new JoinTheCollective(this);
    }
}


class JoinTheCollectiveEffect extends OneShotEffect {

    JoinTheCollectiveEffect() {
        super(Outcome.Benefit);
        staticText = "destroy all creatures. Create X 1/1 colorless Borg artifact creature tokens, where X is the number of nonartifact creatures destroyed this way";
    }

    private JoinTheCollectiveEffect(final JoinTheCollectiveEffect effect) {
        super(effect);
    }

    @Override
    public JoinTheCollectiveEffect copy() {
        return new JoinTheCollectiveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
            StaticFilters.FILTER_PERMANENT_CREATURE,
            source.getControllerId(), source, game
        )) {
            if (permanent.destroy(source, game) && !permanent.isArtifact()) {
                count++;
            }
        }
        if (count > 0) {
            game.processAction();
            new BorgToken().putOntoBattlefield(count, game, source, source.getControllerId(), true, false);
        }
        return true;
    }
}
