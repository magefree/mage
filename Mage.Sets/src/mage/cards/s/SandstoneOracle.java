
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public final class SandstoneOracle extends CardImpl {

    public SandstoneOracle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Sandstone Oracle enters the battlefield, choose an opponent. If that player has more cards in hand that you, draw cards equal to the difference.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SandstoneOracleEffect()));
    }

    private SandstoneOracle(final SandstoneOracle card) {
        super(card);
    }

    @Override
    public SandstoneOracle copy() {
        return new SandstoneOracle(this);
    }
}

class SandstoneOracleEffect extends OneShotEffect {

    SandstoneOracleEffect() {
        super(Outcome.DrawCard);
        this.staticText = "choose an opponent. If that player has more cards in hand than you, draw cards equal to the difference";
    }

    SandstoneOracleEffect(final SandstoneOracleEffect effect) {
        super(effect);
    }

    @Override
    public SandstoneOracleEffect copy() {
        return new SandstoneOracleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            TargetOpponent target = new TargetOpponent(true);
            if (controller.choose(Outcome.DrawCard, target, source, game)) {
                Player opponent = game.getPlayer(target.getFirstTarget());
                if (opponent != null) {
                    game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " has chosen " + opponent.getLogName());
                    int cardsDiff = opponent.getHand().size() - controller.getHand().size();
                    if (cardsDiff > 0) {
                        controller.drawCards(cardsDiff, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
