package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PriceOfKnowledge extends CardImpl {

    public PriceOfKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{B}");


        // Players have no maximum hand size.
        Effect effect = new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield, HandSizeModification.SET, TargetController.ANY);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // At the beginning of each opponent's upkeep, Price of Knowledge deals damage to that player equal to the number of cards in that player's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new PriceOfKnowledgeEffect(), TargetController.OPPONENT, false));


    }

    private PriceOfKnowledge(final PriceOfKnowledge card) {
        super(card);
    }

    @Override
    public PriceOfKnowledge copy() {
        return new PriceOfKnowledge(this);
    }
}

class PriceOfKnowledgeEffect extends OneShotEffect {

    private PriceOfKnowledgeEffect(final PriceOfKnowledgeEffect effect) {
        super(effect);
    }

    public PriceOfKnowledgeEffect() {
        super(Outcome.Neutral);
        staticText = "{this} deals damage to that player equal to the number of cards in that player's hand";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            int xValue = targetPlayer.getHand().size();
            if (xValue > 0) {
                targetPlayer.damage(xValue, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public PriceOfKnowledgeEffect copy() {
        return new PriceOfKnowledgeEffect(this);
    }
}
