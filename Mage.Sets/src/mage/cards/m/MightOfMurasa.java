package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MightOfMurasa extends CardImpl {

    public MightOfMurasa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Kicker {2}{G}
        this.addAbility(new KickerAbility("{2}{G}"));

        // Target creature gets +3/+3 until end of turn. If this spell was kicked, that creature gets +5/+5 until end of turn instead.
        this.getSpellAbility().addEffect(new MightOfMurasaEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MightOfMurasa(final MightOfMurasa card) {
        super(card);
    }

    @Override
    public MightOfMurasa copy() {
        return new MightOfMurasa(this);
    }
}

class MightOfMurasaEffect extends OneShotEffect {

    MightOfMurasaEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature gets +3/+3 until end of turn. " +
                "If this spell was kicked, that creature gets +5/+5 until end of turn instead.";
    }

    private MightOfMurasaEffect(final MightOfMurasaEffect effect) {
        super(effect);
    }

    @Override
    public MightOfMurasaEffect copy() {
        return new MightOfMurasaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int i = KickedCondition.ONCE.apply(game, source) ? 5 : 3;
        game.addEffect(new BoostTargetEffect(i, i, Duration.EndOfTurn), source);
        return true;
    }
}
