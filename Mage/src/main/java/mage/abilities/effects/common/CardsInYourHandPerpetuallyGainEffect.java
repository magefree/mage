package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetPerpetuallyEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.target.targetpointer.TargetPointer;
import mage.util.CardUtil;

import java.util.Set;

public class CardsInYourHandPerpetuallyGainEffect extends OneShotEffect {

    private final Ability ability;
    private final FilterCard filter;

    public CardsInYourHandPerpetuallyGainEffect(Ability ability, FilterCard filter) {
        super(Outcome.AddAbility);
        this.staticText = filter.getMessage() + " perpetually gain ";
        if(!(ability instanceof MageSingleton)){
            this.staticText += "\"" + CardUtil.getTextWithFirstCharUpperCase(ability.getRule()) + ("\"");
        }
        else {
            ability.getRule();
        }
        this.ability = ability;
        this.filter = filter;

    }


    private CardsInYourHandPerpetuallyGainEffect(final CardsInYourHandPerpetuallyGainEffect effect) {
        super(effect);
        this.ability = effect.ability;
        this.filter = effect.filter;
    }

    @Override
    public CardsInYourHandPerpetuallyGainEffect copy() {
        return new CardsInYourHandPerpetuallyGainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null && !controller.getHand().isEmpty()) {
            Set<Card> cards = controller.getHand().getCards(filter, game);
            if (cards == null) {
                return false;
            }
            TargetPointer pointer = new FixedTargets(cards, game);

            ContinuousEffect effect = new GainAbilityTargetPerpetuallyEffect(ability).setTargetPointer(pointer);
            game.addEffect(effect, source);

        }
        return true;
    }
}
