
package mage.cards.t;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public final class Terminus extends CardImpl {

    public Terminus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}");



        // Put all creatures on the bottom of their owners' libraries.
        this.getSpellAbility().addEffect(new TerminusEffect());

        this.addAbility(new MiracleAbility(this, new ManaCostsImpl("{W}")));
    }

    public Terminus(final Terminus card) {
        super(card);
    }

    @Override
    public Terminus copy() {
        return new Terminus(this);
    }
}

class TerminusEffect extends OneShotEffect {

    public TerminusEffect() {
        super(Outcome.Removal);
        this.staticText = "Put all creatures on the bottom of their owners' libraries";
    }

    public TerminusEffect(final TerminusEffect effect) {
        super(effect);
    }

    @Override
    public TerminusEffect copy() {
        return new TerminusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURES, source.getControllerId(), source.getSourceId(), game);
        for (Permanent permanent : permanents) {
            permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
        }
        return true;
    }
}
