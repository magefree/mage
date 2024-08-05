package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.FlipCoinEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class InvertPolarity extends CardImpl {

    public InvertPolarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}{R}");

        // Choose target spell, then flip a coin. If you win the flip, gain control of that spell and you may choose new targets for it. If you lose the flip, counter that spell.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new InfoEffect("Choose target spell"));
        this.getSpellAbility().addEffect(new FlipCoinEffect(
                new InvertPolarityGainControlTargetEffect(),
                new CounterTargetEffect().setText("counter that spell"),
                Outcome.Detriment
        ).concatBy(", then"));
    }

    private InvertPolarity(final InvertPolarity card) {
        super(card);
    }

    @Override
    public InvertPolarity copy() {
        return new InvertPolarity(this);
    }
}

class InvertPolarityGainControlTargetEffect extends OneShotEffect {

    InvertPolarityGainControlTargetEffect() {
        super(Outcome.GainControl);
        staticText = "gain control of that spell and you may choose new targets for it";
    }

    private InvertPolarityGainControlTargetEffect(final InvertPolarityGainControlTargetEffect effect) {
        super(effect);
    }

    @Override
    public InvertPolarityGainControlTargetEffect copy() {
        return new InvertPolarityGainControlTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
        if (controller != null && spell != null) {
            spell.setControllerId(controller.getId());
            spell.chooseNewTargets(game, controller.getId(), false, false, null);
            return true;
        }
        return false;
    }
}
