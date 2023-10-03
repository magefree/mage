package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

public class ReturnCreatureFromGraveyardToBattlefieldAndGainHasteEffect extends OneShotEffect {

    public ReturnCreatureFromGraveyardToBattlefieldAndGainHasteEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put target creature card from a graveyard onto the battlefield under your control. It gains haste";
    }

    protected ReturnCreatureFromGraveyardToBattlefieldAndGainHasteEffect(final ReturnCreatureFromGraveyardToBattlefieldAndGainHasteEffect effect) {
        super(effect);
    }

    @Override
    public ReturnCreatureFromGraveyardToBattlefieldAndGainHasteEffect copy() {
        return new ReturnCreatureFromGraveyardToBattlefieldAndGainHasteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(source.getFirstTarget());
        if (card != null) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            Permanent permanent = game.getPermanent(card.getId());
            if (permanent != null) {
                ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
            return true;
        }

        return false;
    }
}
