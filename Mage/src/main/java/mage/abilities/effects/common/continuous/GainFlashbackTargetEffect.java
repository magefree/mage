package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;

/**
 * @author muz
 */
public class GainFlashbackTargetEffect extends ContinuousEffectImpl {

    boolean textThatCard = false;

    public GainFlashbackTargetEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }

    protected GainFlashbackTargetEffect(final GainFlashbackTargetEffect effect) {
        super(effect);
        this.textThatCard = effect.textThatCard;
    }

    @Override
    public GainFlashbackTargetEffect copy() {
        return new GainFlashbackTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }

        FlashbackAbility ability = new FlashbackAbility(card, card.getManaCost());
        ability.setSourceId(card.getId());
        ability.setControllerId(card.getOwnerId());
        game.getState().addOtherAbility(card, ability);
        return true;
    }

    public GainFlashbackTargetEffect withTextOptions(boolean thatCard) {
        this.textThatCard = thatCard;
        return this;
    }

    @Override
    public String getText(Mode mode) {
        return getTargetPointer().describeTargets(mode.getTargets(), "") +
                " in your graveyard gains flashback until end of turn." +
                " The flashback cost is equal to " +
                (textThatCard ? "that card's" : "its") + " mana cost";
    }


}
