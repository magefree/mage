package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JusticiarsPortal extends CardImpl {

    public JusticiarsPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target creature you control, then return that card to the battlefield under its owner's control. It gains first strike until end of turn.
        this.getSpellAbility().addEffect(new JusticiarsPortalEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private JusticiarsPortal(final JusticiarsPortal card) {
        super(card);
    }

    @Override
    public JusticiarsPortal copy() {
        return new JusticiarsPortal(this);
    }
}

class JusticiarsPortalEffect extends OneShotEffect {

    JusticiarsPortalEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target creature you control, then return that card to the battlefield " +
                "under its owner's control. It gains first strike until end of turn.";
    }

    private JusticiarsPortalEffect(final JusticiarsPortalEffect effect) {
        super(effect);
    }

    @Override
    public JusticiarsPortalEffect copy() {
        return new JusticiarsPortalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        new ExileThenReturnTargetEffect(false, true).apply(game, source);
        ContinuousEffect effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(targetId, game));
        game.addEffect(effect, source);
        return true;
    }
}
