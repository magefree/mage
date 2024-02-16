package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentSameController;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jeffwadsworth
 */
public final class BarrinsSpite extends CardImpl {

    public BarrinsSpite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");

        // Choose two target creatures controlled by the same player. Their controller chooses and sacrifices one of them. Return the other to its owner's hand.
        this.getSpellAbility().addEffect(new BarrinsSpiteEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentSameController(2));
    }

    private BarrinsSpite(final BarrinsSpite card) {
        super(card);
    }

    @Override
    public BarrinsSpite copy() {
        return new BarrinsSpite(this);
    }
}

class BarrinsSpiteEffect extends OneShotEffect {

    BarrinsSpiteEffect() {
        super(Outcome.Detriment);
        this.staticText = "Choose two target creatures controlled by the same player. " +
                "Their controller chooses and sacrifices one of them. Return the other to its owner's hand";
    }

    private BarrinsSpiteEffect(final BarrinsSpiteEffect effect) {
        super(effect);
    }

    @Override
    public BarrinsSpiteEffect copy() {
        return new BarrinsSpiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = source
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        if (permanents.size() == 1) {
            permanents.get(0).sacrifice(source, game);
            return true;
        }
        if (permanents.size() > 2) {
            throw new IllegalStateException("Too many permanents in list, shouldn't be possible");
        }
        Player player = game.getPlayer(permanents.get(0).getControllerId());
        Player controller = game.getPlayer(source.getControllerId());
        if (player == null || controller == null) {
            return false;
        }
        Permanent perm1 = permanents.get(0);
        Permanent perm2 = permanents.get(1);
        if (player.chooseUse(
                outcome, "Choose which permanent to sacrifice",
                "The other will be returned to your hand",
                perm1.getIdName(), perm2.getIdName(), source, game
        )) {
            perm1.sacrifice(source, game);
            controller.moveCards(perm2, Zone.HAND, source, game);
            return true;
        }
        perm2.sacrifice(source, game);
        controller.moveCards(perm1, Zone.HAND, source, game);
        return true;
    }
}
