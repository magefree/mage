package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Elemental44Token;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Resculpt extends CardImpl {

    public Resculpt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Exile target artifact or creature. Its controller creates a 4/4 blue and red Elemental creature token.
        this.getSpellAbility().addEffect(new ResculptEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
    }

    private Resculpt(final Resculpt card) {
        super(card);
    }

    @Override
    public Resculpt copy() {
        return new Resculpt(this);
    }
}

class ResculptEffect extends OneShotEffect {

    ResculptEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Exile target artifact or creature. " +
                "Its controller creates a 4/4 blue and red Elemental creature token";
    }

    private ResculptEffect(final ResculptEffect effect) {
        super(effect);
    }

    @Override
    public ResculptEffect copy() {
        return new ResculptEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        player.moveCards(permanent, Zone.EXILED, source, game);
        new Elemental44Token().putOntoBattlefield(1, game, source, player.getId());
        return true;
    }
}
