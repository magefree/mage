package mage.cards.u;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class UrzasBauble extends CardImpl {

    public UrzasBauble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // {tap}, Sacrifice Urza's Bauble: Look at a card at random in target player's hand. You draw a card at the beginning of the next turn's upkeep.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookAtRandomCardEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false));
        this.addAbility(ability);
    }

    private UrzasBauble(final UrzasBauble card) {
        super(card);
    }

    @Override
    public UrzasBauble copy() {
        return new UrzasBauble(this);
    }
}

class LookAtRandomCardEffect extends OneShotEffect {

    public LookAtRandomCardEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at a card at random in target player's hand";
    }

    public LookAtRandomCardEffect(final LookAtRandomCardEffect effect) {
        super(effect);
    }

    @Override
    public LookAtRandomCardEffect copy() {
        return new LookAtRandomCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source);
        if (controller != null
                && targetPlayer != null
                && sourceObject != null) {
            if (!targetPlayer.getHand().isEmpty()) {
                Cards randomCard = new CardsImpl();
                Card card = targetPlayer.getHand().getRandom(game);
                randomCard.add(card);
                controller.lookAtCards(sourceObject.getName(), randomCard, game);
                game.informPlayer(targetPlayer, "The random card from your hand shown to " + controller.getName() + " is " + card.getName());
            }
            return true;
        }
        return false;
    }

}
