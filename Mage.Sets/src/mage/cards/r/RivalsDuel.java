package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class RivalsDuel extends CardImpl {

    public RivalsDuel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Choose two target creatures that share no creature types. Those creatures fight each other.
        this.getSpellAbility().addEffect(new RivalsDuelEffect());
        this.getSpellAbility().addTarget(new RivalsDuelTarget());
    }

    private RivalsDuel(final RivalsDuel card) {
        super(card);
    }

    @Override
    public RivalsDuel copy() {
        return new RivalsDuel(this);
    }
}

class RivalsDuelEffect extends OneShotEffect {

    RivalsDuelEffect() {
        super(Outcome.Benefit);
        staticText = "choose two target creatures that share no creature types. Those creatures fight each other";
    }

    private RivalsDuelEffect(final RivalsDuelEffect effect) {
        super(effect);
    }

    @Override
    public RivalsDuelEffect copy() {
        return new RivalsDuelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        System.out.println(permanents.size());
        System.out.println(permanents.get(0));
        System.out.println(permanents.get(1));
        return permanents.size() >= 2 && permanents.get(0).fight(permanents.get(1), source, game);
    }
}

class RivalsDuelTarget extends TargetPermanent {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creatures that share no creature types");

    RivalsDuelTarget() {
        super(2, 2, filter, false);
    }

    private RivalsDuelTarget(final RivalsDuelTarget target) {
        super(target);
    }

    @Override
    public RivalsDuelTarget copy() {
        return new RivalsDuelTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Permanent creature = game.getPermanent(id);
        return creature != null
                && this
                .getTargets()
                .stream()
                .filter(uuid -> !id.equals(uuid))
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .noneMatch(permanent -> permanent.shareCreatureTypes(game, creature));
    }
}
