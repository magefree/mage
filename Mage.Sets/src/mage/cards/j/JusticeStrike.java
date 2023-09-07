package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class JusticeStrike extends CardImpl {

    public JusticeStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}");

        // Target creature deals damage to itself equal to its power.
        this.getSpellAbility().addEffect(new JusticeStrikeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private JusticeStrike(final JusticeStrike card) {
        super(card);
    }

    @Override
    public JusticeStrike copy() {
        return new JusticeStrike(this);
    }
}

class JusticeStrikeEffect extends OneShotEffect {

    public JusticeStrikeEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature deals damage to itself equal to its power.";
    }

    private JusticeStrikeEffect(final JusticeStrikeEffect effect) {
        super(effect);
    }

    @Override
    public JusticeStrikeEffect copy() {
        return new JusticeStrikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return permanent.damage(permanent.getPower().getValue(), permanent.getId(), source, game, false, true) > 0;
    }
}
