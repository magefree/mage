
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AmplifyEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.AmplifyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class GhastlyRemains extends CardImpl {

    public GhastlyRemains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Amplify 1
        this.addAbility(new AmplifyAbility(AmplifyEffect.AmplifyFactor.Amplify1));

        // At the beginning of your upkeep, if Ghastly Remains is in your graveyard, you may pay {B}{B}{B}. If you do, return Ghastly Remains to your hand.
        this.addAbility(new GhastlyRemainsTriggeredAbility());

    }

    private GhastlyRemains(final GhastlyRemains card) {
        super(card);
    }

    @Override
    public GhastlyRemains copy() {
        return new GhastlyRemains(this);
    }
}

class GhastlyRemainsTriggeredAbility extends BeginningOfUpkeepTriggeredAbility {

    public GhastlyRemainsTriggeredAbility() {
        super(Zone.GRAVEYARD, new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{B}{B}{B}")), TargetController.YOU, false);
    }

    public GhastlyRemainsTriggeredAbility(GhastlyRemainsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BeginningOfUpkeepTriggeredAbility copy() {
        return new GhastlyRemainsTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null && controller.getGraveyard().contains(sourceId)) {
            return super.checkInterveningIfClause(game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if {this} is in your graveyard, you may pay {B}{B}{B}. If you do, return {this} to your hand.";
    }

}
