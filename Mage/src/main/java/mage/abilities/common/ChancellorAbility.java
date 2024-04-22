
package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.OpeningHandAction;
import mage.abilities.StaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ChancellorAbility extends StaticAbility implements OpeningHandAction {

    public ChancellorAbility(DelayedTriggeredAbility ability, String text) {
        super(Zone.HAND, new ChancellorEffect(ability, text));
    }

    public ChancellorAbility(OneShotEffect effect) {
        super(Zone.HAND, effect);
    }

    protected ChancellorAbility(final ChancellorAbility ability) {
        super(ability);
    }

    @Override
    public ChancellorAbility copy() {
        return new ChancellorAbility(this);
    }

    @Override
    public String getRule() {
        return "You may reveal this card from your opening hand. If you do, " + super.getRule();
    }

    @Override
    public boolean askUseOpeningHandAction(Card card, Player player, Game game) {
        return player.chooseUse(Outcome.PutCardInPlay, "Reveal " + card.getIdName() + '?', this, game);
    }

    @Override
    public boolean isOpeningHandActionAllowed(Card card, Player player, Game game) {
        return true;
    }

    @Override
    public void doOpeningHandAction(Card card, Player player, Game game) {
        Cards cards = new CardsImpl();
        cards.add(card);
        player.revealCards(card.getName(), cards, game);
        this.resolve(game);
    }
}

class ChancellorEffect extends OneShotEffect {

    private final DelayedTriggeredAbility ability;

    ChancellorEffect(DelayedTriggeredAbility ability, String text) {
        super(Outcome.Benefit);
        this.ability = ability;
        staticText = text;
    }

    ChancellorEffect(ChancellorEffect effect) {
        super(effect);
        this.ability = effect.ability;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(ability, source);
        return true;
    }

    @Override
    public ChancellorEffect copy() {
        return new ChancellorEffect(this);
    }

}
