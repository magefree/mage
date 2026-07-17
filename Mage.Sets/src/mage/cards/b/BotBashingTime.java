package mage.cards.b;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BotBashingTime extends CardImpl {

    public BotBashingTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Bot Bashing Time deals 6 damage to target creature. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BotBashingTime(final BotBashingTime card) {
        super(card);
    }

    @Override
    public BotBashingTime copy() {
        return new BotBashingTime(this);
    }
}
