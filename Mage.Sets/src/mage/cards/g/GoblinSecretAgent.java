
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
/**
 *
 * @author L_J
 */
public final class GoblinSecretAgent extends CardImpl {

    public GoblinSecretAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of your upkeep, reveal a card from your hand at random.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GoblinSecretAgentEffect(), TargetController.YOU, false));
    }

    private GoblinSecretAgent(final GoblinSecretAgent card) {
        super(card);
    }

    @Override
    public GoblinSecretAgent copy() {
        return new GoblinSecretAgent(this);
    }
}

class GoblinSecretAgentEffect extends OneShotEffect {

    public GoblinSecretAgentEffect() {
        super(Outcome.Detriment);
        this.staticText = "reveal a card from your hand at random";
    }
    
    public GoblinSecretAgentEffect(final GoblinSecretAgentEffect effect) {
        super(effect);
    }

    @Override
    public GoblinSecretAgentEffect copy() {
        return new GoblinSecretAgentEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            if (!controller.getHand().isEmpty()) {
                CardsImpl randomCard = new CardsImpl();
                Card card = controller.getHand().getRandom(game);
                randomCard.add(card);
                controller.revealCards(sourceObject.getIdName(), randomCard, game);
            }
            return true;
        }
        return false;
    }
    
}
