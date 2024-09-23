package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetPerpetuallyEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

public class GainAbilitySourcePerpetuallyEffect extends OneShotEffect {

    private final Ability ability;
    private final FilterCard filter;

    public GainAbilitySourcePerpetuallyEffect(Ability ability, FilterCard filter) {
        super(Outcome.AddAbility);
        this.staticText = "it perpetually gains " + ability.getRule();
        this.ability = ability;
        this.filter = filter;
    }

    protected GainAbilitySourcePerpetuallyEffect(final GainAbilitySourcePerpetuallyEffect effect) {
        super(effect);
        this.ability = effect.ability;
        this.filter = effect.filter;
    }

    @Override
    public GainAbilitySourcePerpetuallyEffect copy() {
        return new GainAbilitySourcePerpetuallyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(source.getSourceId());
            if (!controller.getHand().isEmpty()) {
                game.addEffect(new GainAbilityTargetPerpetuallyEffect(ability)
                        .setTargetPointer(new FixedTarget(card, game)), source);

            }
            return true;
        }
        return false;
    }
}
