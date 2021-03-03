package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
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
 * @author LoneFox
 */
public final class Liberate extends CardImpl {

    public Liberate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target creature you control. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new LiberateEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private Liberate(final Liberate card) {
        super(card);
    }

    @Override
    public Liberate copy() {
        return new Liberate(this);
    }
}

class LiberateEffect extends OneShotEffect {

    LiberateEffect() {
        super(Outcome.Detriment);
        staticText = "exile target creature you control. Return that card to the battlefield " +
                "under its owner's control at the beginning of the next end step";
    }

    private LiberateEffect(final LiberateEffect effect) {
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
                new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false)
                        .setText("Return that card to the battlefield under its owner's control at the beginning of the next end step")
                        .setTargetPointer(new FixedTarget(card, game))
        ), source);
        return true;
    }

    @Override
    public LiberateEffect copy() {
        return new LiberateEffect(this);
    }
}
