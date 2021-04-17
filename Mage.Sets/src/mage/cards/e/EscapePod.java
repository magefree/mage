package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class EscapePod extends CardImpl {

    public EscapePod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Exile target creature you control. Return that card to the battlefield under your control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new EscapePodEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private EscapePod(final EscapePod card) {
        super(card);
    }

    @Override
    public EscapePod copy() {
        return new EscapePod(this);
    }
}

class EscapePodEffect extends OneShotEffect {

    EscapePodEffect() {
        super(Outcome.Detriment);
        staticText = "Exile target creature you control. Return that card to the battlefield " +
                "under your control at the beginning of the next end step";
    }

    private EscapePodEffect(final EscapePodEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        player.moveCards(permanent, Zone.EXILED, source, game);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnToBattlefieldUnderYourControlTargetEffect().setText(
                        "Return that card to the battlefield under your control at the beginning of the next end step"
                ).setTargetPointer(new FixedTarget(card, game))
        ), source);
        return true;
    }

    @Override
    public EscapePodEffect copy() {
        return new EscapePodEffect(this);
    }
}
