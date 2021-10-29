
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoUnlessAnyPlayerPaysEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class AetherRift extends CardImpl {

    public AetherRift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}{G}");


        // At the beginning of your upkeep, discard a card at random. If you discard a creature card this way, return it from your graveyard to the battlefield unless any player pays 5 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AetherRiftEffect(), TargetController.YOU, false));

    }

    private AetherRift(final AetherRift card) {
        super(card);
    }

    @Override
    public AetherRift copy() {
        return new AetherRift(this);
    }
}

class AetherRiftEffect extends OneShotEffect {

    public AetherRiftEffect() {
        super(Outcome.Benefit);
        this.staticText = "discard a card at random. If you discard a creature card this way, return it from your graveyard to the battlefield unless any player pays 5 life";
    }

    public AetherRiftEffect(final AetherRiftEffect effect) {
        super(effect);
    }

    @Override
    public AetherRiftEffect copy() {
        return new AetherRiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.discardOne(true, false, source, game);
            if (card != null && card.isCreature(game)) {
                Effect returnEffect = new ReturnFromGraveyardToBattlefieldTargetEffect();
                returnEffect.setTargetPointer(new FixedTarget(card.getId(), game));
                Effect doEffect = new DoUnlessAnyPlayerPaysEffect(returnEffect, new PayLifeCost(5),
                        "Pay 5 life to prevent " + card.getLogName() + " to return from graveyard to battlefield?");
                return doEffect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
