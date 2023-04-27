package mage.cards.i;

import java.util.ArrayList;
import java.util.List;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanentSameController;

import java.util.UUID;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public final class Incriminate extends CardImpl {

    public Incriminate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose two target creatures controlled by the same player. That player sacrifices one of them.
        this.getSpellAbility().addEffect(new IncriminateEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentSameController(2));
    }

    private Incriminate(final Incriminate card) {
        super(card);
    }

    @Override
    public Incriminate copy() {
        return new Incriminate(this);
    }
}

class IncriminateEffect extends OneShotEffect {

    IncriminateEffect() {
        super(Outcome.Detriment);
        staticText = "choose two target creatures controlled by the same player. That player sacrifices one of them";
    }

    private IncriminateEffect(final IncriminateEffect effect) {
        super(effect);
    }

    @Override
    public IncriminateEffect copy() {
        return new IncriminateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // the OP can fix the stream to match this working setup if so desired
        Player player = null;
        FilterPermanent filter = new FilterPermanent("creature to sacrifice");
        List<PermanentIdPredicate> permanentIdPredicates = new ArrayList<>();
        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            permanentIdPredicates.add(new PermanentIdPredicate(game.getPermanent(targetId).getId()));
            player = game.getPlayer(game.getPermanent(targetId).getControllerId());
        }
        if (permanentIdPredicates.isEmpty()
                || player == null) {
            return false;
        }
        filter.add(mage.filter.predicate.Predicates.or(permanentIdPredicates));
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        player.chooseTarget(Outcome.Sacrifice, target, source, game);
        Permanent sacrificeCreature = game.getPermanent(target.getFirstTarget());
        return sacrificeCreature != null
                && sacrificeCreature.sacrifice(source, game);
    }
}
