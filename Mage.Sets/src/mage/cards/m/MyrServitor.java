
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class MyrServitor extends CardImpl {

    public MyrServitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.MYR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, if Myr Servitor is on the battlefield, each player returns all cards named Myr Servitor from their graveyard to the battlefield.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new MyrServitorReturnEffect(), TargetController.YOU, false),
                SourceOnBattlefieldCondition.instance,
                "At the beginning of your upkeep, if {this} is on the battlefield, each player returns all cards named Myr Servitor from their graveyard to the battlefield"
        ));

    }

    private MyrServitor(final MyrServitor card) {
        super(card);
    }

    @Override
    public MyrServitor copy() {
        return new MyrServitor(this);
    }
}

class MyrServitorReturnEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("cards named Myr Servitor");

    static {
        filter.add(new NamePredicate("Myr Servitor"));
    }

    public MyrServitorReturnEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "if {this} is on the battlefield, each player returns all cards named Myr Servitor from their graveyard to the battlefield";
    }

    private MyrServitorReturnEffect(final MyrServitorReturnEffect effect) {
        super(effect);
    }

    @Override
    public MyrServitorReturnEffect copy() {
        return new MyrServitorReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    controller.moveCards(player.getGraveyard().getCards(filter, game), Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
