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
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int lethal = Math.min(permanent.getLethalDamage(source.getSourceId(), game), 4);
        permanent.damage(4, source.getSourceId(), source, game);
        if (lethal < 4) {
            new BloodToken().putOntoBattlefield(4 - lethal, game, source);
        }
        return true;
    }
}
