package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BloodToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LacerateFlesh extends CardImpl {

    public LacerateFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Lacerate Flesh deals 4 damage to target creature. Create a number of Blood tokens equal to the amount of excess damage dealt to that creature this way.
        this.getSpellAbility().addEffect(new LacerateFleshEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LacerateFlesh(final LacerateFlesh card) {
        super(card);
    }

    @Override
    public LacerateFlesh copy() {
        return new LacerateFlesh(this);
    }
}

class LacerateFleshEffect extends OneShotEffect {

    LacerateFleshEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 4 damage to target creature. Create a number of Blood tokens " +
                "equal to the amount of excess damage dealt to that creature this way";
    }

    private LacerateFleshEffect(final LacerateFleshEffect effect) {
        super(effect);
    }

    @Override
    public LacerateFleshEffect copy() {
        return new LacerateFleshEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int excess = permanent.damageWithExcess(4, source, game);
        if (excess > 0) {
            new BloodToken().putOntoBattlefield(excess, game, source);
        }
        return true;
    }
}
