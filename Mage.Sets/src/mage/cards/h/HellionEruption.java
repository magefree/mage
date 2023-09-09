
package mage.cards.h;

import java.util.List;
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
import mage.game.permanent.token.HellionToken;

/**
 *
 * @author North
 */
public final class HellionEruption extends CardImpl {

    public HellionEruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}");

        // Sacrifice all creatures you control, then create that many 4/4 red Hellion creature tokens.
        this.getSpellAbility().addEffect(new HellionEruptionEffect());
    }

    private HellionEruption(final HellionEruption card) {
        super(card);
    }

    @Override
    public HellionEruption copy() {
        return new HellionEruption(this);
    }
}

class HellionEruptionEffect extends OneShotEffect {

    public HellionEruptionEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Sacrifice all creatures you control, then create that many 4/4 red Hellion creature tokens";
    }

    private HellionEruptionEffect(final HellionEruptionEffect effect) {
        super(effect);
    }

    @Override
    public HellionEruptionEffect copy() {
        return new HellionEruptionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            permanent.sacrifice(source, game);
        }
        (new HellionToken()).putOntoBattlefield(permanents.size(), game, source, source.getControllerId());
        return true;
    }

}
