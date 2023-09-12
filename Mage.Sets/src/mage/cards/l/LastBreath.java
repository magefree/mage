package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class LastBreath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public LastBreath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target creature with power 2 or less. Its controller gains 4 life.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new LastBreathEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

    }

    private LastBreath(final LastBreath card) {
        super(card);
    }

    @Override
    public LastBreath copy() {
        return new LastBreath(this);
    }
}

class LastBreathEffect extends OneShotEffect {

    LastBreathEffect() {
        super(Outcome.GainLife);
        staticText = "Its controller gains 4 life";
    }

    private LastBreathEffect(final LastBreathEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (target != null) {
            Player player = game.getPlayer(target.getControllerId());
            if (player != null) {
                player.gainLife(4, game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public LastBreathEffect copy() {
        return new LastBreathEffect(this);
    }

}
