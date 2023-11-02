package mage.abilities.effects.common.discard;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 * @author xenohedron
 */
public class LookTargetHandChooseDiscardEffect extends OneShotEffect {

    private final boolean upTo;
    private final DynamicValue numberToDiscard;

    public LookTargetHandChooseDiscardEffect() {
        this(false, 1);
    }

    public LookTargetHandChooseDiscardEffect(boolean upTo, int numberToDiscard) {
        this(upTo, StaticValue.get(numberToDiscard));
    }

    public LookTargetHandChooseDiscardEffect(boolean upTo, DynamicValue numberToDiscard) {
        super(Outcome.Discard);
        this.upTo = upTo;
        this.numberToDiscard = numberToDiscard;
    }

    protected LookTargetHandChooseDiscardEffect(final LookTargetHandChooseDiscardEffect effect) {
        super(effect);
        this.upTo = effect.upTo;
        this.numberToDiscard = effect.numberToDiscard;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player == null || controller == null) {
            return false;
        }
        int num = numberToDiscard.calculate(game, source, this);
        if (num == 0) {
            if (!player.getHand().isEmpty()) {
                controller.lookAtCards("Looking at hand", player.getHand(), game);
            }
            return true;
        }
        TargetCard target = new TargetCardInHand(upTo ? 0 : num, num, num > 1 ? StaticFilters.FILTER_CARD_CARDS : StaticFilters.FILTER_CARD);
        if (controller.choose(Outcome.Discard, player.getHand(), target, source, game)) {
            player.discard(new CardsImpl(target.getTargets()), false, source, game);
        }
        return true;
    }

    @Override
    public LookTargetHandChooseDiscardEffect copy() {
        return new LookTargetHandChooseDiscardEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String numberValue = numberToDiscard instanceof StaticValue ?
                CardUtil.numberToText(((StaticValue) numberToDiscard).getValue(), "a") : "X";
        boolean plural = !numberValue.equals("a");
        String targetDescription = getTargetPointer().describeTargets(mode.getTargets(), "that player");
        return "look at " + targetDescription + "'s hand and choose " + (upTo ? "up to " : "") + numberValue
                + (plural ? " cards" : " card") + " from it. "
                + (targetDescription.equals("that player") ? "The" : "That")
                + " player discards " + (plural ? "those cards." : "that card.");
    }
}
