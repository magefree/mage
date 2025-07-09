package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MausoleumTurnkey extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card of an opponent's choice");

    public MausoleumTurnkey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Mausoleum Turnkey enters the battlefield, return target creature card of an opponent's choice from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.setTargetAdjuster(MausoleumTurnkeyAdjuster.instance);
        this.addAbility(ability);
    }

    private MausoleumTurnkey(final MausoleumTurnkey card) {
        super(card);
    }

    @Override
    public MausoleumTurnkey copy() {
        return new MausoleumTurnkey(this);
    }
}

// Exact copy of KarplusanMinotaurAdjuster
enum MausoleumTurnkeyAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return;
        }
        UUID opponentId = null;
        if (game.getOpponents(controller.getId()).size() > 1) {
            Target target = new TargetOpponent(true);
            if (controller.chooseTarget(Outcome.Neutral, target, ability, game)) {
                opponentId = target.getFirstTarget();
            }
        } else {
            opponentId = game.getOpponents(controller.getId()).iterator().next();
        }
        if (opponentId != null) {
            ability.getTargets().get(0).setTargetController(opponentId);
        }
    }
}
