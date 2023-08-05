package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.GodEternalDiesTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IlhargTheRazeBoar extends CardImpl {

    public IlhargTheRazeBoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Ilharg, the Raze-Boar attacks, you may put a creature card from your hand onto the battlefield tapped and attacking. Return that creature to your hand at the beginning of the next end step.
        this.addAbility(new AttacksTriggeredAbility(new IlhargTheRazeBoarEffect(), true));

        // When Ilharg, the Raze-Boar dies or is put into exile from the battlefield, you may put it into its owner's library third from the top.
        this.addAbility(new GodEternalDiesTriggeredAbility());
    }

    private IlhargTheRazeBoar(final IlhargTheRazeBoar card) {
        super(card);
    }

    @Override
    public IlhargTheRazeBoar copy() {
        return new IlhargTheRazeBoar(this);
    }
}

class IlhargTheRazeBoarEffect extends OneShotEffect {

    IlhargTheRazeBoarEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a creature card from your hand onto the battlefield tapped and attacking. " +
                "Return that creature to your hand at the beginning of the next end step.";
    }

    private IlhargTheRazeBoarEffect(final IlhargTheRazeBoarEffect effect) {
        super(effect);
    }

    @Override
    public IlhargTheRazeBoarEffect copy() {
        return new IlhargTheRazeBoarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_CREATURE);
        if (!player.choose(outcome, player.getHand(), target, source, game)) {
            return false;
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        game.getCombat().addAttackingCreature(permanent.getId(), game);
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("return " + permanent.getName() + " to its owner's hand");
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;
    }
}
