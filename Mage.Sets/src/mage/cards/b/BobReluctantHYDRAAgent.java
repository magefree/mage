package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author muz
 */
public final class BobReluctantHYDRAAgent extends CardImpl {

    public BobReluctantHYDRAAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.COWARD);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Bob attacks alone, return him to his owner's hand. If you do, each opponent loses 2 life and you gain 2 life.
        this.addAbility(new AttacksAloneSourceTriggeredAbility(new BobReluctantHYDRAAgentEffect()));
    }

    private BobReluctantHYDRAAgent(final BobReluctantHYDRAAgent card) {
        super(card);
    }

    @Override
    public BobReluctantHYDRAAgent copy() {
        return new BobReluctantHYDRAAgent(this);
    }
}

class BobReluctantHYDRAAgentEffect extends OneShotEffect {

    BobReluctantHYDRAAgentEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return him to his owner's hand. If you do, each opponent loses 2 life and you gain 2 life";
    }

    private BobReluctantHYDRAAgentEffect(final BobReluctantHYDRAAgentEffect effect) {
        super(effect);
    }

    @Override
    public BobReluctantHYDRAAgentEffect copy() {
        return new BobReluctantHYDRAAgentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (controller == null || permanent == null || !controller.moveCards(permanent, Zone.HAND, source, game)) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.loseLife(2, game, source, false);
            }
        }
        controller.gainLife(2, game, source);
        return true;
    }
}
