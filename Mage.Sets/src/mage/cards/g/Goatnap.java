package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Goatnap extends CardImpl {

    public Goatnap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn. If that creature is a Goat, it also gets +3/+0 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn"));
        this.getSpellAbility().addEffect(new GoatnapEffect());
    }

    private Goatnap(final Goatnap card) {
        super(card);
    }

    @Override
    public Goatnap copy() {
        return new Goatnap(this);
    }
}

class GoatnapEffect extends OneShotEffect {

    GoatnapEffect() {
        super(Outcome.Benefit);
        staticText = "If that creature is a Goat, it also gets +3/+0 until end of turn.";
    }

    private GoatnapEffect(final GoatnapEffect effect) {
        super(effect);
    }

    @Override
    public GoatnapEffect copy() {
        return new GoatnapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null || !permanent.hasSubtype(SubType.GOAT, game)) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(3, 0, Duration.EndOfTurn), source);
        return true;
    }
}