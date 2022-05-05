package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.condition.common.ControlArtifactAndEnchantmentCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.ControlArtifactAndEnchantmentHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WhenWeWereYoung extends CardImpl {

    public WhenWeWereYoung(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Up to two target creatures each get +2/+2 until end of turn. If you control an artifact and an enchantment, those creatures also gain lifelink until end of turn.
        this.getSpellAbility().addEffect(new WhenWeWereYoungEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addHint(ControlArtifactAndEnchantmentHint.instance);
    }

    private WhenWeWereYoung(final WhenWeWereYoung card) {
        super(card);
    }

    @Override
    public WhenWeWereYoung copy() {
        return new WhenWeWereYoung(this);
    }
}

class WhenWeWereYoungEffect extends OneShotEffect {

    WhenWeWereYoungEffect() {
        super(Outcome.Benefit);
        staticText = "up to two target creatures each get +2/+2 until end of turn. " +
                "If you control an artifact and an enchantment, " +
                "those creatures also gain lifelink until end of turn";
    }

    private WhenWeWereYoungEffect(final WhenWeWereYoungEffect effect) {
        super(effect);
    }

    @Override
    public WhenWeWereYoungEffect copy() {
        return new WhenWeWereYoungEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new BoostTargetEffect(2, 2), source);
        if (ControlArtifactAndEnchantmentCondition.instance.apply(game, source)) {
            game.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance()), source);
        }
        return true;
    }
}
