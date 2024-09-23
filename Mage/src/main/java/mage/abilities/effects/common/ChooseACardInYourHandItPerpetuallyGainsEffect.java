package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetPerpetuallyEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.util.RandomUtil;

public class ChooseACardInYourHandItPerpetuallyGainsEffect extends OneShotEffect {

    private final Ability ability;
    private final FilterCard filter;

    public ChooseACardInYourHandItPerpetuallyGainsEffect(Ability ability, FilterCard filter) {
        super(Outcome.AddAbility);
        this.staticText = "choose " + CardUtil.addArticle(filter.getMessage()) + " in your hand. It perpetually gains " + ability.getRule();
        this.ability = ability;
        this.filter = filter;
    }

    protected ChooseACardInYourHandItPerpetuallyGainsEffect(final ChooseACardInYourHandItPerpetuallyGainsEffect effect) {
        super(effect);
        this.ability = effect.ability;
        this.filter = effect.filter;
    }

    @Override
    public ChooseACardInYourHandItPerpetuallyGainsEffect copy() {
        return new ChooseACardInYourHandItPerpetuallyGainsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (!controller.getHand().isEmpty()) {
                Card cardFromHand = null;
                if (controller.getHand().size() > 1) {
                    TargetCard target = new TargetCardInHand(filter);
                    if (controller.choose(Outcome.AddAbility, controller.getHand(), target, source, game)) {
                        cardFromHand = game.getCard(target.getFirstTarget());
                    }
                } else {
                    cardFromHand = RandomUtil.randomFromCollection(controller.getHand().getCards(filter, game));
                }
                if (cardFromHand == null) {
                    return false;
                }
                game.addEffect(new GainAbilityTargetPerpetuallyEffect(ability)
                        .setTargetPointer(new FixedTarget(cardFromHand, game)), source);

            }
            return true;
        }
        return false;
    }
}
