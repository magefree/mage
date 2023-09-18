package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Loki
 */
public final class AgentOfMasks extends CardImpl {

    public AgentOfMasks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        // At the beginning of your upkeep, each opponent loses 1 life. You gain life equal to the life lost this way.
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AgentOfMasksEffect(), TargetController.YOU, false));
    }

    private AgentOfMasks(final AgentOfMasks card) {
        super(card);
    }

    @Override
    public AgentOfMasks copy() {
        return new AgentOfMasks(this);
    }
}

class AgentOfMasksEffect extends OneShotEffect {
    public AgentOfMasksEffect() {
        super(Outcome.Damage);
        staticText = "each opponent loses 1 life. You gain life equal to the life lost this way";
    }

    private AgentOfMasksEffect(final AgentOfMasksEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int loseLife = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            loseLife += game.getPlayer(opponentId).loseLife(1, game, source, false);
        }
        if (loseLife > 0)
            game.getPlayer(source.getControllerId()).gainLife(loseLife, game, source);
        return true;
    }

    @Override
    public AgentOfMasksEffect copy() {
        return new AgentOfMasksEffect(this);
    }

}