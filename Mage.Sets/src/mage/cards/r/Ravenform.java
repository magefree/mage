package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BlueBirdToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ravenform extends CardImpl {

    public Ravenform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Exile target artifact or creature. Its controller creates a 1/1 blue Bird creature token with flying.
        this.getSpellAbility().addEffect(new RavenformEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));

        // Foretell {U}
        this.addAbility(new ForetellAbility(this, "{U}"));
    }

    private Ravenform(final Ravenform card) {
        super(card);
    }

    @Override
    public Ravenform copy() {
        return new Ravenform(this);
    }
}

class RavenformEffect extends OneShotEffect {

    RavenformEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Exile target artifact or creature. " +
                "Its controller creates a 1/1 blue Bird creature token with flying.";
    }

    private RavenformEffect(final RavenformEffect effect) {
        super(effect);
    }

    @Override
    public RavenformEffect copy() {
        return new RavenformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        player.moveCards(permanent, Zone.EXILED, source, game);
        new BlueBirdToken().putOntoBattlefield(1, game, source, player.getId());
        return true;
    }
}
