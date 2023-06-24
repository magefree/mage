package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RegentsAuthority extends CardImpl {

    public RegentsAuthority(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target creature gets +2/+2 until end of turn. If it's an enchantment creature or legendary creature, instead put a +1/+1 counter on it and it gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new RegentsAuthorityEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RegentsAuthority(final RegentsAuthority card) {
        super(card);
    }

    @Override
    public RegentsAuthority copy() {
        return new RegentsAuthority(this);
    }
}

class RegentsAuthorityEffect extends OneShotEffect {

    RegentsAuthorityEffect() {
        super(Outcome.Benefit);
        staticText = "target creature gets +2/+2 until end of turn. If it's an enchantment creature " +
                "or legendary creature, instead put a +1/+1 counter on it and it gets +1/+1 until end of turn";
    }

    private RegentsAuthorityEffect(final RegentsAuthorityEffect effect) {
        super(effect);
    }

    @Override
    public RegentsAuthorityEffect copy() {
        return new RegentsAuthorityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (!permanent.isEnchantment(game)
                && (!permanent.isCreature(game)
                || !permanent.isLegendary(game))) {
            game.addEffect(new BoostTargetEffect(2, 2), source);
            return true;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        game.addEffect(new BoostTargetEffect(1, 1), source);
        return true;
    }
}
