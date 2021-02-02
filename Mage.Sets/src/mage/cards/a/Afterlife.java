package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Backfir3
 */
public final class Afterlife extends CardImpl {

    public Afterlife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Destroy target creature. It can't be regenerated. Its controller puts a
        // 1/1 white Spirit creature token with flying.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addEffect(new AfterlifeEffect());
    }

    private Afterlife(final Afterlife card) {
        super(card);
    }

    @Override
    public Afterlife copy() {
        return new Afterlife(this);
    }
}

class AfterlifeEffect extends OneShotEffect {

    public AfterlifeEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Its controller creates a 1/1 white Spirit creature token with flying";
    }

    public AfterlifeEffect(final AfterlifeEffect effect) {
        super(effect);
    }

    @Override
    public AfterlifeEffect copy() {
        return new AfterlifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            SpiritWhiteToken token = new SpiritWhiteToken();
            token.putOntoBattlefield(1, game, source, permanent.getControllerId());
        }
        return true;
    }

}
