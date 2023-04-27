package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShellShield extends CardImpl {

    public ShellShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Kicker {1}
        this.addAbility(new KickerAbility("{1}"));

        // Target creature you control gets +0/+3 until end of turn. If this spell was kicked, that creature also gains hexproof until end of turn.
        this.getSpellAbility().addEffect(new ShellShieldEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private ShellShield(final ShellShield card) {
        super(card);
    }

    @Override
    public ShellShield copy() {
        return new ShellShield(this);
    }
}

class ShellShieldEffect extends OneShotEffect {

    ShellShieldEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature you control gets +0/+3 until end of turn. " +
                "If this spell was kicked, that creature also gains hexproof until end of turn.";
    }

    private ShellShieldEffect(final ShellShieldEffect effect) {
        super(effect);
    }

    @Override
    public ShellShieldEffect copy() {
        return new ShellShieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new BoostTargetEffect(0, 3, Duration.EndOfTurn), source);
        if (KickedCondition.ONCE.apply(game, source)) {
            game.addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn), source);
        }
        return true;
    }
}
