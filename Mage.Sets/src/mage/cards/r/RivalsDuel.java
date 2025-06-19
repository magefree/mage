package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RivalsDuel extends CardImpl {

    public RivalsDuel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Choose two target creatures that share no creature types. Those creatures fight each other.
        this.getSpellAbility().addEffect(new FightTargetsEffect()
                .setText("Choose two target creatures that share no creature types. Those creatures fight each other."));
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
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Permanent creature = game.getPermanent(id);
        return creature != null
                && this
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .noneMatch(permanent -> permanent.shareCreatureTypes(game, creature));
    }
}
