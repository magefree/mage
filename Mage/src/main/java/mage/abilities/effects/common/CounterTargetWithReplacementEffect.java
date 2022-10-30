package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.game.Game;
import mage.players.Player;

/**
 * @author nantuko, North
 */
public class CounterTargetWithReplacementEffect extends OneShotEffect {

    private final PutCards putIt;

    /**
     *
     * @param putIt
     */
    public CounterTargetWithReplacementEffect(PutCards putIt) {
        super(Outcome.Detriment);
        this.putIt = putIt;
    }

    public CounterTargetWithReplacementEffect(final CounterTargetWithReplacementEffect effect) {
        super(effect);
        this.putIt = effect.putIt;
    }

    @Override
    public CounterTargetWithReplacementEffect copy() {
        return new CounterTargetWithReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return game.getStack().counter(getTargetPointer().getFirst(game, source), source, game, putIt);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("counter ");
        sb.append(getTargetPointer().describeTargets(mode.getTargets(), "it"));
        sb.append(". If that spell is countered this way, ");
        if (putIt == PutCards.EXILED) {
            sb.append("exile it instead of putting it into its owner's graveyard");
        } else {
            sb.append(putIt == PutCards.TOP_OR_BOTTOM ? "put that card " : "put it ");
            sb.append(putIt.getMessage(true, false));
            sb.append(" instead of into that player's graveyard");
        }
        return sb.toString();
    }
}
