
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SanctuaryInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class AnaSanctuary extends CardImpl {

    public AnaSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of your upkeep, if you control a blue or black permanent, target creature gets +1/+1 until end of turn. If you control a blue permanent and a black permanent, that creature gets +5/+5 until end of turn instead.
        Ability ability = new SanctuaryInterveningIfTriggeredAbility(
                new BoostEffect(1), new BoostEffect(5), ObjectColor.BLACK, ObjectColor.BLUE,
                "At the beginning of your upkeep, if you control a blue or black permanent, "
                + "target creature gets +1/+1 until end of turn. If you control a blue permanent and a black permanent, that creature gets +5/+5 until end of turn instead."
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AnaSanctuary(final AnaSanctuary card) {
        super(card);
    }

    @Override
    public AnaSanctuary copy() {
        return new AnaSanctuary(this);
    }
}

class BoostEffect extends OneShotEffect {

    private final int amount;

    BoostEffect(int amount) {
        super(Outcome.Benefit);
        this.amount = amount;
    }

    BoostEffect(final BoostEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public BoostEffect copy() {
        return new BoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ContinuousEffect effect = new BoostTargetEffect(amount, amount, Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        game.addEffect(effect, source);
        return true;
    }
}
