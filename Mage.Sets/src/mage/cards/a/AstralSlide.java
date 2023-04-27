package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CycleAllTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class AstralSlide extends CardImpl {

    public AstralSlide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever a player cycles a card, you may exile target creature. If you do, return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new CycleAllTriggeredAbility(new AstralSlideEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AstralSlide(final AstralSlide card) {
        super(card);
    }

    @Override
    public AstralSlide copy() {
        return new AstralSlide(this);
    }
}

class AstralSlideEffect extends OneShotEffect {

    public AstralSlideEffect() {
        super(Outcome.Detriment);
        staticText = "exile target creature. If you do, return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public AstralSlideEffect(final AstralSlideEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                UUID exileId = UUID.randomUUID();
                if (controller.moveCardsToExile(permanent, source, game, true, exileId, sourceObject.getIdName())) {
                    //create delayed triggered ability
                    Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                    effect.setTargetPointer(new FixedTarget(permanent.getId(), game));
                    game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public AstralSlideEffect copy() {
        return new AstralSlideEffect(this);
    }
}
